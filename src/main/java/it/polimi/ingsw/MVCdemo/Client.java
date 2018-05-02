package it.polimi.ingsw.MVCdemo;

;

import it.polimi.ingsw.connection.costraints.Settings;
import it.polimi.ingsw.connection.rmi.ModelInt;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class  Client {
    public static void main(String args[]){
        ModelInt model;
        try {
            model=(ModelInt) Naming.lookup("rmi://localhost:"+Settings.PORT +"/Model");
            Controller controller = new Controller(model);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
