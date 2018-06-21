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
        private List<OnLinePlayer> players;
        private List<OnLinePlayer>  disconnectedPlayer;
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
            OnLinePlayer x;
            String filtered=username.trim();
            if(!Pattern.compile("^[a-zA-Z0-9_-]{4,12}$").asPredicate().test(filtered))
                throw new ServerException(NOT_VALID_USERNAME,SERVER_ERROR);
            for (OnLinePlayer s : players)
                if (s.getUsername().compareToIgnoreCase((filtered))==0) {
                    throw new ServerException(ALREADY_EXISTING_USERNAME,SERVER_ERROR);
                }
            x=new OnLinePlayer(filtered,obs);
            players.add(x);
            observable.addObserver(obs);
            logger.info(() -> filtered+" is connected with sessionID: "+x.getServerSession().getID());
            return x.getServerSession().getID();
        }
        public synchronized void disconnect(ServerSession userServerSession)throws RemoteException {
            for (OnLinePlayer s : players)
                if (s.getServerSession().getID().equals(userServerSession.getID())) {
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
            List<OnLinePlayer> player;
            int counterGame;
            List<OnLinePlayer> waiting;
            synchronized (this) {
                counterGame= gameControllers.size() + 1;
                player = players.stream().filter(
                        x -> x.getServerSession().getID().equals(userSessionID)).collect(Collectors.toList());
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

        private WrappedGameController currentGame(OnLinePlayer player){
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
            List<OnLinePlayer> player = players.stream().filter(
                    x -> x.isMySessionID(oldSessionID)).collect(Collectors.toList());
            ServerSession newServerSession;
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
            newServerSession = player.get(0).refreshSession();
            logger.info(()->"Restored session of "+player.get(0).getPlayer().getUsername()+" :"+oldSessionID+" -> "+ newServerSession.getID());
            return newServerSession.getID();
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
