package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connection.client.ConnectionController;
import it.polimi.ingsw.connection.client.ConnectionType;
import it.polimi.ingsw.connection.server.messageencoder.MessageType;
import it.polimi.ingsw.view.gui.preliminarystages.lobby.LobbyController;
import it.polimi.ingsw.view.gui.preliminarystages.windowframeschoice.WindowFramesChoice;
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
 * This class helps managing the Connection Controller and the updating of informations.
 * It has to be unique for all the fxml scenes
 */

public class Helper {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final int REFRESH_RATE = 100; // milliseconds
    private ConnectionController connectionController;
    private LobbyController lobbyController;
    private WindowFramesChoice windowFramesChoice;

    //Scheduler
    public void startRefresh() {
        scheduler.scheduleAtFixedRate(this::update, 1, REFRESH_RATE, MILLISECONDS);
    }
    //Updater, managed by the scheduler
    private void update(){
        try {
            String message = GUIParameters.globalHelper.connectionController.readMessage();
            if(message.length() > 0) {
                switch (MessageType.decodeMessageType(message)) {
                    case GENERIC_MESSAGE:
                        if (GUIParameters.alreadyInLobby) {
                            //TODO: screen will display the message (we are playing)
                        } else {
                            lobbyController.printConnectionOrDisconnection(MessageType.decodeMessageContent(message));
                        }
                        break;
                    case GAME_BOARD:
                        //TODO: add method that will launch Game Board Drawing
                        break;
                    case ERROR_MESSAGE:
                        print(MessageType.decodeMessageContent(message));
                        break;
                    case PRE_GAME_CHOICE:
                        lobbyController.lobbyToPreGame();
                        GUIParameters.setAlreadyInLobby();
                        windowFramesChoice.drawPreGame((JSONObject) new JSONParser().parse(MessageType.decodeMessageContent(message)));
                        break;
                    default:
                        print("Message not supported!");
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
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
    public void setWindowFramesChoice(WindowFramesChoice windowFramesChoice){
        this.windowFramesChoice = windowFramesChoice;
    }

    //Singleton constructor
    public static Helper getInstance(ConnectionType connectionType) {
        return new Helper(connectionType);
    }
    private Helper(ConnectionType connectionType) {
        try {
            connectionController = new ConnectionController(connectionType);
        } catch (ConnectException | RemoteException e) {
            print(e.getMessage());
        }
    }
}
