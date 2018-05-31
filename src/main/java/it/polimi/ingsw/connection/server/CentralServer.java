package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.costraints.Settings;

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

        private transient List<WrappedGameController> gameControllers;
        private transient Logger logger=Logger.getLogger(CentralServer.class.getName());
        private transient List<WrappedPlayer> players;
        private transient List<WrappedPlayer>  disconnectedPlayer;
        private transient List<WrappedPlayer> waiting;
        private Observable observable;
        CentralServer() {

            players=new ArrayList<>();
            disconnectedPlayer=new ArrayList<>();
            waiting=new ArrayList<>();
            gameControllers=new ArrayList<>();
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
        public synchronized WrappedGameController getGame(String userSessionID) throws Exception {
            List<WrappedGameController> game;
            int countergame=gameControllers.size()+1;
            List<WrappedPlayer> player = players.stream().filter(
                    x -> x.getSession().getID().equals(userSessionID)).collect(Collectors.toList());
            if (player.size() != 1) {
                throw new Exception("You are not logged "+ userSessionID);
            }
            if(player.get(0).isPlaying()) {
                game = gameControllers.parallelStream().filter(x -> x.getGameController().isPlaying(player.get(0))).collect(Collectors.toList());
                if (game.size() == 1) {
                    if(game.get(0).getGameController().reconnect(player.get(0)))
                        return game.get(0);
                    else
                        player.get(0).getObserver().update(observable,"previous match was ended because there are too few players. Starting a new one");
                }
            }
            player.get(0).getObserver().update(observable,"Waiting others players ...");
            if (waiting.parallelStream().noneMatch(x -> x.getSession().getID().equals(userSessionID))) {
                waiting.parallelStream().forEach(x -> {
                    x.getObserver().update(observable, player.get(0).getPlayer().getUsername() + " is connected to this game!");
                    player.get(0).getObserver().update(observable, x.getPlayer().getUsername() + " is connected to this game!");
                });
                waiting.add(player.get(0));
            }
            if (waiting.size() % 4 != 0 && waiting.size()% 4 < 2) {
                while (waiting.size() % 4 != 0 && waiting.size() % 4 < 2) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        logger.log(Level.SEVERE, "Fatal error!", e);
                    }
                }
            } else {
                if (waiting.size() % 4 != 0)
                    try {
                        wait(Settings.WAITINGTIMETOMATCH);

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        logger.log(Level.SEVERE, "Fatal error!", e);
                    }
                if(gameControllers.parallelStream().noneMatch(x -> x.getGameController().isPlaying(player.get(0)))) {
                    waiting.parallelStream().forEach( x->x.setPlaying(true));
                    gameControllers.add(new WrappedGameController(this, waiting));
                    waiting.clear();
                }
            }
            this.notifyAll();
            logger.info(() -> userSessionID + " is entered in match n "+ countergame);
            return gameControllers.get(gameControllers.size()-1);
        }

        synchronized void closeGame(GameController gameController){
            if(gameControllers.parallelStream().anyMatch( x-> x.getGameController()==gameController)){
                gameController.getPlayers().parallelStream().forEach( x -> x.setPlaying(false));
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
