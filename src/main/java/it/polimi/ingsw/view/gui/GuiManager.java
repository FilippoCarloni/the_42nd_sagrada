package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connection.client.ConnectionController;
import it.polimi.ingsw.connection.client.ConnectionType;
import it.polimi.ingsw.connection.server.messageencoder.MessageType;
import it.polimi.ingsw.view.gui.gameboard.GameBoardController;
import it.polimi.ingsw.view.gui.preliminarystages.LobbyController;
import it.polimi.ingsw.view.gui.preliminarystages.WindowFramesChoice;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
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
 */

public class GuiManager {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final int REFRESH_RATE = 100;    //milliseconds
    private ConnectionController connectionController;
    private String usernameMainPlayer;
    private LobbyController lobbyController;
    private WindowFramesChoice windowFramesChoice;
    private GameBoardController gameBoard;
    private JSONObject preGameMessage;
    private JSONObject gameBoardMessage;
    private String gameStatMessage;
    private static GuiManager guiManagerInstance;
    private static ConnectionType myConnectionType = ConnectionType.RMI;

    /**
     * Scheduler, it launch the update() method every REFRESH_RATE milliseconds
     */
    public void startRefresh() {
        scheduler.scheduleAtFixedRate(this::update, 1, REFRESH_RATE, MILLISECONDS);
    }

    /**
     *  Updater, managed by the scheduler. Main method for all GUI processes, he receive messages from the connection controller
     *  and switches between different cases, depending on the type of the message received.
     */
    private void update(){
        try {
            String message = getInstance().connectionController.readMessage();
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
                            gameBoard.setMessageText(GUIParameters.NOW_PLAYING + MessageType.decodeMessageContent(message) + "\n");
                        break;
                    case GAME_STATS:
                        gameBoard.getContinueButton().setVisible(true);
                        gameBoard.getContinueButton().setDisable(false);
                        gameStatMessage = MessageType.decodeMessageContent(message);
                        gameBoard.setMessageText(GUIParameters.END_GAME);
                    default:
                        print(GUIParameters.MESSAGE_ERROR);
                }
            }
        }
        catch(Exception e) {
            print(e.getMessage());
        }
    }

    /**
     * Getters for all needed references present in this class.
     */
    public ConnectionController getConnectionController(){
        return connectionController;
    }
    public String getUsernameMainPlayer(){
        return usernameMainPlayer;
    }
    public JSONObject getPreGameMessage(){
        return preGameMessage;
    }
    public JSONObject getGameBoardMessage(){
        return gameBoardMessage;
    }
    public String getGameStatMessage() {
        return gameStatMessage;
    }
    public GameBoardController getGameBoard(){
        return gameBoard;
    }
    public LobbyController getLobbyController() {
        return lobbyController;
    }

    /**
     * Setters for all variables present in this class.
     */
    public void setUsernameMainPlayer(String usernameMainPlayer){
        this.usernameMainPlayer = usernameMainPlayer;
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

    /**
     * Singleton constructor for unique Connection Controller; it has to be the same for all GUI scenes.
     * @param connectionType: type of connection chosen; used to set a new Connection Controller.
     */
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
        lobbyController = null;
        gameStatMessage = null;
        gameBoardMessage = null;
    }
}
