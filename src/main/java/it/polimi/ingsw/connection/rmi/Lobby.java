package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.server.Session;

import java.rmi.*;

public interface Lobby extends Remote{
    Session connect(String username)throws RemoteException;
    void disconnect(Session userSession)throws RemoteException;
    GameManger getGame(Session userSession) throws RemoteException;

}