package it.polimi.ingsw.connection.client;

import it.polimi.ingsw.connection.rmi.GameManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ConnectionController extends UnicastRemoteObject implements RemoteObserver, Observer {

    private transient GameManager gameManger;
    private transient CLI view;
    private transient String sessionID;

    ConnectionController(GameManager gameManger, String sessionID) throws RemoteException {
        this.sessionID=sessionID;
        this.gameManger = gameManger;
        view = new CLI(this );
        gameManger.addRemoteObserver(sessionID, this);
        new Thread(view).start();
    }

    @Override
    public void remoteUpdate(Object observable, Object o) throws RemoteException {
        if(o instanceof String)
        view.update((String) o);
    }

    @Override
    public boolean isAlive() throws RemoteException {
        return true;
    }

    @Override
    public void update(Observable observable, Object o) {
        String cmd = o.toString();
        try{
            switch (cmd) {
                case "?":
                    view.update("Commands still need to be added ;)");
                    break;
                case "view":
                    view.update(gameManger.getStatus(sessionID));
                    break;
                case "exit":
                    gameManger.removeRemoteObserver(sessionID,this);
                    view.update("Good Bye!");
                    System.exit(0);
                    break;
                default:
                    if (!gameManger.isMyTurn(sessionID))
                            view.update("It's not your turn, please wait while the other players make their moves.");
                    else {
                        gameManger.sendCommand(sessionID, cmd);
                    }

            }
        }catch (RemoteException e) {
            view.update(e.getMessage());
        }
    }
}