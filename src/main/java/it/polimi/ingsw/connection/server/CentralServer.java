package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.costraints.Settings;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CentralServer {

        private transient List<WrappedGameController> gl;
        private transient Logger logger=Logger.getLogger(CentralServer.class.getName());
        private transient List<WrappedPlayer> players;
        private transient List<WrappedPlayer>  disconnectedPlayer;
        private transient List<WrappedPlayer> waiting;
        CentralServer()throws RemoteException{

            players=new ArrayList<>();
            disconnectedPlayer=new ArrayList<>();
            waiting=new ArrayList<>();
            gl=new ArrayList<>();
        }

        public synchronized String connect(String username)throws Exception {
            WrappedPlayer x;
            String filtred=username.trim();
            if(username.length()==0)
                throw new RemoteException("The username is not valid!");
            for (WrappedPlayer s : players)
                if (s.getPlayer().getUsername().equals(filtred)) {
                    throw new Exception("Username already used");
                }
            x=new WrappedPlayer(filtred);
            players.add(x);
            logger.info(() -> filtred+" is  now playing");
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
        public synchronized WrappedGameController getGame(String userSessionID) throws RemoteException {
            List<WrappedGameController> game;
            int countergame=gl.size()+1;
            List<WrappedPlayer> player = players.stream().filter(
                    x -> x.getSession().getID().equals(userSessionID)).collect(Collectors.toList());
            if (player.size() != 1) {
                throw new RemoteException("You are not logged "+ userSessionID);
            }
            game = gl.parallelStream().filter(x -> x.getGameController().isPlaying(player.get(0))).collect(Collectors.toList());
            if (game.size() == 1) {
                return game.get(0);
            }

            if (waiting.parallelStream().noneMatch(x -> x.getSession().getID().equals(userSessionID))) {
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
                if(gl.stream().noneMatch(x -> x.getGameController().isPlaying(player.get(0)))) {
                    gl.add(new WrappedGameController(waiting));
                    waiting.clear();
                }
            }
            this.notifyAll();
            logger.info(() -> userSessionID + " is entered in match n "+ countergame);
            return gl.get(gl.size()-1);
        }
        public synchronized String restoreSession(String oldSessionID) throws Exception{
            List<WrappedPlayer> player = players.stream().filter(
                    x -> x.getSession().getID().equals(oldSessionID)).collect(Collectors.toList());
            Session newSession;
            if (player.size() != 1) {
                throw new Exception("This sessionID not exists: "+ oldSessionID);
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
