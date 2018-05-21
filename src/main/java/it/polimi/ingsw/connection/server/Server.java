package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.rmi.ConcreteLobby;
import it.polimi.ingsw.connection.rmi.Lobby;
import it.polimi.ingsw.connection.rmi.ServerRMI;
import it.polimi.ingsw.connection.socket.ServerSocket;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.logging.Logger;


public class Server {
    public static void main(String[] args) throws  AlreadyBoundException, RemoteException, IOException {
        Logger logger = Logger.getLogger(Server.class.getName());
        ServerRMI server = new ServerRMI();
        CentralServer centralServer=new CentralServer();
        Lobby lobby=new ConcreteLobby(centralServer);
        server.addSkeleton("Login", lobby);
        logger.info(() -> ("You can find the exposes object at: " + server.getURL() + "<name_of_the_object>"));
        new Thread(new ServerSocket(centralServer)).start();
    }
}