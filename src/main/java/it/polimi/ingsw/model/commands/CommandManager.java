package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;

/**
 * Represents the manager of game commands. Through this object actions can be performed
 * on the game data.
 */
public interface CommandManager {

    /**
     * Returns the current status of the game.
     * @return A GameData instance
     */
    GameData getCurrentData();

    /**
     * Launches the execution of the command string by a certain player.
     * @param player The player that launches the command
     * @param cmd The command string provided by the player
     * @throws IllegalCommandException The command is not rule-compliant and clashes with the current game status
     */
    void executeCommand(Player player, String cmd) throws IllegalCommandException;

    /**
     * Reverts the game status to the previous one.
     * If there are no previous states, this method just does nothing.
     * <br>
     * NOTE: this method ALWAYS executes, even if a player is not allowed to undo his/her action.
     * This feature allows deeper testing and gives complete control of the game status.
     */
    void undoCommand();

    /**
     * Returns true if the player is allowed to revert his/her last action.
     * @return A boolean value
     */
    boolean isUndoAvailable();

    /**
     * Forwards the game status to the next one.
     * If there are no next states, this method just does nothing.
     * <br>
     * NOTE: this method ALWAYS executes, giving complete control of the game status.
     */
    void redoCommand();

    /**
     * Returns true if the player is allowed to forward his/her previously undone action.
     * @return A boolean value
     */
    boolean isRedoAvailable();
}
