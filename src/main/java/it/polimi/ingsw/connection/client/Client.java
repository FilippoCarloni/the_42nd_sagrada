package it.polimi.ingsw.connection.client;

import it.polimi.ingsw.connection.costraints.Settings;
import it.polimi.ingsw.connection.rmi.GameManger;
import it.polimi.ingsw.connection.rmi.Lobby;
import it.polimi.ingsw.connection.server.Session;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class  Client {
    public static void main(String args[]){
        GameManger model;
        Session session;
        Lobby login;
        String name;
        Scanner s=new Scanner(System.in);
        try {

            login= (Lobby) Naming.lookup("rmi://localhost:"+Settings.PORT +"/Login");
            System.out.println("Insert your username and wait other player");
            name=s.nextLine();
            session = login.connect(name);
            while(!session.isValid()){
                System.out.println("this name already exists");
                name=s.nextLine();
                session = login.connect(name);
            }
            model=(GameManger) login.getGame(session);

            Controller controller = new Controller(model, session);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

    }
}
