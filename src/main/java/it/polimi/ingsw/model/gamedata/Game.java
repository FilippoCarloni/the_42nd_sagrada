package it.polimi.ingsw.model.gamedata;

import it.polimi.ingsw.model.commands.IllegalCommandException;
import it.polimi.ingsw.model.players.Player;

import java.util.Map;

/**
 * Represents a generic Sagrada game.
 * It holds all the game data that can be extracted as a GameData object, serializable as JSON
 * and holds internally all Sagrada rules.
 * When a command is not legal according to Sagrada rules, Game throws an IllegalCommandException
 * with the possible cause of the error.
 * Every command can be undone, but be careful: as undoCommand executes always,
 * you should always check if it's the correct player's turn and if it is available according to the rules.
 */
public interface Game {

    /**
     * Executes a string command as a particular player.
     * If the command is not rule compliant, it is not executed.
     * @param player A playing player object (not necessarily the current one)
     * @param command A String containing the command instructions
     * @throws IllegalCommandException The passed command is not rule compliant
     */
    void executeCommand(Player player, String command) throws IllegalCommandException;

    /**
     * Returns the player that is playing during the current turn.
     * @return A playing Player object
     */
    Player getCurrentPlayer();

    /**
     * Reverts a command.
     * This method ALWAYS executes, so it can revert even "pass" commands.
     *
     * NOTE: if used not for testing or debugging, but for usual play,
     * you should externally check if it's the current player's turn
     * and if the "undo" action is available according to Sagrada rules.
     */
    void undoCommand();

    /**
     * True if the "undo" action id available according to Sagrada rules and
     * if the "undo" stack is not empty.
     * @return A boolean value
     */
    boolean isUndoAvailable();

    /**
     * This method ALWAYS executes, redoing a taken action during the game.
     *
     * NOTE: if used not for testing or debugging, but for usual play,
     * you should externally check if it's the current player's turn
     * and if the "redo" action is available.
     */
    void redoCommand();

    /**
     * True if the "redo" stack is not empty.
     * @return A boolean value
     */
    boolean isRedoAvailable();

    /**
     * True if the players had taken ten rounds of play.
     * @return A boolean value
     */
    boolean isGameEnded();

    /**
     * Maps the players to their respective scores, according to the CURRENT game status.
     * @return A map that maps players to a positive integer value
     */
    Map<Player, Integer> getScore();

    /**
     * Exposes a copy of the game data.
     *
     * NOTE: the game data can be serialized in JSON syntax with the encode method.
     * This is a handy shortcut for communication over internet.
     * @return A GameData object containing the current game status
     */
    GameData getData();
}
