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

    private ConcreteGameManager gl;
    private GameManger g;
    private Logger logger=Logger.getLogger(ConcretePlayer.class.getName());
    private List<WrappedPlayer> players;
    private List<WrappedPlayer>  disconnectedPlayer;
    private List<WrappedPlayer> waiting;
    private int counter;
    public ConcreteLobby()throws RemoteException{
        counter=0;

        players=new ArrayList<>();
        disconnectedPlayer=new ArrayList<>();
        waiting=new ArrayList<>();
        g=null;
    }

    public synchronized Session connect(String username)throws RemoteException {
        WrappedPlayer x;
        String filtred=username.trim();
        if(username.length()==0)
            throw new RemoteException("The username is not valid!");
        for (WrappedPlayer s : players)
            if (s.getSession().getID().equals(filtred)) {
                return new Session("","Username already used");
            }
        x=new WrappedPlayer(filtred);
        players.add(x);
        logger.info(() -> filtred+" is now playing");
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

        List<WrappedPlayer> player= players.stream().filter(
                x -> x .getSession().getID().equals(userSession.getID())).collect(Collectors.toList());
        if(player.size()!= 1) {

            throw new RemoteException("You are not Logged");
        }

        if(gl!=null&&gl.isPlaying(player.get(0))) {
            return g;
        }

        if(counter == 4 || g != null)
            throw new RemoteException("multiple games are not supported! (coming soon!)");

        if(waiting.stream().filter(x -> x.getSession().getID().equals(userSession.getID())).count()==0){
                    waiting.add(player.get(0));
                    counter++;
        }
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
            if (g == null) {
                g = (GameManger) UnicastRemoteObject.exportObject(gl = new ConcreteGameManager(waiting.stream().collect(Collectors.toList())), Settings.PORT);
                waiting.clear();
            }
            this.notifyAll();
        }
        logger.info(()-> userSession.getID() + " is entered in match n " + (counter - 1) / 4);
        return g;
    }

}
