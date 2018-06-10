package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.server.GameController;
import it.polimi.ingsw.connection.server.messageencoder.MessageType;
import it.polimi.ingsw.connection.server.serverexception.ServerException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

public class ConcreteGameManager extends UnicastRemoteObject implements GameManager {


    private static final transient Logger logger=Logger.getLogger(ConcreteGameManager.class.getName());
    private final transient GameController gameController;
    public ConcreteGameManager(GameController gameController) throws RemoteException{
        if(gameController == null)
            throw new RemoteException("Null pointer!");
        this.gameController=gameController;
    }

    @Override
    public boolean isMyTurn(String sessionID) throws RemoteException {
        logger.info(() -> "Turn request from: "+sessionID);
        try {
            return gameController.isMyTurn(sessionID);
        }catch (ServerException e) {
            throw new RemoteException(MessageType.encodeMessage(e.getMessage(),MessageType.ERROR_MESSAGE));
        }
    }

    @Override
    public void sendCommand(String sessionID, String command) throws RemoteException {
        logger.info(()->"Command <"+command+"> send from: "+sessionID);
        try{
            gameController.sendCommand(sessionID, command);
        }catch (ServerException e) {
            throw new RemoteException(MessageType.encodeMessage(e.getMessage(),MessageType.ERROR_MESSAGE));
        }
    }

    @Override
    public String getStatus(String sessionID) throws RemoteException {
        logger.info(()->"Status request from: "+sessionID);
        try {
            return gameController.getStatus(sessionID);
        }catch (ServerException e) {
            throw new RemoteException(MessageType.encodeMessage(e.getMessage(),MessageType.ERROR_MESSAGE));
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
