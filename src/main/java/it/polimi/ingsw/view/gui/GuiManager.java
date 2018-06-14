package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connection.client.ConnectionController;
import it.polimi.ingsw.connection.client.ConnectionType;
import it.polimi.ingsw.connection.server.messageencoder.MessageType;
import it.polimi.ingsw.view.gui.preliminarystages.lobby.LobbyController;
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

//TODO: put attention on multi-thread for updates
//TODO: use update to receive screen resolution, to make GUI more responsive

public class GuiManager {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final int REFRESH_RATE = 100; // milliseconds
    private ConnectionController connectionController;
    private LobbyController lobbyController;
    private JSONObject preGameMessage;
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
            if(message.length() > 0) {
                switch (MessageType.decodeMessageType(message)) {
                    case GENERIC_MESSAGE:
                        lobbyController.printConnectionOrDisconnection(MessageType.decodeMessageContent(message));
                        break;
                    case GAME_BOARD:
                        //TODO: add method that will launch Game Board Drawing. Remember that I can enter and receive a Game Board, if I've restored a session
                        break;
                    case ERROR_MESSAGE:
                        print(MessageType.decodeMessageContent(message));
                        break;
                    case PRE_GAME_CHOICE:
                        lobbyController.getPlayButton().setDisable(false);
                        preGameMessage = (JSONObject) new JSONParser().parse(MessageType.decodeMessageContent(message));
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

    //Connection Controller getter
    public ConnectionController getConnectionController(){
        return connectionController;
    }

    //Setters of references to controllers
    public void setLobbyController(LobbyController lobbyController){
        this.lobbyController = lobbyController;
    }
    public JSONObject getPreGameMessage(){
        return preGameMessage;
    }

    //Singleton constructor for unique Connection Controller
    public static void setConnectionType(ConnectionType connectionType){
        myConnectionType = connectionType;
    }
    public static GuiManager getInstance() {
        if(guiManagerInstance == null){
            guiManagerInstance = new GuiManager(myConnectionType);
        }
        return guiManagerInstance;
    }
    private GuiManager(ConnectionType connectionType) {
        try {
            connectionController = new ConnectionController(connectionType);
        } catch (ConnectException | RemoteException e) {
            print(e.getMessage());
        }
    }
}
