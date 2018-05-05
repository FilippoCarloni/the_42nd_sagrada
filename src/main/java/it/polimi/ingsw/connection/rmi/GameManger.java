package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.client.RemoteObserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameManger extends Remote {
    void addRemoteObserver(RemoteObserver obs) throws RemoteException;
    void removeRemoteObserver(RemoteObserver obs) throws RemoteException;
    boolean myTurn(RemoteObserver obs) throws RemoteException;
    void setData(String data)  throws RemoteException;
    String getData() throws RemoteException;
}
