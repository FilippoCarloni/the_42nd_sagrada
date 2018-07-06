package it.polimi.ingsw.connection.rmi;
import it.polimi.ingsw.connection.client.RemoteObserver;
import it.polimi.ingsw.connection.server.CentralServer;
import it.polimi.ingsw.connection.server.messageencoder.MessageType;
import it.polimi.ingsw.connection.server.serverexception.ServerException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

import static it.polimi.ingsw.connection.costraints.ServerMessages.GAME_REQUEST;
import static it.polimi.ingsw.connection.costraints.ServerMessages.LOGIN_REQUEST;
import static it.polimi.ingsw.connection.costraints.ServerMessages.RESTORE_REQUEST;

/**
 * The Concretelobby class implements the Lobby interface.
 * This is the reference for the Lobby RMI connections.
 */
public class ConcreteLobby extends UnicastRemoteObject implements Lobby {
    private static final long serialVersionUID = 2757919800697427494L;
    private transient CentralServer server;
    private static final transient Logger logger=Logger.getLogger(ConcreteLobby.class.getName());

    /**
     * Creates a new game lobby for RMI connections.
     * @param server - The CentralServer references.
     * @throws RemoteException - Throws if there are issue in the creation of the ConcreteLobby.
     */
    public ConcreteLobby(CentralServer server) throws RemoteException{
        this.server=server;
    }

    /**
     * Connect the user to the Lobby.
     * @param username - Username of the user.
     * @param obs - RemoteObserver to contact the user.
     * @return - the SessionID of the session.
     * @throws RemoteException - Throws a RemoteException if there are issues in the connection phase.
     */
    public String connect(String username, RemoteObserver obs) throws RemoteException {
        String session;
        logger.info(() -> LOGIN_REQUEST+username);
        try {
            session=server.connect(username, new WrappedObs(obs));
        } catch (ServerException e) {
            throw new RemoteException(MessageType.encodeMessage(e.getMessage(),MessageType.ERROR_MESSAGE));
        }
        return session;
    }

    /**
     * Asks to enter in a game.
     * @param userSessionID - The userSessionID to identify the user.
     * @return - A GameController relative to the game requested.
     * @throws RemoteException - Throws a RemoteException if there are issues in the game request.
     */
    public GameManager getGame(String userSessionID) throws RemoteException {
        logger.info(() -> GAME_REQUEST+userSessionID);
        try {
            return server.getGame(userSessionID).getRemoteGame();
        } catch (ServerException e) {
            throw new RemoteException(MessageType.encodeMessage(e.getMessage(),MessageType.ERROR_MESSAGE));
        }
    }

    /**
     * Restores the old Session in the server from the oldSessionID and sends the new SessionID to the client.
     * @param oldSessionID - The old sessionID value associated to the Session to restore.
     * @param newObserver - The reference to the client RemoteObserver.
     * @return - the new SessionID of the restored Session.
     * @throws RemoteException - Throws a RemoteException if the are issues restoring the old Session.
     */
    public String restoreSession(String oldSessionID, RemoteObserver newObserver) throws RemoteException{
        try {
            logger.info(() -> RESTORE_REQUEST+oldSessionID);
            return server.restoreSession(oldSessionID,new WrappedObs(newObserver));
        } catch (ServerException e) {
            throw new RemoteException(MessageType.encodeMessage(e.getMessage(),MessageType.ERROR_MESSAGE));
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
