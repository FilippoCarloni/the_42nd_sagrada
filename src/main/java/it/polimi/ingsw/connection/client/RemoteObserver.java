package it.polimi.ingsw.connection.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interfaced defines the behavioral of a RemoteObserver.
 * An RemoteObserver can be updated or pinged to check if it is alive.
 */
public interface RemoteObserver extends Remote {

    /**
     * Sends an update to the RemoteObserver.
     * @param observable - The object that genrates the update.
     * @param updateMsg  The message to send.
     * @throws RemoteException - Throws if the are problems sending the update.
     */
    void remoteUpdate(Object observable, Object updateMsg) throws RemoteException;

    /**
     * Pings the RemoteObserver to check if it is alive or not.
     * @throws RemoteException - If there are issues.
     */
    void ping() throws RemoteException;
}