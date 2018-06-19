package it.polimi.ingsw.model.gameboard.cards;

import java.util.List;

/**
 * Represents a Sagrada deck that holds Drawable objects.
 */
public interface Deck {

    /**
     * Draws an object from the deck.
     * The returned object should be casted to the desired type, for example:
     * <br>
     * PublicObjectiveCard pc = (PublicObjectiveCard) new PublicObjectiveDeck().draw();
     * @return A Drawable object
     */
    Drawable draw();

    /**
     * Draws an arbitrary number of objects from the deck.
     * @param numOfDraws An integer between 1 and the deck size
     * @return A Drawable object
     */
    List<Drawable> draw(int numOfDraws);

    /**
     * Returns the number of objects contained in the deck.
     * @return A positive integer
     */
    int size();
}
