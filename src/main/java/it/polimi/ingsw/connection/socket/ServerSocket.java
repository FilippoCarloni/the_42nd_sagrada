package it.polimi.ingsw.connection.socket;


import it.polimi.ingsw.connection.rmi.Lobby;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.ingsw.connection.costraints.Settings.SOCKET_PORT;

public class ServerSocket implements Runnable{

    private java.net.ServerSocket server;
    private Socket client;
    private ExecutorService th;
    private Lobby lobby;
    private Logger logger= Logger.getLogger(ServerSocket.class.getName());
    private int numError;

    public ServerSocket(Lobby lobby) throws IOException {
        client = null;
        this.lobby=lobby;
        server = new java.net.ServerSocket(SOCKET_PORT);
        th = Executors.newCachedThreadPool();
        numError = 0;
    }
    @Override
    public void run() {
        logger.info(() -> "Server thread started, the server is reachable through socket connection at the port: "+SOCKET_PORT);
        while(numError<10) {
            try {
                client = server.accept();
                logger.info("A client thread is started");
                th.execute(new RemoteClient(client,lobby));
            } catch (IOException e) {
                numError++;
                logger.log(Level.SEVERE, "Connection of new client over sockete error", e);
            }
        }
        logger.log(Level.SEVERE,"Max number of error in the server");
        close();
    }

    private void close() {
        try {
            server.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Connection of new client over sockete error", e);
        }
    }

}
