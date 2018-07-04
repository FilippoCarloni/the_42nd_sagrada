package it.polimi.ingsw.connection.costraints;

/**
 * The ConnectionCommands contains all the commands send by the client to the server.
 */
public class ConnectionCommands {

    private ConnectionCommands(){}

    public static final String ACTION_COMMAND = "action";
    public static final String PLAY_COMMAND = "play";
    public static final String COMMAND_SEPARATOR = " ";
    public static final String WINDOW_COMMAND = "window";
    public static final String VIEW_COMMAND = "view";
    public static final String LOGIN_COMMAND = "login";
    public static final String QUIT_COMMAND = "quit";
    public static final String RESTORE_COMMAND = "restore";
}
