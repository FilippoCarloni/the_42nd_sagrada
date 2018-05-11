package it.polimi.ingsw.connection.socket;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.ingsw.connection.costraints.Settings.SOCKET_PORT;

public class ServerThread implements Runnable{

    private ServerSocket server;
    private Socket client;
    private Scanner in;
    private PrintWriter out;
    private ExecutorService th;
    private Logger logger= Logger.getLogger(ServerThread.class.getName());
    private int numError;

    public ServerThread() throws IOException {
        in = null;
        client = null;
        server = new ServerSocket(SOCKET_PORT);
        th = Executors.newCachedThreadPool();
        numError = 0;
    }
    @Override
    public void run() {
        logger.info(() -> "Server thread started, the server is reachable through socket connection at the port: "+SOCKET_PORT);
        while(numError<10) {
            try {
                client = server.accept();
                in = new Scanner(client.getInputStream());
                out = new PrintWriter(client.getOutputStream());
                logger.info("A client thread is started");
                th.execute(new RemoteClient(client));
            } catch (IOException e) {
                numError++;
                logger.log(Level.SEVERE, "Connection of new client over sockete error", e);
            }
        }
        logger.log(Level.SEVERE,"Max number of error in the server");
    }
    public String readLine() {
        return in.nextLine();
    }
    public void write(String input) {
        out.println(input);
        out.flush();
    }
    public void close() throws IOException {
        server.close();
    }

}
