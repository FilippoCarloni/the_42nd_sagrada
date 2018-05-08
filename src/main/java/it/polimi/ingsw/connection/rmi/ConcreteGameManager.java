package it.polimi.ingsw.connection.rmi;

import com.sun.corba.se.impl.encoding.WrapperInputStream;
import it.polimi.ingsw.connection.client.RemoteObserver;
import it.polimi.ingsw.connection.server.Session;
import it.polimi.ingsw.connection.server.WrappedPlayer;
import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.GameStatus;
import javafx.beans.value.WritableListValue;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ConcreteGameManager extends Observable implements GameManger {

    private static class WrappedObserver implements Observer, Serializable {

        private static final long serialVersionUID = 1L;
        private RemoteObserver ro;

        WrappedObserver(RemoteObserver ro) {
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
            return obj instanceof RemoteObserver && obj.equals(ro);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
    private static Logger logger=Logger.getLogger(ConcreteGameManager.class.getName());
    private GameStatus data;
    private List<WrappedObserver> observers;
    private List<WrappedPlayer> players;

    ConcreteGameManager(List<WrappedPlayer> players) {
        data = new ConcreteGameStatus(players
                .stream()
                .map(WrappedPlayer::getPlayer)
                .collect(Collectors.toList()));
        observers = new ArrayList<>();
        this.players = players;
    }

    @Override
    public boolean isLegal(Session session, String command)  throws RemoteException {
        List <WrappedPlayer> player = players.stream().filter(x -> x.getSession().getID().equals(session.getID()))
                .collect(Collectors.toList());
        if(player.size() != 1) {
            logger.log(Level.SEVERE, "Hacker !!!, not playing user are tryng to enter in a match");
            throw new RemoteException("Error, you are not playing in this game");
        }
        for (WrappedPlayer p : players)
            if (p.getSession().getID().equals(session.getID()))
                return data.isLegal(p.getPlayer(), command);
        return false;
    }

    @Override
    public void sendCommand(Session session, String command) {
        for (WrappedPlayer p : players)
            if (p.getSession().getID().equals(session.getID()))
                data.execute(p.getPlayer(), command);
    }

    @Override
    public boolean isMyTurn(Session session) throws RemoteException  {
        for (WrappedPlayer p : players)
            if (p.getSession().getID().equals(session.getID()))
                    return data.isMyTurn(p.getPlayer());
        return false;
    }

    @Override
    public void addRemoteObserver(Session session, RemoteObserver o) throws RemoteException{
        if(players.stream().filter(x -> x.getSession().getID().equals(session.getID())).count()!= 1) {
            logger.log(Level.SEVERE, "Hacker !!!, not playing user are tryng to enter in a match");
            throw new RemoteException("Error, you are not playing in this game");
        }
        WrappedObserver mo = new WrappedObserver(o);
        addObserver(mo);
        observers.add(mo);
        logger.info(() -> "Added observer:" + mo);
    }

    public void removeRemoteObserver(RemoteObserver obs) throws RemoteException {
        for (WrappedObserver x : observers) {
            if (x.equals(obs)) {
                x.removeRemoteObserver(this);
                observers.remove(x);
                setChanged();
                notifyObservers();
                logger.info(()->"Removed observer:" + x);
                return;
            }
        }
        throw new RemoteException("Error occurred removing an observer.");
    }

    @Override
    public String getStatus() {
        return "" + data;
    }
}
