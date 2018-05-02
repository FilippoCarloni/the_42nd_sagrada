package it.polimi.ingsw.MVCdemo;

import it.polimi.ingsw.connection.rmi.Model;
import it.polimi.ingsw.connection.rmi.ModelInt;
import it.polimi.ingsw.connection.rmi.ServerRMI;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class Server {
    public static void main(String[] args) throws AlreadyBoundException, RemoteException, MalformedURLException {
        ModelInt model;
        ServerRMI server = new ServerRMI();
        server.addSkeleton("Model", UnicastRemoteObject.exportObject(new Model(), 8002));
        System.out.println("You can find the exposes object at: " + server.getURL()+"<name_of_the_object>");
    }
}