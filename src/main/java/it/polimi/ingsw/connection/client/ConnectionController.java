package it.polimi.ingsw.connection.client;

import it.polimi.ingsw.connection.costraints.Settings;
import it.polimi.ingsw.connection.rmi.GameManager;
import it.polimi.ingsw.connection.rmi.Lobby;
import java.io.*;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ConnectionController extends UnicastRemoteObject implements RemoteObserver{
    private transient GameManager gameManger;
    private transient String sessionID;
    private transient Lobby lobby;
    private transient Socket client;
    private transient PrintWriter out;
    private transient Scanner in;
    private ConnectionType connectionType;
    private transient MessageBuffer messages;

    private class ReaderThread implements Runnable {

        @Override
        public void run() {
            boolean open = true;
            String line;
            while (open) {
                try {
                    line = in.nextLine();
                    messages.add(line);
                } catch (Exception e) {
                   messages.add("Disconnected");
                    System.exit(0);
                }
                synchronized (this) {
                    open = !client.isClosed();
                }
            }
        }
    }

    public ConnectionController(ConnectionType connectionType) throws Exception {
        super();
        sessionID = "";
        gameManger = null;
        lobby = null;
        this.connectionType=connectionType;
        initialize();
        messages=new MessageBuffer();
    }

    private void initialize() {
        if (connectionType == ConnectionType.RMI)
            rmiConnection();
        else
            socketConnection();
    }

    public boolean restore(String username){
        if(!sessionID.equals(""))
            return true;
        String response;
        ClientStatus status;
        username = username.trim();
        status = ClientStatus.restoreClientStatus(username);
        if (status != null) {
            sessionID = status.getSesssion();
            if (lobby != null)
                try {
                    sessionID = lobby.restoreSession(sessionID, this);
                } catch (RemoteException e) {
                   // messages.add(e.getMessage()
                    sessionID = "";
                }
            else {
                this.out.println("restore " + sessionID);
                this.out.flush();
                response = this.in.nextLine();
                if (response.split(" ")[0].equals("NewSessionID:"))
                    sessionID = response.split(" ")[1];
                else {
                   // messages.add(response)
                    sessionID = "";
                }
            }
        }

        if (sessionID.equals(""))
            login(username);
        if(sessionID.equals(""))
            return false;
        status = new ClientStatus(sessionID, username);
        ClientStatus.saveStatus(status);
        if (lobby == null) {
            new Thread(new ReaderThread()).start();
        }
        return true;
    }

    private void rmiConnection() {
        try {
            Registry reg = LocateRegistry.getRegistry(new Settings().IP_SERVER, new Settings().RMI_PORT);
            lobby = (Lobby) reg.lookup("Login");

        } catch (NotBoundException|RemoteException e) {
           messages.add(e.getMessage());
        }

    }

    private void socketConnection() {
        try {
            client = new Socket(new Settings().IP_SERVER, new Settings().SOCKET_PORT);
            in = new Scanner(client.getInputStream());
            out = new PrintWriter(client.getOutputStream());
        } catch (IOException ex) {
            messages.add(ex.toString());
        }
    }

    private void login(String name) {
        String response;
        if (lobby != null)
            try {
                sessionID = lobby.connect(name, this);
            } catch (RemoteException e) {
               // messages.add(e.getMessage())
            }
        else {
            this.out.println("login " + name);
            this.out.flush();
            response = this.in.nextLine();
            if (response.split(" ")[0].equals("SessionID:"))
                sessionID = response.split(" ")[1];
            else {
                //messages.add(response)
                sessionID = "";
            }

        }
    }

    public String readMessage(){
        return messages.getNext();
    }

    @Override
    public void remoteUpdate(Object observable, Object o) throws RemoteException {
        if (o instanceof String)
            messages.add((String) o);
    }

    @Override
    public boolean isAlive() throws RemoteException {
        return true;
    }

    public void send(String cmd) {
        try {
            switch (cmd ) {
                case "?":
                    messages.add("Commands still need to be added ;)");
                    break;
                case "view":
                    if (lobby != null) {
                        if (gameManger != null)
                            messages.add(gameManger.getStatus(sessionID));
                        else
                            messages.add("You are not playing");
                    } else {
                        out.println("view");
                        out.flush();
                    }
                    break;
                case "exit":
                    if (lobby == null) {
                        out.println("quit");
                        out.flush();
                        synchronized (this){
                            try {
                                client.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    messages.add("Good Bye!");
                    System.exit(0);
                    break;
                case "play":
                    if (lobby != null)
                        gameManger = lobby.getGame(sessionID);
                    else {
                        out.println("play");
                        out.flush();
                    }
                    break;
                default:
                    if (lobby != null) {
                        if (gameManger != null)
                            if (!gameManger.isMyTurn(sessionID))
                                messages.add("It's not your turn, please wait while the other players make their moves.");
                            else
                                gameManger.sendCommand(sessionID, cmd);
                        else
                            messages.add("You are not playing");
                    } else {
                        out.println("action " + cmd);
                        out.flush();
                    }
            }
        } catch (RemoteException e) {
            messages.add(e.getMessage());
        }
    }

}