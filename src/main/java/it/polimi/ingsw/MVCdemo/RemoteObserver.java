package it.polimi.ingsw.MVCdemo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteObserver extends Remote {

    void remoteUpdate(Object observable, Object updateMsg) throws RemoteException;
}