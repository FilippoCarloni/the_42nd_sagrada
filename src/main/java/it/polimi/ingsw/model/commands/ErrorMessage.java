package it.polimi.ingsw.model.commands;

/**
 * Holds all the possible messages returned by an IllegalCommandException
 * @see IllegalCommandException
 */
public final class ErrorMessage {

    private ErrorMessage() {}

    static final String ERR_GENERIC_MESSAGE = "[!] Command not executed, here are the possible causes: ";
    static final String ERR_COMMAND_NOT_EXISTS = "This command does not exist in the context of this turn. Try checking the syntax or activate the correct tool card.";
    static final String ERR_NOT_YOUR_TURN = "It'n not your turn.";

    public static final String ERR_DIE_NOT_PICKED = "You should pick a die.";
    public static final String ERR_DIE_PICKED = "You picked a die this turn.";
    public static final String ERR_INDEX_TOO_BIG = "Index too big.";
    public static final String ERR_INDEX_TOO_SMALL = "Index too small.";
    public static final String ERR_DIFFERENT_INDEX = "The expected index is different from the inserted one.";
    public static final String ERR_TOOL_ACTIVE = "You should terminate the tool card execution.";
    public static final String ERR_DIE_PLACED = "You already placed a die this turn.";
    public static final String ERR_DIE_NOT_PLACED = "You haven't already placed a die this turn.";
    public static final String ERR_INVALID_COORDINATES = "Passed coordinates exceed window frame bounds.";
    public static final String ERR_SLOT_ALREADY_OCCUPIED = "Chosen slot is already occupied by another die.";
    public static final String ERR_SLOT_UNOCCUPIED = "Chosen slot is empty.";
    public static final String ERR_RULE_ERROR = "Picked die is not allowed to be placed in that position.";
    public static final String ERR_PLACING_ERROR = "Given position for the chosen die violates the placing rule.";
    public static final String ERR_COLOR_ERROR = "Given position for the chosen de violates the color placing rule.";
    public static final String ERR_SHADE_ERROR = "Given position for the chosen de violates the shade placing rule.";
    public static final String ERR_FAVOR_POINTS = "You don't have enough favor points.";
    public static final String ERR_TOOL_ACTIVATED = "You are not allowed to use another tool card this turn.";
    public static final String ERR_CANNOT_INCREASE = "You can't increase the value of the picked die.";
    public static final String ERR_CANNOT_DECREASE = "You can't decrease the value of the picked die.";
    public static final String ERR_FIRST_TURN_OF_ROUND = "This is your first turn of the round.";
    public static final String ERR_SECOND_TURN_OF_ROUND = "This is your second turn of the round.";
    public static final String ERR_DIE_ALREADY_MOVED = "You already moved this die.";
    public static final String ERR_NO_SHARING_DIE_1 = "There is no ";
    public static final String ERR_NO_SHARING_DIE_2 = "-sharing die in the ";

    // auxiliary defines
    public static final String COLOR = "color";
    public static final String SHADE = "shade";
    public static final String ROUND_TRACK = "round track";
    public static final String DICE_POOL = "dice pool";
    public static final String DICE_MOVED_POOL = "dice-moved pool";
}
