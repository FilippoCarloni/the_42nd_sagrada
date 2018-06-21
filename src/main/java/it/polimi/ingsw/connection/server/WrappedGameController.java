package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.rmi.ConcreteGameManager;
import it.polimi.ingsw.connection.rmi.GameManager;
import it.polimi.ingsw.connection.server.serverexception.ServerException;

import java.rmi.RemoteException;
import java.util.List;

import static it.polimi.ingsw.connection.server.serverexception.ErrorCode.RMI_ERROR;

public class WrappedGameController{
    private GameManager remoteGame;
    private GameController gameController;

    WrappedGameController(CentralServer server,List<OnLinePlayer> players) throws ServerException {
        this.gameController=new GameController(server, players);
        try {
            this.remoteGame=new ConcreteGameManager(gameController);
        } catch (RemoteException e) {
            throw new ServerException(e.getMessage(),RMI_ERROR);
        }
    }
    public GameManager getRemoteGame() {
        return remoteGame;
    }

    public GameController getGameController() {
        return gameController;
    }
}