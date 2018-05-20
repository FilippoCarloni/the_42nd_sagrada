package it.polimi.ingsw.connection.client;

import it.polimi.ingsw.connection.costraints.Settings;
import it.polimi.ingsw.connection.rmi.GameManager;
import it.polimi.ingsw.connection.rmi.Lobby;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import static it.polimi.ingsw.connection.costraints.Settings.SOCKET_PORT;

public class ConnectionController extends UnicastRemoteObject implements RemoteObserver, Observer {
    private transient GameManager gameManger;
    private final transient CLI view;
    private transient String sessionID;
    private transient Lobby lobby;
    private transient Socket client;
    private transient PrintWriter out;
    private transient Scanner in;
    private class ReaderThread implements Runnable {

        @Override
        public void run() {
            boolean open = true;
            String line;
            while (open) {
                try {
                    line=in.nextLine();
                    view.update(line);

                } catch (Exception e) {
                    System.out.println("Disconnected");
                    System.exit(0);
                }
                synchronized (client) {
                    open = !client.isClosed();
                }
            }
        }
    }

    ConnectionController() throws Exception {
        super();
        this.sessionID="";
        this.gameManger = null;
        lobby=null;
        initialize();
        view = new CLI(this );
        new Thread(view).start();
    }
    private void initialize() {
        Scanner scanner= new Scanner(System.in);
        String response,name;
        ClientStatus status;

        System.out.println("Insert the connection method: [1]RMI    [2] Socket");
        response=scanner.nextLine();
        if(response.equals("1"))
            rmiConnection();
        else {
            socketConnection();
        }
        System.out.println("Insert which username you would restore, if it is possible the session is restored");
        response=scanner.nextLine();
        name=response.trim();
        status=ClientStatus.restoreClientStatus(name);
        if (status != null) {
            System.out.println("Restoring session of " + status.getUsername());
            sessionID = status.getSesssion();
            if (lobby != null)
                try {
                    sessionID = lobby.restoreSession(sessionID, this);
                }catch (RemoteException e) {
                System.out.println(e.getMessage());
                sessionID="";
                }
            else {
                this.out.println("restore " + sessionID);
                this.out.flush();
                response = this.in.nextLine();
                if (response.split(" ")[0].equals("NewSessionID:"))
                    sessionID = response.split(" ")[1];
                else {
                    System.out.println(response);
                    sessionID="";
                }
            }
        }

        if(sessionID.equals("")) {
            login(name);
            while (sessionID.equals("")) {
                name = scanner.nextLine();
                login(name);
            }
        }
        status=new ClientStatus(sessionID, name);
        ClientStatus.saveStatus(status);
        if(lobby==null) {
          new Thread(new ReaderThread()).start();
        }
        System.out.println("Logged");

    }
    private void rmiConnection(){
        try {
            lobby = (Lobby) Naming.lookup("rmi://localhost:" + Settings.RMI_PORT + "/Login");
    } catch (NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

    }
    private void socketConnection() {
        try {
            client = new Socket("127.0.0.1", SOCKET_PORT);
            in = new Scanner(client.getInputStream());
            out = new PrintWriter(client.getOutputStream());
        } catch (IOException ex) {

            System.out.println(ex.toString());
        }
    }
    private void login(String name) {
        String response;
        System.out.println("Trying a new login with: "+name);
        if(lobby != null)
            try {
                sessionID = lobby.connect(name, this);
                System.out.println("pa");
            }catch (RemoteException e) {
                System.out.println(e.getMessage());
            }
        else {
            this.out.println("login "+name);
            this.out.flush();
            response=this.in.nextLine();
            if(response.split(" ")[0].equals("SessionID:"))
                sessionID=response.split(" ")[1];
            else {
                System.out.println(response);
                sessionID="";
            }

        }
    }

    @Override
    public void remoteUpdate(Object observable, Object o) throws RemoteException {
        synchronized (view) {
            if (o instanceof String)
                view.update((String) o);
        }
    }

    @Override
    public boolean isAlive() throws RemoteException {
        return true;
    }

    @Override
    public void update(Observable observable, Object o) {
        String cmd = o.toString();
        try{
            switch (cmd) {
                case "?":
                    view.update("Commands still need to be added ;)");
                    break;
                case "view":
                    if (lobby!=null) {
                        view.update(gameManger.getStatus(sessionID));
                    }else {
                        out.println("view");
                        out.flush();
                    }
                    break;
                case "exit":
                    if(lobby==null){
                        out.println("quit");
                        out.flush();
                    }
                    view.update("Good Bye!");
                    System.exit(0);
                    break;
                case "play":
                    if(lobby!=null)
                        gameManger=lobby.getGame(sessionID);
                    else{
                        out.println("play");
                        out.flush();
                    }
                    break;
                default:
                    if (lobby!=null) {
                        if (!gameManger.isMyTurn(sessionID))
                            view.update("It's not your turn, please wait while the other players make their moves.");
                        else {
                            gameManger.sendCommand(sessionID, cmd);
                        }
                    }
                    else{
                        out.println("action "+cmd);
                        out.flush();
                    }
            }
        }catch (RemoteException e) {
            view.update(e.getMessage());
        }
    }

}