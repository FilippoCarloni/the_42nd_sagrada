package it.polimi.ingsw.connection.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static it.polimi.ingsw.connection.costraints.Settings.SOCKET_PORT;

public class ClientSocket {
    static Socket client;
    public static void main(String args[]) {
        PrintWriter out;
        Scanner stdin;
        Scanner in;
        String line;
        try {
            client=new Socket("127.0.0.1",SOCKET_PORT);
            in=new Scanner(client.getInputStream());
            out=new PrintWriter(client.getOutputStream());
            stdin=new Scanner(System.in);
            System.out.println("Send a string to server, it respond to you!(send quit to exit)");

            do {
                line=stdin.nextLine();
                out.println(line);
                out.flush();
                System.out.println(in.nextLine());

            }while (!line.equals("quit"));
            client.close();
    	    in.close();
    	    out.close();
        }catch( IOException ex) {

            System.out.println(ex.toString());
        }
    }
}
