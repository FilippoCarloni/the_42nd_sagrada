package it.polimi.ingsw.connection.rmi;
import it.polimi.ingsw.connection.client.RemoteObserver;
import it.polimi.ingsw.connection.server.CentralServer;
import it.polimi.ingsw.connection.server.Session;
import it.polimi.ingsw.connection.server.messageencoder.MessageType;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

public class ConcreteLobby extends UnicastRemoteObject implements Lobby {
    private transient CentralServer server;
    private static final transient Logger logger=Logger.getLogger(ConcreteLobby.class.getName());

    public ConcreteLobby(CentralServer server)throws RemoteException{
        if (server == null)
            throw new RemoteException("Null pointer");
        this.server=server;
    }

    public String connect(String username, RemoteObserver obs)throws RemoteException {
        String session;
        logger.info(() -> "Login requested from: "+username);
        try {
            session=server.connect(username, new WrappedObs(obs));
        } catch (Exception e) {
            throw new RemoteException(MessageType.encodeMessage(e.getMessage(),MessageType.ERROR_MESSAGE));
        }
        return session;
    }
    public void disconnect(Session userSession)throws RemoteException {
        server.disconnect(userSession);
        throw new RemoteException("error, and it is a very big problem!");
    }
    public GameManager getGame(String userSessionID) throws RemoteException {
        logger.info(() -> "Game request from: "+userSessionID);
        try {
            return server.getGame(userSessionID).getRemoteGame();
        } catch (Exception e) {
            throw new RemoteException(MessageType.encodeMessage(e.getMessage(),MessageType.ERROR_MESSAGE));
        }
    }
    public String restoreSession(String oldSessionID, RemoteObserver newObserver) throws RemoteException{
        try {
            return server.restoreSession(oldSessionID,new WrappedObs(newObserver));
        } catch (Exception e) {
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
