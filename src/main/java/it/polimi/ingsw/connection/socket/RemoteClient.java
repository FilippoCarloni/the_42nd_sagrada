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

/**
 * the R
 */
public class RemoteClient implements Runnable,GameObserver {
    private Socket client;
    private PrintWriter out;
    private CentralServer  centralServer;
    private GameController game;
    private String sessionID;
    private Logger logger= Logger.getLogger(ServerSocket.class.getName());
    private boolean isALive;
    private boolean gameRequested;

    /**
     * Creates a new RemoteClient.
     * @param serverSocket - the socket connection with the client.
     * @param centralServer - References to the CentralServer instance.
     */
    RemoteClient(Socket serverSocket,CentralServer centralServer) {
        sessionID=ANONYMOUS;
        client=serverSocket;
        this.centralServer=centralServer;
        gameRequested = false;
        isALive=true;
    }

    /**
     * Continuosly waits the client for new action to perform.
     * If fthe are issues with the clien connection the method stops.
     */
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
                            send(MessageType.encodeMessage(COMMAND_NOT_RECOGNIZED,MessageType.ERROR_MESSAGE));
                            break;

                    }
            }while(!line.equals(QUIT_COMMAND));
            in.close();
            out.close();
            client.close();
            logger.info(() -> sessionID + DISCONNECTED);
        } catch (IOException e) {
            logger.log(Level.SEVERE,SOCKET_ERROR,e);
        }
        catch (Exception e) {
            logger.log(Level.SEVERE,sessionID+SOCKET_ANOMALY_DISCONNECTION);
        }
        isALive=false;
    }

    /**
     * Ask a game to the CentralServer.
     */
    private void play() {
        if (!sessionID.equals(ANONYMOUS)) {
            synchronized (this) {
                if (!gameRequested) {
                    gameRequested = true;
                    new Thread(() -> {
                        try {
                            game = centralServer.getGame(sessionID).getGameController();
                        } catch (ServerException e) {
                            logger.info(e.getMessage());
                        } finally {
                            synchronized (this) {
                                gameRequested = false;
                            }
                        }

                    }).start();
                }
            }
        } else
            send(MessageType.encodeMessage(NOT_LOGGED, MessageType.ERROR_MESSAGE));
    }
    /**
     * Does an specific action in the match in which the player is actually enrolled.
     * @param cmd - ConnectionCommand containing the specific action to perform.
     */
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
            }catch (ServerException e) {
                send(MessageType.encodeMessage(e.getMessage(), MessageType.ERROR_MESSAGE));
            }
        }
        else
            send(MessageType.encodeMessage(NOT_PLAYING,MessageType.ERROR_MESSAGE));

    }

    /**
     * Logs the user with a OnLinePlayer session.
     * @param cmd - ConnectionCommand to login.
     */
    private void login(String []cmd) {
        if(sessionID.equals(ANONYMOUS)) {
            if (cmd.length == 2) {
                try {
                    sessionID = centralServer.connect(cmd[1],this);
                    send(MessageType.encodeMessage(sessionID,MessageType.SESSION));
                }catch (ServerException e) {
                    send(MessageType.encodeMessage(e.getMessage(),MessageType.ERROR_MESSAGE));
                }
            }
            else
                send(MessageType.encodeMessage(SOCKET_WRONG_ARGUMENT,MessageType.ERROR_MESSAGE));
        }
        else
            send (MessageType.encodeMessage(ALREADY_LOGGED,MessageType.ERROR_MESSAGE));
    }

    /**
     * Restores a old OnLinePlayer session.
     * @param cmd - ConnectionCommand to restore a session.
     */
    private void restore(String []cmd){
        if(sessionID.equals(ANONYMOUS)) {
            if (cmd.length == 2) {
                try {
                    sessionID=centralServer.restoreSession(cmd[1],this);
                    send(MessageType.encodeMessage(sessionID,MessageType.SESSION));
                }catch(ServerException e){
                    send(MessageType.encodeMessage(e.getMessage(),MessageType.ERROR_MESSAGE));
                }
            }else
                send(MessageType.encodeMessage(SOCKET_WRONG_ARGUMENT,MessageType.GENERIC_MESSAGE));
        }
        else
            send(MessageType.encodeMessage(ALREADY_LOGGED,MessageType.ERROR_MESSAGE));
    }

    /**
     * Sends the game status of the match in which the player is actually enrolled.
     */
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
