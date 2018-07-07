package it.polimi.ingsw.connection.constraints;

/**
 * The Commands class contains the game commands that perform a particular action.
 */
public class SpecialCommands {
    private SpecialCommands(){}

    /**
     * Permits to pass turn, if it is possible.
     */
    public static final String PASS = "pass";
    /**
     * Permits to undo the last action, if it is possible.
     */
    public static final String UNDO = "undo";
    /**
     * Permits to redo the last action, if it is possible.
     */
    public static final String REDO = "redo";
}
