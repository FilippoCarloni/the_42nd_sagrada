package it.polimi.ingsw.connection.server;

/**
 * The ServerMessage class contains the message that the server sends to the clients.
 */
class ServerMessage {
    private ServerMessage(){
        
    }
    static final String NOT_LOGGED="You are not logged ";
    static final String NOT_VALID_USERNAME="The username is not valid!";
    static final String NOT_MULTYGAME_WITH_ONE_CLIENT="NO multi client for a single user!";
    static final String ALREADY_EXISTING_USERNAME="Username already used";
    static final String NOT_EXISTING_SESSION="This sessionID not exists";
    static final String ENDED_GAME = "Game ended";
    static final String ENTERING_MATCH = " is entered in match n ";
    static final String WAIT_WINDOW = "Wait the timer for the map";
    static final String RESTORED_SESSION = "Restored session of ";
    static final String CONNECTED = " is connected with sessionID: ";
    static final String PREV_MATCH_ENDED = "Previous match was ended because there are too few players. Starting a new one";
    static final String NOT_YOUR_TURN = "Is not your turn!";
    static final String NOT_UNDO = "You can not undo";
    static final String NOT_REDO = "You can not redo";
    static final String WIN_ONE_PLAYER = "You win! because you are the only player";
    static final String DISCONNECTED = " is now disconnected";
    static final String RECONNECTED = " is now reconnected";
    static final String GAME_VIOLATION = "Hacker !!!, not playing user are trying to enter in a match";
    static final String GAME_VIOLATION_MESSAGE = "Error, you are not playing in this game";
    static final String LOBBY_LEAVING = " has leaved the match lobby!";

}
