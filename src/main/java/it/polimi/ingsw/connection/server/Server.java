package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.socket.ServerThread;
import it.polimi.ingsw.connection.rmi.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.logging.Logger;


public class Server {
    public static void main(String[] args) throws  AlreadyBoundException, RemoteException, MalformedURLException,IOException {
        Logger logger= Logger.getLogger(Server.class.getName());
        ServerRMI server = new ServerRMI();
        server.addSkeleton("Login", new ConcreteLobby());
        logger.info(() -> ("You can find the exposes object at: " + server.getURL()+"<name_of_the_object>"));
        new Thread(new ServerThread()).start();
    }
}