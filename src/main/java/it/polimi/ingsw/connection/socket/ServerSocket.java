package it.polimi.ingsw.connection.socket;


import it.polimi.ingsw.connection.costraints.Settings;
import it.polimi.ingsw.connection.server.CentralServer;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.ingsw.connection.costraints.ServerMessage.NEW_SOCKET_CLIENT_THREAD;
import static it.polimi.ingsw.connection.costraints.ServerMessage.SOCKET_SERVER_ERROR;
import static it.polimi.ingsw.connection.costraints.ServerMessage.SOCKET_SERVER_MESSAGE;

/**
 * The ServerSocket class listen in a file configured port and manage all the connection over TCP.
 */
public class ServerSocket implements Runnable{

    private java.net.ServerSocket server;
    private Socket client;
    private ExecutorService th;
    private CentralServer lobby;
    private Logger logger= Logger.getLogger(ServerSocket.class.getName());

    /**
     * Creates a new ServerSocket.
     * @param lobby - The CentralServer reference.
     * @throws IOException - Threows a IOException if tehre are issues in the Socket creation phase.
     */
    public ServerSocket(CentralServer lobby) throws IOException {
        client = null;
        this.lobby=lobby;
        server = new java.net.ServerSocket(new Settings().SOCKET_PORT);
        th = Executors.newCachedThreadPool();
    }

    /**
     * Listens continuously for new client connection, start a new thread for each new connection.
     * if the are issues the method stops and close the socket connection of the server.
     */
    @Override
    public void run() {
        boolean noError = true;
        logger.info(() -> SOCKET_SERVER_MESSAGE+new Settings().SOCKET_PORT);
        while(noError) {
            try {
                client = server.accept();
                logger.info(NEW_SOCKET_CLIENT_THREAD);
                th.execute(new RemoteClient(client,lobby));
            } catch (IOException e) {
                noError = false;
                logger.log(Level.SEVERE, SOCKET_SERVER_ERROR, e);
            }
        }
        close();
    }

    /**
     * Closes the Server socket connection.
     */
    private void close() {
        try {
            server.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, SOCKET_SERVER_ERROR, e);
        }
    }

}
