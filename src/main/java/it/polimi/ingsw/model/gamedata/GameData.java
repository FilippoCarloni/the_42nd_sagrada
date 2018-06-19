package it.polimi.ingsw.model.gamedata;

import it.polimi.ingsw.model.gameboard.cards.publicobjectives.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolCard;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.roundtrack.RoundTrack;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.turns.TurnManager;
import it.polimi.ingsw.model.utility.JSONSerializable;

import java.util.List;
import java.util.Map;

/**
 * Represents the data holder of a particular instant of a Sagrada game.
 * In particular, the GameData stores information about:
 * <ul>
 *     <li>round track</li>
 *     <li>dice bag</li>
 *     <li>dice pool</li>
 *     <li>tool cards</li>
 *     <li>public objectives</li>
 *     <li>players</li>
 *     <li>turn state</li>
 *     <li>particular turn information regarding current player actions</li>
 * </ul>
 * In addition, it can generate a score map calculated on the current status
 * and can be serialized in a JSON object.
 */
public interface GameData extends JSONSerializable {

    /**
     * Advances the status of the game (passes the turn and resets the turn variables.)
     */
    void advance();

    /**
     * Generates a map containing the player score based on the current status of the game.
     * @return A Map that connects players to a positive score integer
     */
    Map<Player, Integer> getCurrentScore();

    /**
     * Returns the game round track.
     * @return A RoundTrack object
     */
    RoundTrack getRoundTrack();

    /**
     * Returns the game dice bag.
     * @return A DiceBag object
     */
    DiceBag getDiceBag();

    /**
     * Returns the game dice pool.
     * @return A List of dice
     */
    List<Die> getDicePool();

    /**
     * Returns the game public objectives.
     * @return A List of public objectives
     */
    List<PublicObjectiveCard> getPublicObjectives();

    /**
     * Returns the game tool cards.
     * @return A List of tool cards
     */
    List<ToolCard> getTools();

    /**
     * Returns the players of the current game.
     * @return A List of player instances
     */
    List<Player> getPlayers();

    /**
     * Returns the game turn manager.
     * @return A TurnManager instance
     */
    TurnManager getTurnManager();

    /**
     * Returns the die currently picked by the player.
     * @return A Die instance; null if during the turn wasn't picked any die
     */
    Die getPickedDie();

    /**
     * Sets the current picked die.
     * @param die A not-null Die instance
     */
    void setPickedDie(Die die);

    /**
     * Returns true if a die was placed in a window frame during the current turn.
     * @return A boolean value
     */
    boolean isDiePlaced();

    /**
     * Sets the boolean value that specifies if any die was placed this turn.
     * @param diePlaced A boolean value
     */
    void setDiePlaced(boolean diePlaced);

    /**
     * Returns the ID of the currently activated tool card of active type.
     * <br>
     * NOTE: if there are no active tool cards this method return 0;
     * tool cards can be active (their effect must be ended by the player) or passive
     * (their effect is automatically terminated). This method fetches only for
     * tool cards of the ACTIVE type
     * @return A positive integer that identifies a tool card or 0 if there's no active tool card
     */
    int getActiveToolID();

    /**
     * Sets the current active tool card ID of active type.
     * @param id A positive integer that identifies a tool card
     */
    void setActiveToolID(int id);

    /**
     * Returns the ID of the currently activated tool card of passive type.
     * <br>
     * NOTE: if there are no active tool cards this method return 0;
     * tool cards can be active (their effect must be ended by the player) or passive
     * (their effect is automatically terminated). This method fetches only for
     * tool cards of the PASSIVE type
     * @return A positive integer that identifies a tool card or 0 if there's no active tool card
     */
    int getPassiveToolID();

    /**
     * Sets the current active tool card ID of passive type.
     * @param id A positive integer that identifies a tool card
     */
    void setPassiveToolID(int id);

    /**
     * Returns true if a tool cards has already been activated during this turn.
     * @return A boolean value
     */
    boolean isToolActivated();

    /**
     * Sets the boolean value that specifies if a tool card has already been activates during this turn.
     * @param toolActivated A boolean value
     */
    void setToolActivated(boolean toolActivated);

    /**
     * Returns the list of all the dice moved in a window frame this turn.
     * The list will be empty if no die was moved.
     * @return A List of dice
     */
    List<Die> getDiceMoved();

    /**
     * Returns true if is legal for a player to undo his/her action.
     * Normal actions like picking a die from the dice pool or placing a die in the window frame
     * can be undone, but there's a small set of actions that should not be undone: there
     * actions involve the use of tool cards that roll the previously rolled dice.
     * A player that was not satisfied from the result of a random roll should not be allowed
     * to revert the action.
     * Pass action clearly can't be undone by a player.
     * @return A boolean value
     */
    boolean isUndoAvailable();

    /**
     * Sets the boolean value that specifies if the undo action is available for the player
     * at the current state of the game.
     * @param available A boolean value
     */
    void setUndoAvailable(boolean available);
}
