package it.polimi.ingsw.connection.costraints;

/**
 * The ServerMessage class contains the message that the server sends to the clients.
 */
public class ServerMessage {
    private ServerMessage(){
        
    }

    public static final String NOT_LOGGED="You are not logged ";
    public static final String NOT_VALID_USERNAME="The username is not valid!";
    public static final String NOT_MULTYGAME_WITH_ONE_CLIENT="NO multi client for a single user!";
    public static final String ALREADY_EXISTING_USERNAME="Username already used";
    public static final String NOT_EXISTING_SESSION="This sessionID not exists";
    public static final String ENDED_GAME = "Game ended";
    public static final String ENTERING_MATCH = " is entered in match n ";
    public static final String WAIT_WINDOW = "Wait the timer for the map";
    public static final String RESTORED_SESSION = "Restored session of ";
    public static final String CONNECTED = " is connected with sessionID: ";
    public static final String PREV_MATCH_ENDED = "Previous match was ended because there are too few players. Starting a new one";
    public static final String NOT_YOUR_TURN = "Is not your turn!";
    public static final String NOT_UNDO = "You can not undo";
    public static final String NOT_REDO = "You can not redo";
    public static final String WIN_ONE_PLAYER = "You win! because you are the only player";
    public static final String DISCONNECTED = " is now disconnected";
    public static final String RECONNECTED = " is now reconnected";
    public static final String GAME_VIOLATION = "Hacker !!!, not playing user are trying to enter in a match";
    public static final String GAME_VIOLATION_MESSAGE = "Error, you are not playing in this game";
    public static final String LOBBY_LEAVING = " has leaved the match lobby!";
    public static final String NOT_PLAYING = "You are not playing";
    public static final String NOT_AVAILABLE_CONNECTION_TYPE = "No available connection type";
    public static final String SERVER_DISCONNECTION_ERROR = "Server disconnection";
    public static final String INVALID_MESSAGE = "Invalid message" ;
    public static final String GOOD_BYE = "Good Bye";
    public static final String LOGIN_REQUEST = "Login requested from: ";
    public static final String GAME_REQUEST = "Restore request from: ";
    public static final String RESTORE_REQUEST = "Restore request from: ";
    public static final String STATUS_REQUEST = "Status request from: ";
    public static final String TURN_REQUEST = "Turn request from: ";
    public static final String WINDOW_REQUEST = "Set window request from: ";
    public static final String COMMAND_REQUEST_1 = "Command <";
    public static final String COMMAND_REQUEST_2 = "> send from: ";
    public static final String SOCKET_SERVER_MESSAGE =  "Server thread started, the server is reachable through socket connection at the port: ";
    public static final String NEW_SOCKET_CLIENT_THREAD = "A new client thread is started";
    public static final String SOCKET_SERVER_ERROR = "Connection of new client over socket error";
    public static final String NOT_VALID_NUMBER = "Connection of new client over socket error";
}
