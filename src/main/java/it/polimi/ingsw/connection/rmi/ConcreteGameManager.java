package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.server.GameController;
import it.polimi.ingsw.connection.server.messageencoder.MessageType;
import it.polimi.ingsw.connection.server.serverexception.ServerException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

import static it.polimi.ingsw.connection.costraints.ServerMessage.*;

/**
 * The ConcreteGameManager is the game references for the RMI connection.
 */
public class ConcreteGameManager extends UnicastRemoteObject implements GameManager {


    private static final transient Logger logger=Logger.getLogger(ConcreteGameManager.class.getName());
    private final transient GameController gameController;

    /**
     * Creates a new ConcreteGameManager.
     * @param gameController - The reference of the GameController of teh match.
     * @throws RemoteException - Throws RemoteException if there are issues in the object creation.
     */
    public ConcreteGameManager(GameController gameController) throws RemoteException{
        this.gameController=gameController;
    }
    /**
     * Says if is the turn of the OnLinePlayer associated to the sessionID.
     * @param sessionID - The sessionID of the OnLinePlayer to check.
     * @return - True if is the turn of the OnLinePlayer, false otherwise.
     * @throws RemoteException - Throws a RemoteException if there are issues in the request.
     */
    @Override
    public boolean isMyTurn(String sessionID) throws RemoteException {
        logger.info(() -> TURN_REQUEST+sessionID);
        try {
            return gameController.isMyTurn(sessionID);
        }catch (ServerException e) {
            throw new RemoteException(MessageType.encodeMessage(e.getMessage(),MessageType.ERROR_MESSAGE));
        }
    }


    /**
     * Executes the game command for the OnLinePlayer associated to the sessionID.
     * @param sessionID - The sessionID associated to the OnLinePlayer.
     * @param command - The game command to executes.
     * @throws RemoteException - Throws a RemoteException if there are issues.
     */
    @Override
    public void sendCommand(String sessionID, String command) throws RemoteException {
        logger.info(()->COMMAND_REQUEST_1+command+COMMAND_REQUEST_2+sessionID);
        try{
            gameController.sendCommand(sessionID, command);
        }catch (ServerException e) {
            throw new RemoteException(MessageType.encodeMessage(e.getMessage(),MessageType.ERROR_MESSAGE));
        }
    }

    /**
     * Return the game status of the match in which is enrolled the OnLinePlayer associated to the sessionID.
     * @param sessionID - The sessionID of player.
     * @return - The game status of the match in are playing the player.
     * @throws RemoteException - Throws a RemoteException if there are issues.
     */
    @Override
    public String getStatus(String sessionID) throws RemoteException {
        logger.info(()->STATUS_REQUEST+sessionID);
        try {
            return gameController.getStatus(sessionID);
        }catch (ServerException e) {
            throw new RemoteException(MessageType.encodeMessage(e.getMessage(),MessageType.ERROR_MESSAGE));
        }
    }


    /**
     * Sets the initial window for the OnlinePlayer associated to the sessionID enrolled in the game.
     * @param sessionID - The sessionID associated to the player.
     * @param window - The window number to set.
     * @throws RemoteException - Throws a RemoteException if there are issues in the operation.
     */
    @Override
    public void setMap(String sessionID, int window) throws RemoteException {
        logger.info(() -> WINDOW_REQUEST + sessionID);
        try {
            gameController.setMap(sessionID,window);
        } catch (ServerException e) {
            throw new RemoteException(MessageType.encodeMessage(e.getMessage(), MessageType.ERROR_MESSAGE));
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
