package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.client.RemoteObserver;
import java.rmi.*;

/**
 * The Lobby interfaces defines the action that can be by the lobbyof the game.
 * In particular is the reference for the RMI first step connection.
 */
public interface Lobby extends Remote{
    /**
     * Connect the user to the Lobby.
     * @param username - Username of the user.
     * @param obs - RemoteObserver to contact the user.
     * @return - the SessionID of the session.
     * @throws RemoteException - Throws a RemoteException if there are issues in the connection phase.
     */
    String connect(String username, RemoteObserver obs)throws RemoteException;

    /**
     * Asks to enter in a game.
     * @param userSessionID - The userSessionID to identify the user.
     * @return - A GameController relative to the game requested.
     * @throws RemoteException - Throws a RemoteException if there are issues in the game request.
     */
    GameManager getGame(String userSessionID) throws RemoteException;

    /**
     * Restores the old Session in the server from the oldSessionID and sends the new SessionID to the client.
     * @param oldSessionID - The old sessionID value associated to the Session to restore.
     * @param newObserver - The reference to the client RemoteObserver.
     * @return - the new SessionID of the restored Session.
     * @throws RemoteException - Throws a RemoteException if the are issues restoring the old Session.
     */
    String restoreSession(String oldSessionID,RemoteObserver newObserver) throws RemoteException;

}