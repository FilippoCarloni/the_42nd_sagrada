package it.polimi.ingsw.connection.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static it.polimi.ingsw.connection.costraints.Settings.SOCKET_PORT;

public class ClientSocket {
    static private class ReaderThread implements Runnable{

        @Override
        public void run() {
            boolean open = true;
            while (open) {
                try {
                    System.out.println(in.nextLine());
                }catch (Exception e){
                    System.out.println("Disconnected");
                    System.exit(0);
                }
                synchronized (client){
                    open=!client.isClosed();
                }
            }
        }
    }
    private static Socket client;
    private static PrintWriter out;
    private static Scanner stdin;
    private static Scanner in;
    private static String line;
    public static void main(String args[]) {
        try {
            client=new Socket("127.0.0.1",SOCKET_PORT);
            in=new Scanner(client.getInputStream());
            out=new PrintWriter(client.getOutputStream());
            new Thread(new ReaderThread()).start();
;           System.out.println("Send a string to server, it respond to you!(send quit to exit)");
            stdin=new Scanner(System.in);
            do{
                line=stdin.nextLine();
                out.println(line);
                out.flush();
            }while (!line.equals("quit"));
            synchronized (client) {
                client.close();
            }
    	    in.close();
    	    out.close();
        }catch( IOException ex) {

            System.out.println(ex.toString());
        }
    }
}
