package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.server.GameController;
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
        }catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void sendCommand(String sessionID, String command) throws RemoteException {
        logger.info(()->"Command <"+command+"> send from: "+sessionID);
        try{
            gameController.sendCommand(sessionID, command);
        }catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public String getStatus(String sessionID) throws RemoteException {
        logger.info(()->"Status request from: "+sessionID);
        try {
            return gameController.getStatus(sessionID);
        }catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

}
