package it.polimi.ingsw.model.turns;

import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.JSONSerializable;

import java.util.List;

/**
 * Represents the class that manages the player order of Sagrada turns.
 * Sagrada is a game based on drafting, and the turn order embraces that policy.
 * Let's make an example:
 *   " Alice, Bob and Carol are playing a Sagrada game.
 *     Let's assume that it's Alice's turn and the players in clockwise order are Alice -> Bob -> Carol.
 *     The turn order of this round is: Alice -> Bob -> Carol -> Carol -> Bob -> Alice.
 *     The next round starts with a Bob turn, as he follows Alice in the clockwise order.
 *     The second turn order is: Bob -> Carol -> Alice -> Alice -> Carol -> Bob.
 *     The next round starts with a Carol turn, as he follows Bob in the clockwise order.
 *     The third turn order is: Carol -> Alice -> Bob -> Bob -> Alice -> Carol.
 *     This turn order structures repeats until the end of the game. "
 */
public interface TurnManager extends JSONSerializable {

    /**
     * Returns the current playing player.
     * @return A Player object that should be playing its game
     */
    Player getCurrentPlayer();

    List<Player> getPlayers();

    /**
     * Advances the turn order.
     * As explained above, the current playing player will change according to the drafting rules.
     */
    void advanceTurn();

    /**
     * Returns true if the current playing player is the first of the round.
     * @return A boolean value true only when the round is just started
     */
    boolean isRoundStarting();

    /**
     * Returns true if the current playing player is the last of the round.
     * @return A boolean value true only when the round is ending as the last player is playing
     */
    boolean isRoundEnding();

    /**
     * Returns true if the current playing player already played a turn during this round.
     * This method is used only by Tool Cards.
     * @return A boolean value true if it's the second turn of the current player this round
     */
    boolean isSecondTurn();

    /**
     * Makes the current playing player able to take another turn after the current one.
     * This method can only be used once per player per round and only during
     * the first turn of the player during the round.
     * This method should only be used by Tool Cards.
     */
    void takeTwoTurns();
}
