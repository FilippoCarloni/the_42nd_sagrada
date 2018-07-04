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

import static it.polimi.ingsw.connection.costraints.ServerMessage.*;
import static it.polimi.ingsw.connection.costraints.Settings.ANONYMOUS;
import static it.polimi.ingsw.connection.costraints.ConnectionCommands.*;

public class RemoteClient implements Runnable,GameObserver {
    private Socket client;
    private PrintWriter out;
    private CentralServer lobby;
    private GameController game;
    private String sessionID;
    private Logger logger= Logger.getLogger(ServerSocket.class.getName());
    private boolean isALive;
    private boolean gameRequested;
    RemoteClient(Socket serverSocket,CentralServer lobby) {
        sessionID=ANONYMOUS;
        client=serverSocket;
        this.lobby=lobby;
        gameRequested = false;
        isALive=true;
    }
    @Override
    public void run() {
        Scanner in;
        String line;
        String []cmd;
        try {
            in=new Scanner(client.getInputStream());
            out=new PrintWriter(client.getOutputStream());
            do{
                line=in.nextLine();
                if(line.trim().length()>0) {
                    String finalLine = line;
                    logger.info(() ->sessionID+" send: "+ finalLine);
                }
                cmd=line.split(COMMAND_SEPARATOR);
                if(cmd.length>0)
                    switch (cmd[0]) {
                        case WINDOW_COMMAND:
                            setWindow(cmd);
                           break;
                        case RESTORE_COMMAND:
                            restore(cmd);
                            break;
                        case LOGIN_COMMAND:
                            login(cmd);
                            break;
                        case PLAY_COMMAND:
                            play();
                            break;
                        case VIEW_COMMAND:
                            view();
                            break;
                        case ACTION_COMMAND:
                            action(cmd);
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
            logger.info(() -> sessionID + DISCONNECTED);
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Connection of new client over socket error",e);
        }
        catch (Exception e) {
            logger.log(Level.SEVERE,sessionID+" Anomaly disconnection");
        }
        isALive=false;
    }

    private void play(){
        if (!sessionID.equals(ANONYMOUS)) {
            if (!gameRequested) {
                gameRequested = true;
                new Thread(() -> {
                    try {
                        game = lobby.getGame(sessionID).getGameController();
                    } catch (ServerException e) {
                        logger.info(e.getMessage());
                    } finally {
                        gameRequested = false;
                    }

                }).start();
            }
        } else
                send(MessageType.encodeMessage(NOT_LOGGED, MessageType.ERROR_MESSAGE));
    }
    private void action(String []cmd){
        String action="";
        if(game != null) {
            for (int i = 1; i < cmd.length; i++) {
                action = action.concat(COMMAND_SEPARATOR + cmd[i]);
            }
            try {
                if (!game.isMyTurn(sessionID))
                    send(MessageType.encodeMessage(NOT_YOUR_TURN, MessageType.GENERIC_MESSAGE));
                else
                    game.sendCommand(sessionID, action);
            }catch (Exception e) {
                send(MessageType.encodeMessage(e.getMessage(), MessageType.ERROR_MESSAGE));
            }
        }
        else
            send(MessageType.encodeMessage(NOT_PLAYING,MessageType.ERROR_MESSAGE));

    }
    private void login(String []cmd) {
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
    }
    private void restore(String []cmd){
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
    }
    private void view(){
        try {
            if (game != null)
                send(game.getStatus(sessionID));
            else
                send(MessageType.encodeMessage(NOT_PLAYING, MessageType.ERROR_MESSAGE));
        }catch (ServerException e) {
            send(MessageType.encodeMessage(e.getMessage(), MessageType.ERROR_MESSAGE));
        }
    }
    /**
     * Sets the window for the game in which is enrolled the player.
     * @param cmd - Command send from the client.
     */
    private void setWindow(String []cmd) {
        if(game!=null)
            if(cmd.length==2) {
                try {
                    game.setMap(sessionID, Integer.parseInt(cmd[1]));
                }catch (ServerException | NumberFormatException e) {
                    send(MessageType.encodeMessage(NOT_VALID_NUMBER, MessageType.ERROR_MESSAGE));
                }
            }
            else
                send(MessageType.encodeMessage(NOT_PLAYING,MessageType.ERROR_MESSAGE));

    }

    /**
     * Sends a message over tcp connection to client.
     * @param msg - Message to send to the client.
     */
    private void send(String msg) {
        out.println(msg);
        out.flush();
    }

    /**
     * Permits to monitor if it is in live.
     * @return - A boolean that represents if it is alive or not.
     */
    @Override
    public synchronized boolean isAlive() {
        return isALive;
    }

    /**
     * Updates the client.
     * @param o - The Observable object that generates the updated.
     * @param arg - The message to send.
     */
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof String )
        send((String) arg);
    }
}
