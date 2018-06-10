package it.polimi.ingsw.connection.client;

import it.polimi.ingsw.connection.costraints.Settings;
import it.polimi.ingsw.connection.rmi.GameManager;
import it.polimi.ingsw.connection.rmi.Lobby;
import it.polimi.ingsw.connection.server.messageencoder.MessageType;

import java.io.*;
import java.net.ConnectException;
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
    private transient ConnectionType connectionType;
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

    public ConnectionController(ConnectionType connectionType) throws ConnectException,RemoteException {
        super();
        sessionID = "";
        gameManger = null;
        lobby = null;
        this.connectionType=connectionType;
        initialize();
        messages=new MessageBuffer();
    }

    private void initialize() throws ConnectException {
        if (connectionType == ConnectionType.RMI)
            rmiConnection();
        else
            if(connectionType==ConnectionType.SOCKET)
            socketConnection();
        else
            throw new ConnectException("No available connection type");
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
            if (connectionType==ConnectionType.RMI)
                try {
                    sessionID = lobby.restoreSession(sessionID, this);
                } catch (RemoteException e) {
                   // messages.add(e.getMessage()
                    sessionID = "";
                }
            else
                if(connectionType==ConnectionType.SOCKET) {
                    this.out.println("restore " + sessionID);
                    this.out.flush();
                    response = this.in.nextLine();
                    if (MessageType.decodeMessageType(response)==MessageType.GENERIC_MESSAGE&&MessageType.decodeMessageContent(response).split(" ")[0].equals("NewSessionID:"))
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
        if(!ClientStatus.saveStatus(status))
            return false;
        if (lobby == null) {
            new Thread(new ReaderThread()).start();
        }
        return true;
    }

    private void rmiConnection() throws ConnectException {
        try {
            Registry reg = LocateRegistry.getRegistry(new Settings().IP_SERVER, new Settings().RMI_PORT);
            lobby = (Lobby) reg.lookup("Login");

        } catch (NotBoundException|RemoteException e) {
           throw new ConnectException(e.getMessage());
        }

    }

    private void socketConnection() throws ConnectException {
        try {
            client = new Socket(new Settings().IP_SERVER, new Settings().SOCKET_PORT);
            in = new Scanner(client.getInputStream());
            out = new PrintWriter(client.getOutputStream());
        } catch (IOException ex) {
            throw new ConnectException(ex.getMessage());
        }
    }

    private void login(String name) {
        String response;
        if (connectionType==ConnectionType.RMI)
            try {
                sessionID = lobby.connect(name, this);
            } catch (RemoteException e) {
               // messages.add(e.getMessage())
            }
        else
            if(connectionType==ConnectionType.SOCKET) {
                this.out.println("login " + name);
                this.out.flush();
                response = this.in.nextLine();
                if (MessageType.decodeMessageType(response)==MessageType.GENERIC_MESSAGE&&MessageType.decodeMessageContent(response).split(" ")[0].equals("SessionID:"))
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
        else
            throw new RemoteException("Invalid message");
    }

    @Override
    public boolean isAlive() throws RemoteException {
        return true;
    }

    public void send(String cmd) {
        try {
            switch (cmd ) {
                case "?":
                    messages.add(MessageType.encodeMessage("Commands still need to be added ;)",MessageType.GENERIC_MESSAGE));
                    break;
                case "view":
                    if (lobby != null) {
                        if (gameManger != null)
                            messages.add(gameManger.getStatus(sessionID));
                        else
                            messages.add(MessageType.encodeMessage("You are not playing",MessageType.ERROR_MESSAGE));
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
                                messages.add(MessageType.encodeMessage("disconnection error",MessageType.ERROR_MESSAGE));
                            }
                        }
                    }
                    messages.add(MessageType.encodeMessage("Good Bye!",MessageType.GENERIC_MESSAGE));
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
                        if (gameManger != null) {
                            if (!gameManger.isMyTurn(sessionID))
                                messages.add(MessageType.encodeMessage("It's not your turn, please wait while the other players make their moves.",MessageType.GENERIC_MESSAGE));
                            else
                                gameManger.sendCommand(sessionID, cmd);
                        }
                        else
                            messages.add(MessageType.encodeMessage("You are not playing",MessageType.ERROR_MESSAGE));
                    } else {
                        out.println("action " + cmd);
                        out.flush();
                    }
            }
        } catch (RemoteException e) {
            messages.add(e.getCause().getMessage());
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}