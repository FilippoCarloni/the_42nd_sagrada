package it.polimi.ingsw.connection.client;

import it.polimi.ingsw.connection.constraints.Settings;
import it.polimi.ingsw.connection.rmi.GameManager;
import it.polimi.ingsw.connection.rmi.Lobby;
import it.polimi.ingsw.connection.server.ServerSession;
import it.polimi.ingsw.connection.server.messageencoder.MessageType;

import java.io.*;
import java.net.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import static it.polimi.ingsw.connection.constraints.ServerMessages.*;
import static it.polimi.ingsw.connection.constraints.ConnectionCommands.*;

/**
 * The Connectionmanageer class is the references for the connection.
 * In particular it permits to send and receive messages over a network communication independently by the connection type chose.
 */
public class ConnectionManager implements RemoteObserver, Serializable {
    private transient GameManager gameManger;
    private transient String sessionID;
    private transient Lobby lobby;
    private transient Socket client;
    private transient PrintWriter out;
    private transient Scanner in;
    private transient ConnectionType connectionType;
    private transient MessageBuffer messages;
    private transient RemoteObserver clientRMI;

    /**
     *  Internal class to continuously read thee input from the server over socket.
     */
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
                    messages.add(MessageType.encodeMessage(SERVER_DISCONNECTION_ERROR,MessageType.ERROR_MESSAGE));
                    open = false;
                }
                if(open)
                    synchronized (this) {
                        open = !client.isClosed();
                    }
            }
        }
    }

    /**
     * Creates a new ConnectionManager with a specific ConnectionType.
     * @param connectionType - Connection type for the communication.
     * @throws ConnectException - Throws ConenctionException if there are issues.
     */
    public ConnectionManager(ConnectionType connectionType) throws ConnectException {
        super();
        sessionID = ServerSession.INVALID_SESSION_ID;
        gameManger = null;
        lobby = null;
        this.connectionType = connectionType;
        initializeConnection();
        messages = new MessageBuffer();
    }

    /**
     * Initializes the connection over a specific ConnectionType.
     * @throws ConnectException - Throws a ConnectionException if there are issues with the connection initializzation.
     */
    private void initializeConnection() throws ConnectException {
        if (connectionType == ConnectionType.RMI)
            rmiConnection();
        else if (connectionType == ConnectionType.SOCKET)
            socketConnection();
        else
            throw new ConnectException(NOT_AVAILABLE_CONNECTION_TYPE);
    }

    /**
     * Restores a Session or creates a new one from the username of the user.
     * @param username - The username to use to create a Session.
     * @return - If the creation of the Session is done correctly or not.
     */
    public boolean restore(String username) {
        boolean logged = false;
        if (!sessionID.equals(ServerSession.INVALID_SESSION_ID))
            return true;
        String response;
        ClientStatus status;
        username = username.trim();
        status = ClientStatus.restoreClientStatus(username);
        if (status != null) {
            if (connectionType == ConnectionType.RMI)
                try {
                    sessionID = lobby.restoreSession(status.getSession(), this);
                    logged = true;
                } catch (RemoteException e) {
                    // messages.add(e.getMessage()
                }
            else if (connectionType == ConnectionType.SOCKET) {
                this.out.println(RESTORE_COMMAND+ COMMAND_SEPARATOR + status.getSession());
                this.out.flush();
                response = this.in.nextLine();
                if (MessageType.decodeMessageType(response) == MessageType.SESSION) {
                    sessionID = MessageType.decodeMessageContent(response);
                    logged = true;
                }
            }
        }
        if (!logged && !login(username))
            return false;
        if (!ClientStatus.saveStatus(new ClientStatus(sessionID, username)))
            return false;
        if (connectionType == ConnectionType.SOCKET) {
            new Thread(new ReaderThread()).start();
        }
        return true;
    }

    /**
     * Cofnigure correcty a connection over RMI.
     * @throws ConnectException - throws an ConnectionException if there are issues in RMI configuration.
     */
    private void rmiConnection() throws ConnectException {
        String clientIP;
        try {
            if (InetAddress.getLocalHost().isLoopbackAddress() && !InetAddress.getByName(new Settings().serverIP).isLoopbackAddress()) {
                clientIP = new Settings().clientIP;
                if (InetAddress.getByName(clientIP).isLoopbackAddress())
                    throw new ConnectException(SERVER_DISCONNECTION_ERROR);
            }
            else
                clientIP = InetAddress.getLocalHost().getHostAddress();
            System.setProperty("java.rmi.server.hostname", clientIP);
        } catch (UnknownHostException e) {
            throw new ConnectException(e.getMessage());
        }
        try {
            Registry reg = LocateRegistry.getRegistry(new Settings().serverIP , new Settings().rmiPort);
            lobby = (Lobby) reg.lookup(Settings.LOBBY_RMI_ID);
        } catch (NotBoundException | RemoteException e) {
            throw new ConnectException(e.getMessage());
        }
        try {
            clientRMI = (RemoteObserver) UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            throw new ConnectException(e.getMessage());
        }
    }

    /**
     * Configure correctly a connection over socket.
     * @throws ConnectException - throws an ConnectionExpetion if there are issues in socket configuration.
     */
    private void socketConnection() throws ConnectException {
        try {
            client = new Socket(new Settings().serverIP , new Settings().socketPort);
            in = new Scanner(client.getInputStream());
            out = new PrintWriter(client.getOutputStream());
        } catch (IOException ex) {
            throw new ConnectException(ex.getMessage());
        }
    }

    /**
     * Trays to log in a server.
     * @param name - Name of the user to log.
     * @return - If the login is done correctly or not.
     */
    private boolean login(String name) {
        String response;
        if (connectionType == ConnectionType.RMI)
            try {
                sessionID = lobby.connect(name, clientRMI);
            } catch (RemoteException e) {
                return false;
            }
        else if (connectionType == ConnectionType.SOCKET) {
            this.out.println(LOGIN_COMMAND + COMMAND_SEPARATOR + name);
            this.out.flush();
            response = this.in.nextLine();
            if (MessageType.decodeMessageType(response) == MessageType.SESSION)
                sessionID = MessageType.decodeMessageContent(response);
            else {
                //messages.add(response)
                return false;
            }
        }
        return true;
    }

    /**
     * Reads the next message from the messages buffer.
     * @return - The next message from the messages buffer.
     */
    public String readMessage() {
        return messages.getNext();
    }

    /**
     * Sends an update to the RemoteObserver.
     * @param observable - The object that genrates the update.
     * @param updateMsg  The message to send.
     * @throws RemoteException - Throws if the are problems sending the update.
     */
    @Override
    public void remoteUpdate(Object observable, Object updateMsg) throws RemoteException {
        if (updateMsg instanceof String)
            messages.add((String) updateMsg);
        else
            throw new RemoteException(INVALID_MESSAGE);
    }

    /**
     * Pings the RemoteObserver to check if it is alive or not.
     * */
    @Override
    public void ping() {
        //Use only ping from the server
    }

    /**
     * Send a command to the server.
     * @param cmd - Contains the message to send to the server.
     */
    public void send(String cmd) {
        cmd = cmd.trim();
        String[] action = cmd.split(COMMAND_SEPARATOR);
        if (action.length > 0)
            try {
                switch (action[0]) {
                    case VIEW_COMMAND:
                        view();
                        break;
                    case QUIT_COMMAND:
                        quit();
                        break;
                    case PLAY_COMMAND:
                        play();
                        break;
                    case WINDOW_COMMAND:
                        setWindow(cmd);
                        break;
                    default:
                        action(cmd);
                }
            } catch (RemoteException e) {
                messages.add(e.getCause().getMessage());

            } catch (NoSuchElementException e) {
                messages.add(MessageType.encodeMessage(e.getCause().getMessage(), MessageType.ERROR_MESSAGE));
            }
    }

    /**
     * Do the play action over the preconfigured connection type.
     * @throws RemoteException - throws a RemoteException if there are issues.
     */
    private void play() throws RemoteException{
        if (connectionType == ConnectionType.RMI)
            gameManger = lobby.getGame(sessionID);
        else if (connectionType == ConnectionType.SOCKET) {
            out.println(PLAY_COMMAND);
            out.flush();
        }
    }

    /**
     * Do the game action over the preconfigured connection type.
     * @throws RemoteException - throws a RemoteException if there are issues.
     */
    private void action(String cmd) throws RemoteException  {
        if (connectionType == ConnectionType.RMI) {
            if (gameManger != null) {
                if (!gameManger.isMyTurn(sessionID))
                    messages.add(MessageType.encodeMessage(NOT_YOUR_TURN, MessageType.GENERIC_MESSAGE));
                else
                    gameManger.sendCommand(sessionID, cmd);
            } else
                messages.add(MessageType.encodeMessage(NOT_PLAYING, MessageType.ERROR_MESSAGE));
        } else if (connectionType == ConnectionType.SOCKET) {
            out.println(ACTION_COMMAND + COMMAND_SEPARATOR + cmd);
            out.flush();
        }
    }

    /**
     * Do the set window action over the preconfigured connection type.
     * @throws RemoteException - throws a RemoteException if there are issues.
     */
    private void setWindow(String cmd) throws RemoteException {
        String []action = cmd.split(COMMAND_SEPARATOR);
        if(action.length == 2)
            if (connectionType == ConnectionType.RMI) {
                if (gameManger != null) {
                    try {
                        gameManger.setMap(sessionID, Integer.parseInt(action[1]));
                    } catch (NumberFormatException e) {
                        send(MessageType.encodeMessage(NOT_VALID_NUMBER, MessageType.ERROR_MESSAGE));
                    }
                } else
                    messages.add(MessageType.encodeMessage(NOT_PLAYING, MessageType.ERROR_MESSAGE));
            } else if (connectionType == ConnectionType.SOCKET) {
                out.println(cmd);
                out.flush();
            }
    }

    /**
     * Do the view action over the preconfigured connection type.
     * @throws RemoteException - throws a RemoteException if there are issues.
     */
    private void view() throws RemoteException {
        if (connectionType == ConnectionType.RMI) {
            if (gameManger != null)
                messages.add(gameManger.getStatus(sessionID));
            else
                messages.add(MessageType.encodeMessage(NOT_PLAYING, MessageType.ERROR_MESSAGE));
        } else if (connectionType == ConnectionType.SOCKET) {
            out.println(VIEW_COMMAND);
            out.flush();
        }
    }

    /**
     * Closse the connection versus the server.
     */
    private void quit(){
        if (connectionType == ConnectionType.SOCKET) {
            out.println(QUIT_COMMAND);
            out.flush();
            synchronized (this) {
                try {
                    client.close();
                } catch (IOException e) {
                    messages.add(MessageType.encodeMessage(SERVER_DISCONNECTION_ERROR, MessageType.ERROR_MESSAGE));
                }
            }
        }
        messages.add(MessageType.encodeMessage(GOOD_BYE, MessageType.GENERIC_MESSAGE));
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