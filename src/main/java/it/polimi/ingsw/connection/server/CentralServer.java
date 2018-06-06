package it.polimi.ingsw.connection.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
//@TODO create a proper exception class for the server
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

        public synchronized String connect(String username,GameObserver obs)throws Exception {
            WrappedPlayer x;
            String filtred=username.trim();
            if(!Pattern.compile("^[a-zA-Z0-9_-]{4,12}$").asPredicate().test(filtred))
                throw new RemoteException("The username is not valid!");
            for (WrappedPlayer s : players)
                if (s.getPlayer().getUsername().compareToIgnoreCase((filtred))==0) {
                    throw new Exception("Username already used");
                }
            x=new WrappedPlayer(filtred,obs);
            players.add(x);
            observable.addObserver(obs);
            logger.info(() -> filtred+" is connected with sessionID: "+x.getSession().getID());
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
        public  WrappedGameController getGame(String userSessionID) throws Exception {
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
                    throw new Exception("You are not logged " + userSessionID);
                }
                game = currentGame(player.get(0));
                if (game != null) {
                    if (game.getGameController().reconnect(player.get(0)))
                        return game;
                    else
                        player.get(0).getObserver().update(observable, "previous match was ended because there are too few players. Starting a new one");
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
            if(wrappedGameController.size()==1)
                if(gameControllers.parallelStream().anyMatch( x-> x.getGameController()==gameController)){
                    gameController.getPlayers().parallelStream().forEach( x -> x.setPlaying(false));
                    gameControllers.remove(wrappedGameController.get(0));
                }

        }
        public synchronized String restoreSession(String oldSessionID, GameObserver obs) throws Exception{
            List<WrappedPlayer> player = players.stream().filter(
                    x -> x.getSession().getID().equals(oldSessionID)).collect(Collectors.toList());
            Session newSession;
            if (player.size() != 1) {
                throw new Exception("This sessionID not exists: "+ oldSessionID);
            }
            if(player.get(0).getObserver().isAlive())
                throw new Exception("NO multi client for a single user!");
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
