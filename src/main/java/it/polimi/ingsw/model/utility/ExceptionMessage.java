package it.polimi.ingsw.model.utility;

/**
 * Holds all the exception messages thrown by model classes.
 */
public class ExceptionMessage {

    private ExceptionMessage() {}

    // generic exceptions
    public static final String NOT_MATCHING_PARAMETER = "The passed parameter does not match any of the expected values.";
    public static final String OBJECT_NOT_EXISTS = "The object that you are searching for does not exist.";
    public static final String NEGATIVE_INTEGER = "The integer can't be negative.";
    public static final String INDEX_OUT_OF_BOUND = "The index exceeds the defined bound.";
    public static final String NULL_PARAMETER = "The passed parameter should not be null.";
    static final String BAD_JSON = "Bad JSON format.";
    public static final String NULL_REFERENCE = "There is an illegal null reference to this object.";

    // game-specific exceptions
    public static final String PLAYER_ALREADY_PLAYED_TWO_TURNS = "The player already played two turns.";
    public static final String PLAYER_SAME_NAME = "Players can't have the same name.";
}
