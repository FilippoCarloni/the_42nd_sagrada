package it.polimi.ingsw.connection.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameManager extends Remote {

    boolean isMyTurn(String sessionID) throws RemoteException;
    void sendCommand(String sessionID, String command) throws RemoteException;
    String getStatus(String sessionID) throws RemoteException;
}
