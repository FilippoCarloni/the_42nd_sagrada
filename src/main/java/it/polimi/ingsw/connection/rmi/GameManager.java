package it.polimi.ingsw.connection.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The GamaManager interface define the RMI method interface to call from the client.
 */
public interface GameManager extends Remote {

    /**
     * Says if is the turn of the OnLinePlayer associated to the sessionID.
     * @param sessionID - The sessionID of the OnLinePlayer to check.
     * @return - True if is the turn of the OnLinePlayer, false otherwise.
     * @throws RemoteException - Throws a RemoteException if there are issues in the request.
     */
    boolean isMyTurn(String sessionID) throws RemoteException;

    /**
     * Executes the game command for the OnLinePlayer associated to the sessionID.
     * @param sessionID - The sessionID associated to the OnLinePlayer.
     * @param command - The game command to executes.
     * @throws RemoteException - Throws a RemoteException if there are issues.
     */
    void sendCommand(String sessionID, String command) throws RemoteException;

    /**
     * Return the game status of the match in which is enrolled the OnLinePlayer associated to the sessionID.
     * @param sessionID - The sessionID of player.
     * @return - The game status of the match in are playing the player.
     * @throws RemoteException - Throws a RemoteException if there are issues.
     */
    String getStatus(String sessionID) throws RemoteException;

    /**
     * Sets the initial window for the OnlinePlayer associated to the sessionID enrolled in the game.
     * @param sessionID - The sessionID associated to the player.
     * @param window - The window number to set.
     * @throws RemoteException - Throws a RemoteException if there are issues in the operation.
     */
    void setMap(String sessionID, int window) throws RemoteException;
}
