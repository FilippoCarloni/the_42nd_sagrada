package it.polimi.ingsw.connection.client;

import it.polimi.ingsw.connection.costraints.Settings;
import it.polimi.ingsw.connection.rmi.GameManager;
import it.polimi.ingsw.connection.rmi.Lobby;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientRMI {
    public static void main(String args[]){
        GameManager model;
        Lobby login;
        String name;
        String sessionID="";
        ClientStatus status=null;
        Scanner s=new Scanner(System.in);
        FileInputStream fileIn=null;
        ObjectInputStream in=null;
        FileOutputStream fileOut=null;
        ObjectOutputStream out=null;
        System.out.println("Insert which username would restore, if it is possible(this function will be removed)");
        name=s.nextLine();
        try {

            login = (Lobby) Naming.lookup("rmi://localhost:" + Settings.RMI_PORT + "/Login");
            try {
                fileIn = new FileInputStream("./ClientStatus_"+name+".ser");
                in = new ObjectInputStream(fileIn);
                status = (ClientStatus) in.readObject();
            } catch (IOException i) {

                System.out.println("A previous game session of "+name+" session not found");
            } catch (ClassNotFoundException c) {
                System.out.println("A previous game session not found");
                return;
            } finally {
                try {
                    if(in!=null)
                        in.close();
                    if(fileIn!=null)
                        fileIn.close();
                } catch (IOException e) {

                }
            }
            if (status != null) {
                System.out.println("Restoring session of " + status.getUsername());
                sessionID = status.getSesssion();
                login.restoreSession(sessionID);
            } else {
                System.out.println("Trying a new login");
                try {
                    sessionID = login.connect(name);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                while (sessionID.equals("")) {
                    name = s.nextLine();
                    try {
                        sessionID = login.connect(name);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
                status=new ClientStatus(sessionID, name);
                try {
                    fileOut = new FileOutputStream("./ClientStatus_"+name+".ser");
                    out= new ObjectOutputStream(fileOut);
                    out.writeObject(status);
                    out.close();
                    fileOut.close();
                } catch (IOException i) {

                }
                finally {
                    try {
                        if(out!=null)
                            out.close();
                        if(fileOut!= null)
                        fileOut.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            System.out.println("Logged");
            model=(GameManager) login.getGame(sessionID);
            ConnectionController controller = new ConnectionController(model, sessionID);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

    }
}
