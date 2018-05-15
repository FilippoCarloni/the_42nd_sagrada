package it.polimi.ingsw.connection.socket;

import it.polimi.ingsw.connection.rmi.GameManager;
import it.polimi.ingsw.connection.rmi.Lobby;
import it.polimi.ingsw.connection.server.Session;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoteClient implements Runnable{
    private Socket client;
    private String action;
    private Scanner in;
    private PrintWriter out;
    private Lobby lobby;
    private GameManager game;
    private Session session;
    private String line;
    private Logger logger= Logger.getLogger(ServerSocket.class.getName());
    RemoteClient(Socket s,Lobby lobby) {
        client=s;
        this.lobby=lobby;
        action="";
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
                    logger.info(() ->"Client send: "+line);
                cmd=line.split(" ");
                logger.info(line);
                if(cmd.length>0)
                    switch (cmd[0]) {
                        case "restore":
                            if(session==null||!session.isValid()) {
                                if (cmd.length == 2) {
                                    session=new Session(cmd[1]);
                                    try {
                                       session=lobby.restoreSession(session);
                                       send("NewSessionID:"+session.getID());
                                    }catch(RemoteException e){
                                        send(e.getMessage());
                                        session=null;
                                    }
                                }else
                                    send("No username is passed!");
                            }
                            else
                                send("You are already logged");
                            break;

                        case "login":
                            if(session==null||!session.isValid()) {
                                if (cmd.length == 2) {
                                    session = lobby.connect(cmd[1]);
                                    if (session.isValid())
                                        send("SessionID:"+session.getID());
                                    else
                                        send("username already used");
                                }
                            }
                            else
                                send("You are already logged");
                            break;
                        case "play":
                            send("Waiting others players ...");
                            game=lobby.getGame(session);
                            send(game.getStatus()+"");
                            break;
                        case "view":
                            if(game!=null)
                                send(game.getStatus());
                            else
                                send("You are not playing");
                            break;
                        case "action":
                            if(game != null) {
                                for (i = 1; i < cmd.length; i++) {
                                    action = action.concat(" " + cmd[i]);
                                }
                                if (!game.isMyTurn(session))
                                    send("It's not your turn, please wait while the other players make their moves.");
                                else {
                                    boolean legal = game.isLegal(session, action.trim());
                                    if (!legal) {
                                        send("Illegal command, please check if the syntax is correct.");
                                    } else {
                                        game.sendCommand(session, action);
                                    }
                                }
                                action = "";
                            }
                            else
                                send("You are not playing");
                            break;
                        default:
                            send("Command not recognized");
                            break;

                    }
            }while(!line.equals("quit"));
            in.close();
            out.close();
            client.close();
            logger.info("Client disconnection");
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Connection of new client over socket error",e);
        }
        catch (Exception e) {
            logger.log(Level.SEVERE,"Anomaly disconnection");
        }
    }
    private void send(String msg) {
        out.println(msg);
        out.flush();
    }
}
