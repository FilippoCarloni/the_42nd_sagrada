package it.polimi.ingsw.connection.socket;

import it.polimi.ingsw.connection.server.CentralServer;
import it.polimi.ingsw.connection.server.GameController;
import it.polimi.ingsw.connection.server.GameObserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.ingsw.connection.costraints.Settings.ANONYMOUS;

public class RemoteClient implements Runnable,GameObserver {
    private Socket client;
    private String action;
    private Scanner in;
    private PrintWriter out;
    private CentralServer lobby;
    private GameController game;
    private String sessionID;
    private String line;
    private Logger logger= Logger.getLogger(ServerSocket.class.getName());
    private boolean isALive;
    RemoteClient(Socket s,CentralServer lobby) {
        sessionID=ANONYMOUS;
        client=s;
        this.lobby=lobby;
        action="";
        isALive=true;
    }
    @Override
    public void run() {
        String []cmd;
        int i;
        try {
            in=new Scanner(client.getInputStream());
            out=new PrintWriter(client.getOutputStream());
            do{
                line=in.nextLine();
                if(line.trim().length()>0)
                    logger.info(() ->sessionID+" send: "+line);
                cmd=line.split(" ");
                if(cmd.length>0)
                    switch (cmd[0]) {
                        case "restore":
                            if(sessionID.equals(ANONYMOUS)) {
                                if (cmd.length == 2) {
                                    try {
                                       sessionID=lobby.restoreSession(cmd[1],this);
                                       send("NewSessionID: "+sessionID);
                                    }catch(Exception e){
                                        send(e.getMessage());
                                    }
                                }else
                                    send("No username is passed!");
                            }
                            else
                                send("You are already logged");
                            break;

                        case "login":
                            if(sessionID.equals(ANONYMOUS)) {
                                if (cmd.length == 2) {
                                    try {
                                    sessionID = lobby.connect(cmd[1],this);
                                    send("SessionID: "+sessionID);
                                    }catch (Exception e) {
                                        send(e.getMessage());
                                    }
                                }
                            }
                            else
                                send ("You are already logged");
                            break;
                        case "play":
                            if(!sessionID.equals(ANONYMOUS)) {
                                if(game==null) {
                                    send("Waiting others players ...");
                                }
                                else
                                    send("Restoring game");
                                game = lobby.getGame(sessionID).getGameController();
                                send(game.getStatus(sessionID));
                            }else
                                send ("You are not already logged");
                            break;
                        case "view":
                            if(game!=null)
                                send(game.getStatus(sessionID));
                            else
                                send("You are not playing");
                            break;
                        case "action":
                            if(game != null) {
                                for (i = 1; i < cmd.length; i++) {
                                    action = action.concat(" " + cmd[i]);
                                }
                               if (!game.isMyTurn(sessionID))
                                    send("It's not your turn, please wait while the other players make their moves.");
                                else {
                                    try {
                                        game.sendCommand(sessionID, action);
                                    }catch (Exception e) {
                                        send(e.getMessage());
                                    }
                                }
                                action = "";
                            }
                            else
                                send("You are not playing");
                            break;
                        case "quit":
                            break;
                        default:
                            send("Command not recognized");
                            break;

                    }
            }while(!line.equals("quit"));
            in.close();
            out.close();
            client.close();
            logger.info(sessionID+" disconnection");
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Connection of new client over socket error",e);
        }
        catch (Exception e) {
            logger.log(Level.SEVERE,sessionID+" Anomaly disconnection");
        }
        isALive=false;
    }
    private void send(String msg) {
        out.println(msg);
        out.flush();
    }

    @Override
    public synchronized boolean isAlive() {
        return isALive;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof String )
        send((String) arg);
    }
}
