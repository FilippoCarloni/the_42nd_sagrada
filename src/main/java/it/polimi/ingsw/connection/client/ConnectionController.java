package it.polimi.ingsw.connection.client;

import it.polimi.ingsw.connection.costraints.Settings;
import it.polimi.ingsw.connection.rmi.GameManager;
import it.polimi.ingsw.connection.rmi.Lobby;
import it.polimi.ingsw.connection.server.ServerSession;
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
        sessionID = ServerSession.INVALID_SESSION_ID;
        gameManger = null;
        lobby = null;
        this.connectionType=connectionType;
        initializeConnection();
        messages=new MessageBuffer();
    }

    private void initializeConnection() throws ConnectException {
        if (connectionType == ConnectionType.RMI)
            rmiConnection();
        else
            if(connectionType==ConnectionType.SOCKET)
            socketConnection();
        else
            throw new ConnectException("No available connection type");
    }

    public boolean restore(String username){
        boolean logged=false;
        if(!sessionID.equals(ServerSession.INVALID_SESSION_ID))
            return true;
        String response;
        ClientStatus status;
        username = username.trim();
        status = ClientStatus.restoreClientStatus(username);
        if (status != null) {
            if (connectionType == ConnectionType.RMI)
                try {
                    sessionID = lobby.restoreSession(status.getSesssion(), this);
                    logged = true;
                } catch (RemoteException e) {
                    // messages.add(e.getMessage()
                }
            else if (connectionType == ConnectionType.SOCKET) {
                this.out.println("restore " + status.getSesssion());
                this.out.flush();
                response = this.in.nextLine();
                if (MessageType.decodeMessageType(response) == MessageType.SESSION) {
                    sessionID = MessageType.decodeMessageContent(response);
                    logged = true;
                }
            }
        }
            if(!logged&&!login(username))
                return false;
        if(!ClientStatus.saveStatus(new ClientStatus(sessionID, username)))
            return false;
        if (connectionType==ConnectionType.SOCKET) {
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

    private boolean login(String name) {
        String response;
        if (connectionType==ConnectionType.RMI)
            try {
                sessionID = lobby.connect(name, this);
            } catch (RemoteException e) {
               // messages.add(e.getMessage())
                return false;
            }
        else
            if(connectionType==ConnectionType.SOCKET) {
                this.out.println("login " + name);
                this.out.flush();
                response = this.in.nextLine();
                if (MessageType.decodeMessageType(response)==MessageType.SESSION)
                    sessionID = MessageType.decodeMessageContent(response);
                else {
                    //messages.add(response)
                    return false;
                }
        }
        return true;
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
        cmd=cmd.trim();
        String []action=cmd.split(" ");
        if(action.length>0)
        try {
            switch (action[0]) {
                case "?":
                    messages.add(MessageType.encodeMessage("Commands still need to be added ;)",MessageType.GENERIC_MESSAGE));
                    break;
                case "view":
                    if (connectionType==ConnectionType.RMI) {
                        if (gameManger != null)
                            messages.add(gameManger.getStatus(sessionID));
                        else
                            messages.add(MessageType.encodeMessage("You are not playing",MessageType.ERROR_MESSAGE));
                    } else if (connectionType==ConnectionType.SOCKET){
                        out.println("view");
                        out.flush();
                    }
                    break;
                case "exit":
                    if (connectionType==ConnectionType.SOCKET) {
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
                    if (connectionType==ConnectionType.RMI)
                        gameManger = lobby.getGame(sessionID);
                    else if (connectionType==ConnectionType.SOCKET){
                        out.println("play");
                        out.flush();
                    }
                    break;
                case "window":
                    if(action.length==2) {
                        if(connectionType==ConnectionType.RMI){
                            if (gameManger != null) {
                                try {
                                    gameManger.setMap(sessionID, Integer.parseInt(action[1]));
                                }catch (NumberFormatException e){

                                }
                            }
                            else
                                messages.add(MessageType.encodeMessage("You are not playing",MessageType.ERROR_MESSAGE));
                        } else if (connectionType==ConnectionType.SOCKET){
                            out.println(cmd);
                            out.flush();
                        }
                    }
                    break;
                default:
                    if (connectionType==ConnectionType.RMI) {
                        if (gameManger != null) {
                            if (!gameManger.isMyTurn(sessionID))
                                messages.add(MessageType.encodeMessage("It's not your turn, please wait while the other players make their moves.",MessageType.GENERIC_MESSAGE));
                            else
                                gameManger.sendCommand(sessionID, cmd);
                        }
                        else
                            messages.add(MessageType.encodeMessage("You are not playing",MessageType.ERROR_MESSAGE));
                    } else if(connectionType==ConnectionType.SOCKET){
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