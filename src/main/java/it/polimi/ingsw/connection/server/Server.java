package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.rmi.*;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;


public class Server {
    public static void main(String[] args) throws AlreadyBoundException, RemoteException, MalformedURLException {
        ServerRMI server = new ServerRMI();
        server.addSkeleton("Login", new ConcreteLobby());
        System.out.println("You can find the exposes object at: " + server.getURL()+"<name_of_the_object>");
    }
}