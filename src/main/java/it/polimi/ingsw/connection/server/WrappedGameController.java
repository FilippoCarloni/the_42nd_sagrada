package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.rmi.ConcreteGameManager;
import it.polimi.ingsw.connection.rmi.GameManager;

import java.rmi.RemoteException;
import java.util.List;

public class WrappedGameController{
    private GameManager remoteGame;
    private GameController gameController;
    private ConcreteGameManager game;

    WrappedGameController(List<WrappedPlayer> players) throws RemoteException {
        this.gameController=new GameController(players);
        this.game=new ConcreteGameManager(gameController);
        this.remoteGame=game;
    }

    public GameManager getRemoteGame() {
        return remoteGame;
    }

    public ConcreteGameManager getGame() {
        return game;
    }
    public GameController getGameController() {
        return gameController;
    }
}