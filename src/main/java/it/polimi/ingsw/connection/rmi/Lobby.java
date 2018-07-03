package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.client.RemoteObserver;
import java.rmi.*;

public interface Lobby extends Remote{
    /**
     * Connect the user to the Lobby.
     * @param username - Username of the user.
     * @param obs - RemoteObserver to contact the user.
     * @return - the SessionID of the session.
     * @throws RemoteException - Throws if there are issues in the connection phase.
     */
    String connect(String username, RemoteObserver obs)throws RemoteException;

    /**
     * Asks to enter in a game.
     * @param userSessionID - The userSessionID to identify the user.
     * @return - A GameController relative to the game requested.
     * @throws RemoteException - If there are issues in the game request.
     */
    GameManager getGame(String userSessionID) throws RemoteException;
    String restoreSession(String oldSessionID,RemoteObserver obs) throws RemoteException;

}