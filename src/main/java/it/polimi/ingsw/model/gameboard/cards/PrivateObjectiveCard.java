package it.polimi.ingsw.model.gameboard.cards;

import it.polimi.ingsw.model.utility.Color;

/**
 * Represents a Sagrada private objective card.
 * Every player has only one private objective card during a game.
 */
public interface PrivateObjectiveCard extends ObjectiveCard {

    /**
     * Returns the color of the card.
     * @return A Color enum value
     */
    Color getColor();
}
