package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.client.RemoteObserver;
import it.polimi.ingsw.connection.server.Session;
import it.polimi.ingsw.connection.server.WrappedPlayer;
import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.GameStatus;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ConcreteGameManager extends Observable implements GameManager {

    private static class WrappedObs implements Observer {
        private  RemoteObserver ro;

        WrappedObs(RemoteObserver ro) {
            this.ro = ro;
        }

        @Override
        public void update(Observable o, Object arg) {
            try {
                ro.remoteUpdate(o.toString(), arg);
            } catch (RemoteException e) {
                logger.info(() -> "Remote exception removing observer:" + this);
                o.deleteObserver(this);
            }
        }

        void removeRemoteObserver(Observable o) {
            o.deleteObserver(this);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof WrappedObs && ((WrappedObs) obj).ro.equals(ro);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
    private static final transient Logger logger=Logger.getLogger(ConcreteGameManager.class.getName());
    private final transient GameStatus data;
    private final transient List<WrappedPlayer> players;
    private final transient List<WrappedObs> observers;
    ConcreteGameManager(List<WrappedPlayer> players) {
        data = new ConcreteGameStatus(players
                .stream()
                .map(WrappedPlayer::getPlayer)
                .collect(Collectors.toList()));
        this.players = players;
        observers=new ArrayList<>();
    }

    @Override
    public synchronized boolean isLegal(Session session, String command)  throws RemoteException {
        return data.isLegal(this.getPlayer(session).getPlayer(), command.trim());
    }

    @Override
    public synchronized void sendCommand(Session session, String command) throws RemoteException {
        data.execute(this.getPlayer(session).getPlayer(), command.trim());
    }

    @Override
    public synchronized boolean isMyTurn(Session session) throws RemoteException  {
        return data.isMyTurn(this.getPlayer(session).getPlayer());
    }

    @Override
    public void addRemoteObserver(Session session, RemoteObserver o) throws RemoteException{
        this.getPlayer(session);
        synchronized(observers) {
            WrappedObs mo = new WrappedObs(o);
            addObserver(mo);
            observers.add(mo);
            logger.info(() -> "Added observer:" + mo);
        }
    }

    @Override
    public  void removeRemoteObserver(Session session, RemoteObserver obs) throws RemoteException {
        getPlayer(session);
        synchronized(observers) {
            for (WrappedObs x : observers) {
                if (x.equals(new WrappedObs(obs))) {
                    x.removeRemoteObserver(this);
                    observers.remove(x);
                    logger.info(() -> "Removed observer:" + x);
                    return;
                }
            }
        }
        throw new RemoteException("Error occurred removing an observer.");
    }

    @Override
    public synchronized String getStatus() {
        return "" + data;
    }
    private WrappedPlayer getPlayer(Session session) throws RemoteException{
        List<WrappedPlayer> player=players.stream().filter(x -> x.getSession().getID().equals(session.getID()))
                .collect(Collectors.toList());
        if(player.size() != 1) {
            logger.log(Level.SEVERE, "Hacker !!!, not playing user are trying to enter in a match");
            throw new RemoteException("Error, you are not playing in this game");
        }
        return player.get(0);
    }
    boolean isPlaying(WrappedPlayer player){
        return players.stream().filter(x-> x.equals(player)).count() == 1;
    }
}
