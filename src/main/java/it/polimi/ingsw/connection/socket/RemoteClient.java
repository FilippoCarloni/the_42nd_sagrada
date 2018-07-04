package it.polimi.ingsw.connection.socket;

import it.polimi.ingsw.connection.server.CentralServer;
import it.polimi.ingsw.connection.server.GameController;
import it.polimi.ingsw.connection.server.GameObserver;
import it.polimi.ingsw.connection.server.messageencoder.MessageType;
import it.polimi.ingsw.connection.server.serverexception.ServerException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.ingsw.connection.costraints.Settings.ANONYMOUS;
import static it.polimi.ingsw.connection.costraints.ConnectionCommands.*;

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
    RemoteClient(Socket serverSocket,CentralServer lobby) {
        sessionID=ANONYMOUS;
        client=serverSocket;
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
                cmd=line.split(COMMAND_SEPARATOR);
                if(cmd.length>0)
                    switch (cmd[0]) {
                        case WINDOW_COMMAND:
                            if(game!=null)
                            if(cmd.length==2) {
                                try {
                                    game.setMap(sessionID, Integer.parseInt(cmd[1]));
                                }catch (NumberFormatException e) {

                                }
                            }
                            else
                                send(MessageType.encodeMessage("you are not playing",MessageType.ERROR_MESSAGE));
                            break;
                        case RESTORE_COMMAND:
                            if(sessionID.equals(ANONYMOUS)) {
                                if (cmd.length == 2) {
                                    try {
                                       sessionID=lobby.restoreSession(cmd[1],this);
                                       send(MessageType.encodeMessage(sessionID,MessageType.SESSION));
                                    }catch(ServerException e){
                                        send(MessageType.encodeMessage(e.getMessage(),MessageType.ERROR_MESSAGE));
                                    }
                                }else
                                    send(MessageType.encodeMessage("No username is passed!",MessageType.GENERIC_MESSAGE));
                            }
                            else
                                send(MessageType.encodeMessage("You are already logged",MessageType.ERROR_MESSAGE));
                            break;

                        case LOGIN_COMMAND:
                            if(sessionID.equals(ANONYMOUS)) {
                                if (cmd.length == 2) {
                                    try {
                                    sessionID = lobby.connect(cmd[1],this);
                                    send(MessageType.encodeMessage(sessionID,MessageType.SESSION));
                                    }catch (ServerException e) {
                                        send(MessageType.encodeMessage(e.getMessage(),MessageType.ERROR_MESSAGE));
                                    }
                                }
                                else
                                    send(MessageType.encodeMessage("Spaces are not allowed!",MessageType.ERROR_MESSAGE));
                            }
                            else
                                send (MessageType.encodeMessage("You are already logged",MessageType.ERROR_MESSAGE));
                            break;
                        case PLAY_COMMAND:
                            new Thread(() -> {
                                if(!sessionID.equals(ANONYMOUS)) {
                                    try {
                                        game = lobby.getGame(sessionID).getGameController();
                                    } catch (Exception e) {
                                        logger.info(e.getMessage());
                                    }
                                }else
                                    send (MessageType.encodeMessage("You are not already logged",MessageType.ERROR_MESSAGE));
                            }).start();
                            break;
                        case VIEW_COMMAND:
                            try {
                                if (game != null)
                                    send(game.getStatus(sessionID));
                                else
                                    send(MessageType.encodeMessage("You are not playing", MessageType.ERROR_MESSAGE));
                            }catch (ServerException e){
                                send(MessageType.encodeMessage(e.getMessage(), MessageType.ERROR_MESSAGE));
                            }
                            break;
                        case ACTION_COMMAND:
                            if(game != null) {
                                for (i = 1; i < cmd.length; i++) {
                                    action = action.concat(COMMAND_SEPARATOR + cmd[i]);
                                }
                                try {
                                    if (!game.isMyTurn(sessionID))
                                        send(MessageType.encodeMessage("It's not your turn, please wait while the other players make their moves.", MessageType.GENERIC_MESSAGE));
                                    else
                                        game.sendCommand(sessionID, action);
                                }catch (Exception e) {
                                    send(MessageType.encodeMessage(e.getMessage(), MessageType.ERROR_MESSAGE));
                                }
                                action = "";
                            }
                            else
                                send(MessageType.encodeMessage("You are not playing",MessageType.ERROR_MESSAGE));
                            break;
                        case QUIT_COMMAND:
                            break;
                        default:
                            send(MessageType.encodeMessage("Command not recognized",MessageType.ERROR_MESSAGE));
                            break;

                    }
            }while(!line.equals(QUIT_COMMAND));
            in.close();
            out.close();
            client.close();
            logger.info(() -> sessionID+" disconnection");
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
