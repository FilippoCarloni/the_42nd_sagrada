package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.MVCdemo.RemoteObserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ModelInt extends Remote {
    void addRemoteObserver(RemoteObserver o) throws RemoteException;
    void removeRemoteObserver(RemoteObserver obs) throws RemoteException;
    boolean myTurn(RemoteObserver obs) throws RemoteException;
    void setData(String data)  throws RemoteException;
    String getData() throws RemoteException;
}

