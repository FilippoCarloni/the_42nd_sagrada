package it.polimi.ingsw.model.players;

import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.utility.JSONSerializable;

/**
 * Represents the abstract player of the game.
 * It performs indirectly moves that change the game status and has:
 *     - a username which identifies the player uniquely
 *     - a window frame for constructing the stained-glass window
 *     - some favor points that can be used for Tool Card activation
 *     - a private objective that grants additional points at the end of the game
 *
 * Note that the player doesn't start with frame and private objective initialized:
 * this allows the player to choose between multiple frames at the start of the game.
 */
public interface Player extends JSONSerializable {

    /**
     * Returns the player's username.
     * @return A String representing the unique username
     */
    String getUsername();

    /**
     * Returns the number of favor points that the player has.
     * @return An integer between 0 and 6
     */
    int getFavorPoints();

    /**
     * Sets the favor points of a player to a specific value.
     * @param points Integer greater than 0
     */
    void setFavorPoints(int points);

    /**
     * Returns the player's window frame.
     * It can be null if the game is not started.
     * @return A WindowFrame object
     */
    WindowFrame getWindowFrame();

    /**
     * Sets the player's window frame.
     * This method should only be used at the start of the game.
     * @param window A WindowFrame object
     */
    void setWindowFrame(WindowFrame window);

    /**
     * Returns the player's private objective card.
     * It can be null if the game is not started.
     * @return A PrivateObjectiveCard object
     */
    PrivateObjectiveCard getPrivateObjective();

    /**
     * Sets the player's private objective card.
     * This method should only be used at the start of the game.
     * @param po A PrivateObjectiveCard object
     */
    void setPrivateObjective(PrivateObjectiveCard po);
}
