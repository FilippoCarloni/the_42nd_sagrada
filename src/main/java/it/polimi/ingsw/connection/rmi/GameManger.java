package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.client.RemoteObserver;
import it.polimi.ingsw.connection.server.Session;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameManger extends Remote {

    void addRemoteObserver(RemoteObserver obs) throws RemoteException;
    void removeRemoteObserver(RemoteObserver obs) throws RemoteException;
    boolean isMyTurn(Session session) throws RemoteException;
    boolean isLegal(Session session, String command) throws RemoteException;
    void sendCommand(Session session, String command) throws RemoteException;
    String getStatus() throws RemoteException;
}
