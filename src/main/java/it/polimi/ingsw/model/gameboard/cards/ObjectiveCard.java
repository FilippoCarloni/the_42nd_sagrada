package it.polimi.ingsw.model.gameboard.cards;

import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

/**
 * Represents a private or public objective card.
 * Every objective card is able to calculate favor points based on the positioning
 * of dice on the player frame. This points are used to calculate the final score of the
 * player at the end of the game.
 */
public interface ObjectiveCard extends Card {

    /**
     * Returns the value associated to the window frame according to the card effect.
     * @param window A WindowFrame objects
     * @return A positive integer
     */
    int getValuePoints(WindowFrame window);
}
