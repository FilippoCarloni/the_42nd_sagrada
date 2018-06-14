package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.gameboard.cards.Card;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;

import java.util.List;

/**
 * Represents a generic Sagrada Tool Card.
 * A tool card can be active or passive:
 *   - active  : the player must actively end its effect by taking precise actions
 *   - passive : the player is not forced to end the effect; the effect is automatically
 *               shut down when the player takes any generic action
 * A tool card holds a number of favor points. Its activation cost is 1 if there are
 * no favor points on it, otherwise it is 2.
 * A tool card is represented in the game model as both a Command (activator) and a Command generator:
 * When activated a tool card executes immediately a Command.
 * When a tool card is active it adds to the commands pool its custom commands.
 * @see Command
 */
public interface ToolCard extends Card {

    /**
     * Returns the number of favor points currently placed on the tool card.
     * @return A positive integer
     */
    int getFavorPoints();

    /**
     * Adds favor points to the tool card: 1 if the current number of favor points is 0
     * and 2 otherwise
     */
    void addFavorPoints();

    /**
     * Returns true if the tool card is of ACTIVE type and
     * false if the tool card is of PASSIVE type
     * @return A boolean value
     */
    boolean isEffectActive();

    /**
     * Returns the command that is automatically executed when the tool card is activated.
     * @param player The player that casts the command
     * @param gameData The current game data
     * @param cmd The command string sent by the player
     * @return A custom Command instance
     */
    Command getActivator(Player player, GameData gameData, String cmd);

    /**
     * Returns the custom commands that the activated tool card gives access to.
     * @param player The player that casts the command
     * @param gameData The current game data
     * @param cmd The command string sent by the player
     * @return A List of custom Command instances
     */
    List<Command> getCommands(Player player, GameData gameData, String cmd);
}
