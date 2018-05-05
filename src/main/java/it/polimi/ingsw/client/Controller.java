package it.polimi.ingsw.client;


import it.polimi.ingsw.connection.rmi.GameManger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Controller extends UnicastRemoteObject implements RemoteObserver,Observer {

    private GameManger model;
    private CLI view;
    private boolean isMyturn;
    Controller(GameManger model) throws RemoteException {
        this.model = model;
        isMyturn=false;
        view=new CLI(this );
        model.addRemoteObserver(this);
        new Thread(view).start();
        getTurn();
    }
    @Override
    public void remoteUpdate(Object observable, Object o) throws RemoteException {
        view.update("New value: "+model.getData());
       if(model.myTurn(this))
            view.update("Is your turn!");

    }

    @Override
    public void update(Observable observable, Object o) {
        String cmd[]=o.toString().split(" ");
     try{

              switch (cmd[0]) {
                  case "set":

                     if(model.myTurn(this)){
                          model.setData(o.toString().split(" ")[cmd.length-1]);
                      } else {
                          view.update( "It's not your turn");
                      }
                      break;
                  case "view":
                      view.update("current value: "+model.getData());
                  break;
                  case "exit":
                      model.removeRemoteObserver(this);
                      view.update("Good Bye!");
                      System.exit(0);
                      break;
                  default:
                      view.update("Not supported command!");
              }

        }catch (RemoteException e) {

        }

    }
    private void getTurn() {
        try {
            if(model.myTurn(this))
                System.out.println("Is your turn");
            else
                System.out.println("Is not your turn, please wait your turn");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}