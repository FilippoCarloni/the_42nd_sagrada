package it.polimi.ingsw.connection.constraints;

/**
 * The ServerMessage class contains the constants parts of the messages that the server sends to the clients.
 */
public class ServerMessages {
    private ServerMessages(){
        
    }

    public static final String NOT_LOGGED="You must login first";
    public static final String NOT_VALID_USERNAME="The username is not valid";
    public static final String NOT_MULTY_GAME_WITH_ONE_CLIENT="Multi client is not allowed for single users";
    public static final String ALREADY_EXISTING_USERNAME="Username already used";
    public static final String NOT_EXISTING_SESSION="This sessionID doesn't exists";
    public static final String ENDED_GAME = "The game is ended";
    public static final String ENTERING_MATCH = " is entered in match n ";
    public static final String WAIT_WINDOW = "Wait the timer for the window frame choice";
    public static final String RESTORED_SESSION = "Restored session of ";
    public static final String CONNECTED = " is connected with sessionID: ";
    public static final String PREV_MATCH_ENDED = "Previous match was ended because there are too few players. Starting a new one";
    public static final String NOT_YOUR_TURN = "It's not your turn";
    public static final String NOT_UNDO = "You can't undo";
    public static final String NOT_REDO = "You can't redo";
    public static final String WIN_ONE_PLAYER = "You win because you are the only player left";
    public static final String DISCONNECTED = " is now disconnected";
    public static final String RECONNECTED = " is now reconnected";
    public static final String GAME_VIOLATION = "Hacker !!!, not playing user are trying to enter in a match";
    public static final String GAME_VIOLATION_MESSAGE = "Error, you are not playing in this game";
    public static final String LOBBY_LEAVING = " has leaved the lobby";
    public static final String NOT_PLAYING = "You are not playing";
    public static final String NOT_AVAILABLE_CONNECTION_TYPE = "No available connection type";
    public static final String SERVER_DISCONNECTION_ERROR = "Server disconnection";
    public static final String INVALID_MESSAGE = "Invalid message" ;
    public static final String GOOD_BYE = "Good bye";
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
    public static final String ALREADY_LOGGED = "You are already logged";
    public static final String SOCKET_WRONG_ARGUMENT = "Bad arguments passed";
    public static final String SOCKET_ANOMALY_DISCONNECTION = "Anomaly disconnection";
    public static final String SOCKET_ERROR = "Socket error";
    public static final String COMMAND_NOT_RECOGNIZED = "Command not recognized";
    static final String NOT_FOUND_CONFIG_FILE = "Error found in the configuration file";
    public static final String WAITING_MESSAGE = "Waiting others players...";
}
