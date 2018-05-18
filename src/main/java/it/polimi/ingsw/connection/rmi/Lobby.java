package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.server.Session;

import java.rmi.*;

public interface Lobby extends Remote{
    String connect(String username)throws RemoteException;
    void disconnect(Session userSession)throws RemoteException;
    GameManager getGame(String userSession) throws RemoteException;
    String restoreSession(String oldSessionID) throws RemoteException;

}