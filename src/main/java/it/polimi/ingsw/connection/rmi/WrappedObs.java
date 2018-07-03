package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.client.RemoteObserver;
import it.polimi.ingsw.connection.server.GameObserver;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.logging.Logger;

import static it.polimi.ingsw.connection.rmi.RMIMessage.REMOTE_UPDATE_ERROR;


/**
 * The WrappedRemoteObserver class wraps a RemoteObser in a GameObserver. It permits to use the RemoteObserver
 * like a GameObserver.
 */
class WrappedObs extends Observable implements GameObserver, Serializable {
    private transient RemoteObserver ro;
    private static final transient Logger logger=Logger.getLogger(WrappedObs.class.getName());

    /**
     * Creates a WrappedRemoteObserver from a RemoteObserver.
     * @param ro - The RemoteObserver to wrap.
     */
    WrappedObs(RemoteObserver ro) {
        this.ro = ro;
    }

    /**
     * Updates the RemoteObserver.
     * @param o - The Observable that generates the update.
     * @param arg - The message to send.
     */
    @Override
    public void update(Observable o, Object arg) {
        try {
            ro.remoteUpdate(this, arg);
        } catch (RemoteException e) {
            logger.info(() -> REMOTE_UPDATE_ERROR + e.getMessage() + this);
        }
    }

    /**
     * Compare two WrappedRemoteObserver.
     * @param obj - The WrappedRemoteObserver to compare.
     * @return - If the obj wraps a remoteObserver that equals the reference to the internal reference to the
     * RemoteObserver.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof WrappedObs && ((WrappedObs) obj).ro.equals(ro);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Try to sends a ping to the RemoteObserver to verify if it is alive or not.
     * @return - If the player is alive or not.
     */
    @Override
    public boolean isAlive() {
        boolean isAlive = true;
        try {
            ro.ping();
        } catch (RemoteException e) {
            isAlive = false;
        }
        return isAlive;
    }
}
