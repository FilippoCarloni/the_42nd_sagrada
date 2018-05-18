package it.polimi.ingsw.connection.rmi;
import it.polimi.ingsw.connection.server.CentralServer;
import it.polimi.ingsw.connection.server.Session;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConcreteLobby extends UnicastRemoteObject implements Lobby {
    private transient CentralServer server;

    public ConcreteLobby(CentralServer server)throws RemoteException{
        if (server == null)
            throw new RemoteException("Null pointer");
        this.server=server;
    }

    public synchronized String connect(String username)throws RemoteException {
        String session;
        try {
            session=server.connect(username);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
        return session;
    }
    public synchronized void disconnect(Session userSession)throws RemoteException {
        server.disconnect(userSession);
        throw new RemoteException("error, and it is a very big problem!");
    }
    public synchronized GameManager getGame(String userSessionID) throws RemoteException {
       return server.getGame(userSessionID).getRemoteGame();

    }
    public String restoreSession(String oldSessionID) throws RemoteException{
        try {
            return server.restoreSession(oldSessionID);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
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
