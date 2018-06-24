package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connection.client.ConnectionController;
import it.polimi.ingsw.connection.client.ConnectionType;
import it.polimi.ingsw.connection.server.messageencoder.MessageType;
import it.polimi.ingsw.view.gui.gameboard.GameBoardController;
import it.polimi.ingsw.view.gui.preliminarystages.LobbyController;
import it.polimi.ingsw.view.gui.preliminarystages.LoginController;
import it.polimi.ingsw.view.gui.preliminarystages.WindowFramesChoice;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static jdk.nashorn.internal.objects.Global.print;

/**
 * This class helps managing the Connection Controller and the updating of information.
 * It has to be unique for all the fxml scenes
 */

//TODO: add an error message to print if server is offline, instead of launching a simple NullPointerException (into the GuiManager
//TODO: singleton constructor)

public class GuiManager {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final int REFRESH_RATE = 100;    //milliseconds
    private ConnectionController connectionController;
    private String usernamePlayer1;
    private LobbyController lobbyController;
    private WindowFramesChoice windowFramesChoice;
    private GameBoardController gameBoard;          //I will use it to update the game board
    private JSONObject preGameMessage;
    private JSONObject gameBoardMessage = null;
    private static GuiManager guiManagerInstance;
    private static ConnectionType myConnectionType = ConnectionType.RMI;

    //Scheduler
    public void startRefresh() {
        scheduler.scheduleAtFixedRate(this::update, 1, REFRESH_RATE, MILLISECONDS);
    }

    //Updater, managed by the scheduler
    private void update(){
        try {
            String message = getInstance().connectionController.readMessage();
            System.out.println(message);
            if(message.length() > 0) {
                switch (MessageType.decodeMessageType(message)) {
                    case GENERIC_MESSAGE:
                        if(gameBoard == null)
                            lobbyController.printConnectionOrDisconnection(MessageType.decodeMessageContent(message));
                        if(gameBoard != null)
                            gameBoard.setMessageText(MessageType.decodeMessageContent(message) + "\n");
                        break;
                    case GAME_BOARD:
                        if(gameBoardMessage == null && windowFramesChoice != null)
                            windowFramesChoice.getGameBoardButton().setDisable(false);
                        gameBoardMessage = (JSONObject) new JSONParser().parse(MessageType.decodeMessageContent(message));
                        if(gameBoard != null)
                            gameBoard.gameBoardUpdate(gameBoardMessage);
                        break;
                    case ERROR_MESSAGE:
                        gameBoard.setMessageText(MessageType.decodeMessageContent(message) + "\n");
                        break;
                    case PRE_GAME_CHOICE:
                        lobbyController.getStartButton().setDisable(false);
                        preGameMessage = (JSONObject) new JSONParser().parse(MessageType.decodeMessageContent(message));
                        break;
                    case CURRENT_PLAYER:
                        if(gameBoard != null)
                            gameBoard.setMessageText("Now is playing: " + MessageType.decodeMessageContent(message) + "\n");
                        break;
                    default:
                        print("Message not supported!");
                }
            }
        }
        catch(Exception e) {
            print(e.getMessage());
        }
    }

    //Getter
    public ConnectionController getConnectionController(){
        return connectionController;
    }
    public String getUsernamePlayer1(){
        return usernamePlayer1;
    }
    public JSONObject getPreGameMessage(){
        return preGameMessage;
    }
    public JSONObject getGameBoardMessage(){
        return gameBoardMessage;
    }
    public GameBoardController getGameBoard(){
        return gameBoard;
    }

    //Setter
    public void setUsernamePlayer1(String usernamePlayer1){
        this.usernamePlayer1 = usernamePlayer1;
    }
    public void setLobbyController(LobbyController lobbyController){
        this.lobbyController = lobbyController;
    }
    public void setWindowFramesChoice(WindowFramesChoice windowFramesChoice){
        this.windowFramesChoice = windowFramesChoice;
    }
    public void setGameBoard(GameBoardController gameBoard){
        this.gameBoard = gameBoard;
    }

    //Singleton constructor for unique Connection Controller
    public static void setConnectionType(ConnectionType connectionType){
        myConnectionType = connectionType;
    }
    public static GuiManager getInstance() throws ConnectException, RemoteException{
        if(guiManagerInstance == null){
            guiManagerInstance = new GuiManager(myConnectionType);
        }
        return guiManagerInstance;
    }
    private GuiManager(ConnectionType connectionType) throws ConnectException, RemoteException{
        connectionController = new ConnectionController(connectionType);
    }
}
