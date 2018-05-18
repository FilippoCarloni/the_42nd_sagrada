package it.polimi.ingsw.connection.rmi;

import it.polimi.ingsw.connection.client.RemoteObserver;
import it.polimi.ingsw.connection.server.GameController;
import it.polimi.ingsw.connection.server.GameObserver;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Observable;
import java.util.logging.Logger;

public class ConcreteGameManager extends UnicastRemoteObject implements GameManager {

    private static class WrappedObs extends Observable implements GameObserver, Serializable {
        private  RemoteObserver ro;

        WrappedObs(RemoteObserver ro) {
            this.ro = ro;
        }

        @Override
        public void update(Observable o, Object arg) {
            try {
                ro.remoteUpdate(this, arg);
                System.out.println("ciao");
            } catch (RemoteException e) {
                logger.info(() -> "Remote exception update observer:" +e.getMessage()+ this);
                o.deleteObserver(this);
            }
        }

        void removeRemoteObserver(Observable o) {
            o.deleteObserver(this);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof WrappedObs && ((WrappedObs) obj).ro.equals(ro);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean isAlive() {
            boolean isAlive = true;
            try {
                ro.isAlive();
            } catch (RemoteException e) {
                isAlive=false;
            }
            return isAlive;
        }
    }
    private static final transient Logger logger=Logger.getLogger(ConcreteGameManager.class.getName());
    private final transient GameController gameController;
    public ConcreteGameManager(GameController gameController) throws RemoteException{
        if(gameController == null)
            throw new RemoteException("Null pointer!");
        this.gameController=gameController;
    }

    @Override
    public void addRemoteObserver(String sessionID, RemoteObserver obs) throws RemoteException {
        try {
            gameController.addGameObserver(sessionID, new WrappedObs(obs));
        }catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void removeRemoteObserver(String sessionID, RemoteObserver obs) throws RemoteException {

    }

    @Override
    public boolean isMyTurn(String sessionID) throws RemoteException {
        try {
            return gameController.isMyTurn(sessionID);
        }catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void sendCommand(String sessionID, String command) throws RemoteException {
        try {
            gameController.sendCommand(sessionID, command);
        }catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public String getStatus(String sessionID) throws RemoteException {
        try {
            return gameController.getStatus(sessionID);
        }catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

}
