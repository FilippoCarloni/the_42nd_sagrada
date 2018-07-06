package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connection.client.ConnectionManager;
import it.polimi.ingsw.connection.client.ConnectionType;
import it.polimi.ingsw.connection.server.messageencoder.MessageType;
import it.polimi.ingsw.view.gui.gameboard.GameBoardController;
import it.polimi.ingsw.view.gui.preliminarystages.LobbyController;
import it.polimi.ingsw.view.gui.preliminarystages.WindowFramesChoice;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.ConnectException;
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
    private ConnectionManager connectionController;
    private String usernameMainPlayer;
    private String nowPlaying;
    private LobbyController lobbyController;
    private WindowFramesChoice windowFramesChoice;
    private GameBoardController gameBoard;
    private JSONObject preGameMessage;
    private JSONObject gameBoardMessage;
    private JSONObject gameStatMessage;
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
                        if(lobbyController != null) {
                            lobbyController.printConnectionOrDisconnection(MessageType.decodeMessageContent(message));
                        }
                        if(gameBoard != null)
                            gameBoard.setMessageText(MessageType.decodeMessageContent(message) + "\n");
                        break;
                    case GAME_BOARD:
                        if(gameBoardMessage == null && windowFramesChoice != null)
                            windowFramesChoice.getGameBoardButton().setDisable(false);
                        gameBoardMessage = (JSONObject) new JSONParser().parse(MessageType.decodeMessageContent(message));
                        if(lobbyController != null && windowFramesChoice == null)
                            lobbyController.getStartButton().setDisable(false);
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
                        nowPlaying = GUIParameters.NOW_PLAYING + MessageType.decodeMessageContent(message) + "\n";
                        if(gameBoard != null)
                            gameBoard.setMessageText(nowPlaying);
                        break;
                    case GAME_STATS:
                        gameBoard.getContinueButton().setVisible(true);
                        gameBoard.getContinueButton().setDisable(false);
                        gameStatMessage = (JSONObject) new JSONParser().parse(MessageType.decodeMessageContent(message));
                        gameBoard.setMessageText(GUIParameters.END_GAME);
                        break;
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
     * Getter for the connection controller.
     * @return a reference to the Connection controller.
     */
    public ConnectionManager getConnectionController(){
        return connectionController;
    }

    /**
     * Getter for the username of the main player; used by every class into the preliminary stage to set into the title the
     * main player username, and by the game board to set the main player username over his window frame.
     * @return a String containing main player's username.
     */
    public String getUsernameMainPlayer(){
        return usernameMainPlayer;
    }

    /**
     * Getter for the pre game message, used by the window frame choice controller to draw the pre game screen
     * @return a JSONObject containing all information about the pre game screen to draw.
     */
    public JSONObject getPreGameMessage(){
        return preGameMessage;
    }

    /**
     * Getter for the game board message.
     * @return a JSONObject containing all information about the actual game state.
     */
    public JSONObject getGameBoardMessage(){
        return gameBoardMessage;
    }

    /**
     * Getter for the game stat message, containing the final score of the ended match. Used by the end game screen controller
     * to draw the final score.
     * @return a JSONObject containing all information about the final score of the ended match.
     */
    public JSONObject getGameStatMessage() {
        return gameStatMessage;
    }

    /**
     * Getter for the game board controller.
     * @return a reference to the game board controller.
     */
    public GameBoardController getGameBoard(){
        return gameBoard;
    }

    /**
     * Getter for the username of the currently playing player. Used into the game board controller to write into the message
     * text area who is playing.
     * @return a String containing the username of the currently playing player.
     */
    public String getNowPlaying(){
        return nowPlaying;
    }

    /**
     * Setter for the main player username variable.
     * @param usernameMainPlayer: a String containing main player's username.
     */
    public void setUsernameMainPlayer(String usernameMainPlayer){
        this.usernameMainPlayer = usernameMainPlayer;
    }

    /**
     * Setter for the lobby controller reference.
     * @param lobbyController: a reference to the actual lobby controller.
     */
    public void setLobbyController(LobbyController lobbyController){
        this.lobbyController = lobbyController;
    }

    /**
     * Setter for the window frame choice reference.
     * @param windowFramesChoice: a reference to the actual window frame choice controller.
     */
    public void setWindowFramesChoice(WindowFramesChoice windowFramesChoice){
        this.windowFramesChoice = windowFramesChoice;
    }

    /**
     * Setter for the game board controller reference.
     * @param gameBoard: a reference to the actual game board controller.
     */
    public void setGameBoard(GameBoardController gameBoard){
        this.gameBoard = gameBoard;
    }

    /**
     * Setter for the JSONObject containing the game board message; it will contain all information about the
     * actual game status.
     * @param gameBoardMessage: a JSONObject containing all information about the actual game status.
     */
    public void setGameBoardMessage(JSONObject gameBoardMessage) {
        this.gameBoardMessage = gameBoardMessage;
    }

    /**
     * Setter for the JSONObject containing the game stat message; it will contain the final score of the ended game.
     * @param gameStatMessage: a JSONObject containing the final score of the ended game.
     */
    public void setGameStatMessage(JSONObject gameStatMessage) {
        this.gameStatMessage = gameStatMessage;
    }

    /**
     * Method that set the stages behaviour on close request: it sends the message "exit" to the connection controller,
     * to signal the disconnection, and then it kills the process with a System.exit(0).
     * @param stage: the stage on which the behaviour will be set.
     */
    public final static void setOnCloseRequest(Stage stage){
        stage.setOnCloseRequest(e -> {
            try {
                getInstance().connectionController.send(GUIParameters.EXIT);
            } catch (ConnectException e1) {
                e1.printStackTrace();
            }
            System.exit(0);
        });
    }

    /**
     * Singleton constructor for unique Connection Controller; it has to be the same for all GUI scenes.
     * @param connectionType: type of connection chosen; used to set a new Connection Controller.
     */
    public static void setConnectionType(ConnectionType connectionType){
        myConnectionType = connectionType;
    }
    public static GuiManager getInstance() throws ConnectException{
        if(guiManagerInstance == null){
            guiManagerInstance = new GuiManager(myConnectionType);
        }
        return guiManagerInstance;
    }
    private GuiManager(ConnectionType connectionType) throws ConnectException{
        connectionController = new ConnectionManager(connectionType);
        lobbyController = null;
        gameStatMessage = null;
        gameBoardMessage = null;
    }
}
