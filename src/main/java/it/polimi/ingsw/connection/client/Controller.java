package it.polimi.ingsw.connection.client;


import it.polimi.ingsw.connection.rmi.GameManger;
import it.polimi.ingsw.connection.server.Session;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Controller extends UnicastRemoteObject implements RemoteObserver, Observer {

    private GameManger gameManger;
    private CLI view;
    private Session session;

    Controller(GameManger gameManger, Session session) throws RemoteException {
        this.session = session;
        this.gameManger = gameManger;
        view = new CLI(this );
        gameManger.addRemoteObserver(this);
        new Thread(view).start();
        getTurn();
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
                case "check":
                    boolean ans = gameManger.isLegal(this.session, "");
                    if (ans)
                        view.update("Correct move");
                    else view.update("Illegal move");
                    break;
                case "cmd":
                    gameManger.sendCommand(this.session, "");
                    break;
                case "view":
                    view.update(gameManger.getStatus());
                    break;
                case "exit":
                    gameManger.removeRemoteObserver(this);
                    view.update("Good Bye!");
                    System.exit(0);
                    break;
                default:
                    view.update("Not supported command!");
            }
        }catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void getTurn() {
        try {
            if(gameManger.isMyTurn(session))
                System.out.println("It's your turn.");
            else
                System.out.println("It's not your turn, please wait.");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
