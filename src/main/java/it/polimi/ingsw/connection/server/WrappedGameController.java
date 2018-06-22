package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.rmi.ConcreteGameManager;
import it.polimi.ingsw.connection.rmi.GameManager;
import it.polimi.ingsw.connection.server.serverexception.ServerException;

import java.rmi.RemoteException;
import java.util.List;

import static it.polimi.ingsw.connection.server.serverexception.ErrorCode.RMI_ERROR;

/**
 * The WrappedGameController class contains a GameManager a GameController.
 * This class is used to contain the references to a specific match.
 */
public class WrappedGameController{
    private GameManager remoteGame;
    private GameController gameController;

    /**
     * Creates a new WrappedGameController.
     * Also creates a new match and a remote match(rmi).
     * @param server The references of the CentralServer instance.
     * @param players The player of the match.
     * @throws ServerException If there are errors creating a new match.
     */
    WrappedGameController(CentralServer server,List<OnLinePlayer> players) throws ServerException {
        this.gameController=new GameController(server, players);
        try {
            this.remoteGame=new ConcreteGameManager(gameController);
        } catch (RemoteException e) {
            throw new ServerException(e.getMessage(),RMI_ERROR);
        }
    }

    /**
     * Used by rmi classes.
     * @return A GameManager, the rmi reference of the match.
     */
    public GameManager getRemoteGame() {
        return remoteGame;
    }

    /**
     * The direct references to the match.
     * @return A GameController
     */
    public GameController getGameController() {
        return gameController;
    }
}