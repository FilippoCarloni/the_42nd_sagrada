package it.polimi.ingsw.connection.client;


import it.polimi.ingsw.connection.rmi.GameManager;
import it.polimi.ingsw.connection.server.Session;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Controller extends UnicastRemoteObject implements RemoteObserver, Observer {

    private transient GameManager gameManger;
    private transient CLI view;
    private transient Session session;

    Controller(GameManager gameManger, Session session) throws RemoteException {
        this.session = session;
        this.gameManger = gameManger;
        view = new CLI(this );
        gameManger.addRemoteObserver(session, this);
        new Thread(view).start();
    }

    @Override
    public void remoteUpdate(Object observable, Object o) throws RemoteException {
        view.update(gameManger.getStatus());
        if(gameManger.isMyTurn(session))
            view.update("It's your turn!");
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
                    view.update(gameManger.getStatus());
                    break;
                case "exit":
                    gameManger.removeRemoteObserver(session,this);
                    view.update("Good Bye!");
                    System.exit(0);
                    break;
                default:
                    if (!gameManger.isMyTurn(session))
                            view.update("It's not your turn, please wait while the other players make their moves.");
                        else {
                            boolean legal = gameManger.isLegal(session, cmd);
                            if (!legal) {
                                view.update("Illegal command, please check if the syntax is correct.");
                            } else {
                                gameManger.sendCommand(session, cmd);
                            }
                    }
            }
        }catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
