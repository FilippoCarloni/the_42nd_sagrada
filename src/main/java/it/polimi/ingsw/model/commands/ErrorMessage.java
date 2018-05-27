package it.polimi.ingsw.model.commands;

public final class ErrorMessage {

    private ErrorMessage() {}

    static final String ERR_GENERIC_MESSAGE = "[!] Command not executed, here are the possible causes: ";
    static final String ERR_COMMAND_NOT_EXISTS = "This command does not exist in the context of this game";
    static final String ERR_NOT_YOUR_TURN = "It'n not your turn";

    public static final String ERR_DIE_NOT_PICKED = "You should pick a die";
    public static final String ERR_DIE_PICKED = "You picked a die this turn";
    public static final String ERR_INDEX_TOO_BIG = "Index too big";
    public static final String ERR_INDEX_TOO_SMALL = "Index too small";
    public static final String ERR_TOOL_ACTIVE = "You should terminate the tool card execution";
    public static final String ERR_DIE_PLACED = "You already placed a die this turn";
    public static final String ERR_INVALID_COORDINATES = "Passed coordinates exceed window frame bounds";
    public static final String ERR_SLOT_ALREADY_OCCUPIED = "Chosen slot is already occupied by another die";
    public static final String ERR_RULE_ERROR = "Picked die is not allowed to be placed in that position";
}
