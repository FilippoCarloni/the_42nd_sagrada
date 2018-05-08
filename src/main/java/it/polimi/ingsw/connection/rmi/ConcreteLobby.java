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

public class ConcreteLobby extends UnicastRemoteObject implements Lobby {

    private GameManger g;
    private Logger logger=Logger.getLogger(ConcretePlayer.class.getName());
    private List<WrappedPlayer> players;
    ConcreteLobby l;
    private List<WrappedPlayer>  disconnectedPlayer;
    private int counter;
    public ConcreteLobby()throws RemoteException{
        counter=0;

        players=new ArrayList<>();
        disconnectedPlayer=new ArrayList<>();
        g=null;
    }

    public synchronized Session connect(String username)throws RemoteException {
        WrappedPlayer x;
        for (WrappedPlayer s : players)
            if (s.getSession().getID().equals(username))
                return new Session("","Username already used");
        if(username.length()==0)
            throw new RemoteException("The username is not valid!");
        x=new WrappedPlayer(username.trim());
        players.add(x);
        logger.info(() -> username+" is now playing");
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
  public synchronized GameManger getGame(Session userSession) throws RemoteException {
        if(counter == 4 || g != null)
            throw new RemoteException("multiple games are not supported! (coming soon!)");
        for (WrappedPlayer s : players)
            if (s.getSession().getID().equals(userSession.getID())) {
                counter++;
                if (counter % 4 != 0 && counter % 4 < 2) {
                    while (counter % 4 != 0 && counter % 4 < 2) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            logger.log(Level.SEVERE,"Fatal error!",e);
                        }
                    }
                } else {
                    if (counter % 4 != 0)
                        try {
                            wait(Settings.WAITINGTIMETOMATCH);

                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            logger.log(Level.SEVERE, "Fatal error!", e);
                        }
                    if (g == null)
                        g = (GameManger) UnicastRemoteObject.exportObject(new ConcreteGameManager(players), Settings.PORT);
                    this.notifyAll();
                }
                logger.info(()-> userSession.getID() + " is entered in match n " + (counter - 1) / 4);
                return g;
            }
            throw new RemoteException("Error");
    }

}
