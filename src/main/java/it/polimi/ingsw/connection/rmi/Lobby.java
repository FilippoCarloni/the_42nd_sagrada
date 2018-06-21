package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.client.RemoteObserver;
import it.polimi.ingsw.connection.server.ServerSession;

import java.rmi.*;

public interface Lobby extends Remote{
    String connect(String username, RemoteObserver obs)throws RemoteException;
    void disconnect(ServerSession userServerSession)throws RemoteException;
    GameManager getGame(String userSession) throws RemoteException;
    String restoreSession(String oldSessionID,RemoteObserver obs) throws RemoteException;

}