package it.polimi.ingsw.model.commands;

/**
 * Represents a generic command that can be performed on a game status in order to
 * change its current information, advancing the game.
 */
public interface Command {

    /**
     * Returns true if the command sent by the player matches the regular expression
     * of this Command instance.
     * @return A boolean value
     */
    boolean isValid();

    /**
     * Executes the command on the game status.
     * @throws IllegalCommandException The command is not rule compliant and clashes with the current game status
     */
    void execute() throws IllegalCommandException;
}
