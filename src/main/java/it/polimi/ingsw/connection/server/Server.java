package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.rmi.ConcreteLobby;
import it.polimi.ingsw.connection.rmi.Lobby;
import it.polimi.ingsw.connection.rmi.ServerRMI;
import it.polimi.ingsw.connection.socket.ServerSocket;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.util.logging.Logger;

/**
 * The Server class runs all the process to start a Server.
 */
public class Server {

    /**
     * Used to run the socket server and the rmi server.
     * @param args Unused, the server setting are in the config file.
     * @throws AlreadyBoundException Throws it if the server is already bind in the configured network ports.
     * @throws IOException Throws it if there are problem in binding phase of the server socket.
     */
    public static void main(String[] args) throws  AlreadyBoundException, IOException {
        Logger logger = Logger.getLogger(Server.class.getName());
        ServerRMI server = new ServerRMI();
        CentralServer centralServer=new CentralServer();
        Lobby lobby=new ConcreteLobby(centralServer);
        server.addSkeleton("Login", lobby);
        logger.info(() -> ("You can find the exposes object at: " + server.getURL() + "<name_of_the_object>"));
        new Thread(new ServerSocket(centralServer)).start();
    }
}