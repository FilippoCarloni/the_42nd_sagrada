package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.client.RemoteObserver;
import it.polimi.ingsw.connection.server.Session;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameManager extends Remote {

    void addRemoteObserver(Session session, RemoteObserver obs) throws RemoteException;
    void removeRemoteObserver(Session session,RemoteObserver obs) throws RemoteException;
    boolean isMyTurn(Session session) throws RemoteException;
    boolean isLegal(Session session, String command) throws RemoteException;
    void sendCommand(Session session, String command) throws RemoteException;
    String getStatus() throws RemoteException;
}
