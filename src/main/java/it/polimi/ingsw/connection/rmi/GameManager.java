package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.client.RemoteObserver;
import it.polimi.ingsw.connection.server.Session;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameManager extends Remote {

    void addRemoteObserver(String sessionID, RemoteObserver obs) throws RemoteException;
    void removeRemoteObserver(String sessionID,RemoteObserver obs) throws RemoteException;
    boolean isMyTurn(String sessionID) throws RemoteException;
    void sendCommand(String sessionID, String command) throws RemoteException;
    String getStatus(String sessionID) throws RemoteException;
}
