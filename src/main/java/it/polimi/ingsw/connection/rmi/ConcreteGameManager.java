package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.client.RemoteObserver;
import it.polimi.ingsw.connection.server.WrappedPlayer;

import javax.sound.midi.SysexMessage;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ConcreteGameManager extends Observable implements GameManger{

    private class WrappedObserver implements Observer, Serializable {

        private static final long serialVersionUID = 1L;

        private RemoteObserver ro = null;

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

    private String data;
    private int counter;
    private List<WrappedObserver> observers;
    private List<WrappedPlayer> players;
    public ConcreteGameManager(List<WrappedPlayer> players) {
        counter = 0;
        data = "Hello world!";
        observers=new ArrayList<>();
        this.players=players;
    }
    @Override
    public boolean myTurn(RemoteObserver obs)  throws RemoteException{
        int i;
        synchronized (this) {
            i = counter % this.countObservers();
        }
        return observers.get(i).equals(obs);

    }
    @Override
    public void setData(String data)  throws RemoteException{

        this.data = data;
        synchronized (this) {
            counter++;
        }
        setChanged();
        notifyObservers();
    }
    @Override
    public String getData() throws RemoteException{
        return data;
    }

    @Override
    public void addRemoteObserver(RemoteObserver o) throws RemoteException {
        WrappedObserver mo = new WrappedObserver(o);
        addObserver(mo);
        observers.add(mo);
        System.out.println("Added observer:" + mo);
    }
    public void removeRemoteObserver(RemoteObserver obs) throws RemoteException {
        int i=0;
        for (WrappedObserver x : observers) {
            if (x.equals(obs)) {
                x.removeRemoteObserver(this);
                observers.remove(x);
                setChanged();
                notifyObservers();
                System.out.println("Removed observer:" + x);
                return;
            }
            i++;
        }
        throw new RemoteException("Error occurred removing an observer!");
    }
}
