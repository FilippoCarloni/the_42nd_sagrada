package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.server.messageencoder.MessageType;
import it.polimi.ingsw.connection.server.serverexception.ServerException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static it.polimi.ingsw.connection.server.ServerMessage.*;
import static it.polimi.ingsw.connection.server.serverexception.ErrorCode.SERVER_ERROR;

public class CentralServer {

        private List<WrappedGameController> gameControllers;
        private Logger logger=Logger.getLogger(CentralServer.class.getName());
        private List<WrappedPlayer> players;
        private List<WrappedPlayer>  disconnectedPlayer;
        private List<LobbyManager> lobbyManagers;
        private Observable observable;
        CentralServer() {

            players=new ArrayList<>();
            disconnectedPlayer=new ArrayList<>();
            gameControllers=new ArrayList<>();
            lobbyManagers=new ArrayList<>();
            observable=new Observable();
        }

        public synchronized String connect(String username,GameObserver obs) throws ServerException {
            WrappedPlayer x;
            String filtered=username.trim();
            if(!Pattern.compile("^[a-zA-Z0-9_-]{4,12}$").asPredicate().test(filtered))
                throw new ServerException(NOT_VALID_USERNAME,SERVER_ERROR);
            for (WrappedPlayer s : players)
                if (s.getPlayer().getUsername().compareToIgnoreCase((filtered))==0) {
                    throw new ServerException(ALREADY_EXISTING_USERNAME,SERVER_ERROR);
                }
            x=new WrappedPlayer(filtered,obs);
            players.add(x);
            observable.addObserver(obs);
            logger.info(() -> filtered+" is connected with sessionID: "+x.getSession().getID());
            return x.getSession().getID();
        }
        public synchronized void disconnect(Session userSession)throws RemoteException {
            for (WrappedPlayer s : players)
                if (s.getSession().getID().equals(userSession.getID())) {
                    if (s.isPlaying()) {
                        disconnectedPlayer.add(s);
                        return;
                    }
                    else {
                        players.remove(s);
                        return ;
                    }
                }
            throw new RemoteException("error, and it is a very big problem!");
        }
        public  WrappedGameController getGame(String userSessionID) throws ServerException {
            WrappedGameController game;
            LobbyManager lobbyManager=null;
            boolean find=false;
            List<WrappedPlayer> player;
            int counterGame;
            List<WrappedPlayer> waiting;
            synchronized (this) {
                counterGame= gameControllers.size() + 1;
                player = players.stream().filter(
                        x -> x.getSession().getID().equals(userSessionID)).collect(Collectors.toList());
                if (player.size() != 1) {
                    throw new ServerException(NOT_LOGGED + userSessionID,SERVER_ERROR);
                }
                game = currentGame(player.get(0));
                if (game != null) {
                    if (game.getGameController().reconnect(player.get(0)))
                        return game;
                    else
                        player.get(0).getObserver().update(observable, MessageType.encodeMessage("Previous match was ended because there are too few players. Starting a new one", MessageType.GENERIC_MESSAGE));
                }
                for (LobbyManager lobby : lobbyManagers) {
                    if (lobby.add(player.get(0))) {
                        find = true;
                        lobbyManager = lobby;
                        break;
                    }
                }
                if (!find) {
                    lobbyManager = new LobbyManager();
                    lobbyManagers.add(lobbyManager);
                    lobbyManager.add(player.get(0));
                }
            }
            waiting=lobbyManager.waitStart();
            synchronized (this) {
                if (currentGame(player.get(0)) == null) {
                    waiting.parallelStream().forEach(x -> x.setPlaying(true));
                    gameControllers.add(new WrappedGameController(this, waiting));
                    waiting.clear();
                    lobbyManagers.remove(lobbyManager);
                }
            }
            logger.info(() -> userSessionID + " is entered in match n "+ counterGame);
            return currentGame(player.get(0));
        }

        private WrappedGameController currentGame(WrappedPlayer player){
            List<WrappedGameController> game=new ArrayList<>();
            if(player.isPlaying())
                 game=gameControllers.parallelStream().filter(x -> x.getGameController().isPlaying(player)).collect(Collectors.toList());
            return (game.size()==1) ? game.get(0) : null;
        }

        synchronized void closeGame(GameController gameController){
            List<WrappedGameController> wrappedGameController;
            wrappedGameController=gameControllers.parallelStream().filter(x->x.getGameController()==gameController).collect(Collectors.toList());
            if(wrappedGameController.size()==1) {
                gameController.getPlayers().parallelStream().forEach(x -> x.setPlaying(false));
                gameControllers.remove(wrappedGameController.get(0));
            }
        }
        public synchronized String restoreSession(String oldSessionID, GameObserver obs) throws ServerException{
            List<WrappedPlayer> player = players.stream().filter(
                    x -> x.getSession().getID().equals(oldSessionID)).collect(Collectors.toList());
            Session newSession;
            if (player.size() != 1) {
                throw new ServerException(NOT_EXISTING_SESSION+": "+ oldSessionID,SERVER_ERROR);
            }
            if(player.get(0).getObserver().isAlive())
                throw new ServerException(NOT_MULTYGAME_WITH_ONE_CLIENT,SERVER_ERROR);
            else {
                observable.deleteObserver(player.get(0).getObserver());
                observable.addObserver(obs);
                player.get(0).setObserver(obs);
            }
            newSession=new Session(player.get(0).getPlayer().getUsername(),"");
            player.get(0).setSession(newSession);
            logger.info(()->"Restored session of "+player.get(0).getPlayer().getUsername()+" :"+oldSessionID+" -> "+newSession.getID());
            return newSession.getID();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

}
