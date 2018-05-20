package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.client.RemoteObserver;
import it.polimi.ingsw.connection.server.GameObserver;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.logging.Logger;

class WrappedObs extends Observable implements GameObserver, Serializable {
    private RemoteObserver ro;
    private static final transient Logger logger=Logger.getLogger(WrappedObs.class.getName());
    WrappedObs(RemoteObserver ro) {
        this.ro = ro;
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            ro.remoteUpdate(this, arg);
        } catch (RemoteException e) {
            logger.info(() -> "Remote exception update observer:" + e.getMessage() + this);
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

    @Override
    public boolean isAlive() {
        boolean isAlive = true;
        try {
            ro.isAlive();
        } catch (RemoteException e) {
            isAlive = false;
        }
        return isAlive;
    }
}
