package it.polimi.ingsw.model.gameboard.cards;

import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

/**
 * Represents a private or public objective card.
 */
public interface ObjectiveCard extends Card {

    /**
     * Returns the value associated to the window frame according to the card effect.
     * @param window A WindowFrame objects
     * @return A positive integer
     */
    int getValuePoints(WindowFrame window);
}
