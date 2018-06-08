package it.polimi.ingsw.model.gamedata;

import it.polimi.ingsw.model.commands.IllegalCommandException;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.Parameters;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * Returns a list of private objectives that should be shared between the players
     * in order to start a new match (1x player).
     *
     * NOTE: for a game to start, is required a list of players. All of the players
     * in the list must have a not-null private objective and a not-null window frame.
     * This method allows private objective picking outside of the "concrete" game context.
     * @param numOfPlayers An integer between 2 and 4
     * @return A list of PrivateObjectiveCard instances (size between 2 and 4)
     */
    static List<PrivateObjectiveCard> getPrivateObjectives(int numOfPlayers) {
        if (numOfPlayers < 2 || numOfPlayers > Parameters.MAX_PLAYERS)
            throw new IllegalArgumentException("You entered an invalid player number.");
        return new PrivateObjectiveDeck().draw(numOfPlayers)
                .stream().map(po -> (PrivateObjectiveCard) po).collect(Collectors.toList());
    }

    /**
     * Returns a list of window frames that should be shared between the players
     * in order to start a new match (4x player).
     *
     * NOTE: for a game to start, is required a list of players. All of the players
     * in the list must have a not-null private objective and a not-null window frame.
     * This method allows window frame picking outside of the "concrete" game context.
     * @param numOfPlayers An integer between 2 and 4
     * @return A list of WindowFrame instances (size between 8 and 16)
     */
    static List<WindowFrame> getWindowFrames(int numOfPlayers) {
        if (numOfPlayers < 2 || numOfPlayers > Parameters.MAX_PLAYERS)
            throw new IllegalArgumentException("You entered an invalid player number.");
        return new WindowFrameDeck().draw(numOfPlayers * Parameters.NUM_OF_WINDOWS_PER_PLAYER_BEFORE_CHOICE)
                .stream().map(po -> (WindowFrame) po).collect(Collectors.toList());
    }

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
     * Exposes a copy of the entire game data. This method is useful for testing purposes.
     * @return A GameData object containing the current game status
     */
    GameData getData();

    /**
     * Exposes a copy of the game data. Hides information that the asking player
     * should not see. This method is meant to be used for internet communication.
     * @param player The player that asked its game data
     * @return A JSON object containing the current game status from the point of view of the player
     */
    JSONObject getData(Player player);
}
