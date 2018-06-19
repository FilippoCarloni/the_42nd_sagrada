package it.polimi.ingsw.model.gameboard.cards;

import it.polimi.ingsw.model.utility.JSONSerializable;

/**
 * Represents any Sagrada card.
 * Sagrada card types are:
 * <ul>
 *     <li>public objective</li>
 *     <li>private objective</li>
 *     <li>tool</li>
 * </ul>
 */
public interface Card extends Drawable, JSONSerializable {

    /**
     * Returns the card name.
     * @return A String that represent the card name
     */
    String getName();

    /**
     * Returns card effect.
     * @return A String that describes the card behavior
     */
    String getDescription();

    /**
     * Returns a unique ID that identifies the card.
     * @return A positive integer, unique for each card
     */
    int getID();
}
