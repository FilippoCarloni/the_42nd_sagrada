package it.polimi.ingsw.connection.rmi;
import it.polimi.ingsw.connection.costraints.Settings;
import it.polimi.ingsw.connection.server.Session;
import it.polimi.ingsw.connection.server.WrappedPlayer;
import it.polimi.ingsw.model.players.ConcretePlayer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.*;
import java.util.stream.Collectors;

public class ConcreteLobby extends UnicastRemoteObject implements Lobby {

     private class WrappedGameMngr{
        private GameManager remoteGame;
        private ConcreteGameManager game;

        WrappedGameMngr(ConcreteGameManager game) throws RemoteException {
            this.game=game;
            this.remoteGame= (GameManager) UnicastRemoteObject.exportObject(game, Settings.RMI_PORT);
        }

        GameManager getRemoteGame() {
            return remoteGame;
        }

        ConcreteGameManager getGame() {
            return game;
        }
    }

    private transient List<WrappedGameMngr> gl;
    private transient Logger logger=Logger.getLogger(ConcretePlayer.class.getName());
    private transient List<WrappedPlayer> players;
    private transient List<WrappedPlayer>  disconnectedPlayer;
    private transient List<WrappedPlayer> waiting;
    public ConcreteLobby()throws RemoteException{

        players=new ArrayList<>();
        disconnectedPlayer=new ArrayList<>();
        waiting=new ArrayList<>();
        gl=new ArrayList<>();
    }

    public synchronized Session connect(String username)throws RemoteException {
        WrappedPlayer x;
        String filtred=username.trim();
        if(username.length()==0)
            throw new RemoteException("The username is not valid!");
        for (WrappedPlayer s : players)
            if (s.getPlayer().getUsername().equals(filtred)) {
                return new Session("","Username already used");
            }
        x=new WrappedPlayer(filtred);
        players.add(x);
        logger.info(() -> filtred+" is  now playing");
        return x.getSession();
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
    public synchronized GameManager getGame(Session userSession) throws RemoteException {
        List<WrappedGameMngr> game;
        int countergame=gl.size()+1;
        List<WrappedPlayer> player = players.stream().filter(
              x -> x.getSession().getID().equals(userSession.getID())).collect(Collectors.toList());
        if (player.size() != 1) {
          throw new RemoteException("You are not logged "+ userSession.getID());
        }
        game = gl.parallelStream().filter(x -> x.getGame().isPlaying(player.get(0))).collect(Collectors.toList());
        if (game.size() == 1) {
          return game.get(0).getRemoteGame();
        }

        if (waiting.parallelStream().noneMatch(x -> x.getSession().getID().equals(userSession.getID()))) {
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
              if(gl.stream().noneMatch(x -> x.getGame().isPlaying(player.get(0)))) {
                  gl.add(new WrappedGameMngr(new ConcreteGameManager(new ArrayList<>(waiting))));
                  waiting.clear();
              }
          }
        this.notifyAll();
        logger.info(() -> userSession.getID() + " is entered in match n "+ countergame);
        return gl.get(gl.size()-1).getRemoteGame();
    }
    public Session restoreSession(Session oldSession) throws RemoteException{
        List<WrappedPlayer> player = players.stream().filter(
                x -> x.getSession().getID().equals(oldSession.getID())).collect(Collectors.toList());
        Session newSession;
        if (player.size() != 1) {
            throw new RemoteException("You are not logged "+ oldSession.getID());
        }
        newSession=new Session(player.get(0).getPlayer().getUsername(),"");
        player.get(0).setSession(newSession);
        return newSession;
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
