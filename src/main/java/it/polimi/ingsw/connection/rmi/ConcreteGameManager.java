package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.client.RemoteObserver;
import it.polimi.ingsw.connection.server.Session;
import it.polimi.ingsw.connection.server.WrappedPlayer;
import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.GameStatus;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
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
                System.out.println("Remote exception removing observer:" + this);
                o.deleteObserver(this);
            }
        }

        void removeRemoteObserver(Observable o){
            o.deleteObserver(this);
        }

        @Override
        public boolean equals(Object obj) {
            return obj.equals(ro);
        }
    }

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
    public boolean isLegal(Session session, String command) {
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
    public boolean isMyTurn(Session session) {
        for (WrappedPlayer p : players)
            if (p.getSession().getID().equals(session.getID()))
                    return data.isMyTurn(p.getPlayer());
        return false;
    }

    @Override
    public void addRemoteObserver(RemoteObserver o) {
        WrappedObserver mo = new WrappedObserver(o);
        addObserver(mo);
        observers.add(mo);
        System.out.println("Added observer:" + mo);
    }

    public void removeRemoteObserver(RemoteObserver obs) throws RemoteException {
        for (WrappedObserver x : observers) {
            if (x.equals(obs)) {
                x.removeRemoteObserver(this);
                observers.remove(x);
                setChanged();
                notifyObservers();
                System.out.println("Removed observer:" + x);
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
