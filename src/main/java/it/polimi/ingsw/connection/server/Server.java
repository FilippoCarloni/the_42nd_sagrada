package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.rmi.*;
import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.GameStatus;
import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;


public class Server {
    public static void main(String[] args) throws AlreadyBoundException, RemoteException, MalformedURLException {
        ServerRMI server = new ServerRMI();
        server.addSkeleton("Login", new ConcreteLobby());
        System.out.println("You can find the exposes object at: " + server.getURL()+"<name_of_the_object>");
    }
}