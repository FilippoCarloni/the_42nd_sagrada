package it.polimi.ingsw.connection.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoteClient implements Runnable{
    private Socket client;
    private String line;
    private Scanner in;
    private PrintWriter out;
    private Logger logger= Logger.getLogger(ServerThread.class.getName());
    public RemoteClient(Socket s) {
        client=s;
    }
    @Override
    public void run() {
        try {
            in=new Scanner(client.getInputStream());
            out=new PrintWriter(client.getOutputStream());

            do{
                line=in.nextLine();
                logger.info(() ->"Client send: "+line);
                out.println("Received:"+line.toUpperCase());
                out.flush();
            }while(!line.equals("quit"));
            in.close();
            out.close();
            client.close();
            logger.info("Client disconnection");
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Connection of new client over socket error",e);
        }
        catch (Exception e) {
            logger.log(Level.SEVERE,"Anomaly disconnection",e);
        }
    }
}
