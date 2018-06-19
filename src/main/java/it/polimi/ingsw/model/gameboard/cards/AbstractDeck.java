package it.polimi.ingsw.model.gameboard.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static it.polimi.ingsw.model.utility.ExceptionMessage.EMPTY_COLLECTION;
import static it.polimi.ingsw.model.utility.ExceptionMessage.NEGATIVE_INTEGER;
import static it.polimi.ingsw.model.utility.ExceptionMessage.NULL_PARAMETER;

/**
 * Represents a basic deck with generic Drawable objects as its card content.
 * Holds the implementation of the draw method that must be equal for every deck.
 * A concrete deck should be an AbstractDeck child and should fill itself with Drawable objects.
 */
public abstract class AbstractDeck implements Deck {

    private ArrayList<Drawable> cards;

    /**
     * Adds a card to the drawable pool.
     * @param card A Drawable instance
     */
    protected void add(Drawable card) {
        if (card == null) throw new NullPointerException(NULL_PARAMETER);
        if (cards == null) cards = new ArrayList<>();
        cards.add(card);
    }

    @Override
    public Drawable draw() {
        if (cards.isEmpty()) throw new NoSuchElementException(EMPTY_COLLECTION);
        return cards.remove(new Random().nextInt(cards.size()));
    }

    @Override
    public List<Drawable> draw(int numOfDraws) {
        if (numOfDraws <= 0) throw new IllegalArgumentException(NEGATIVE_INTEGER);
        ArrayList<Drawable> drawnCards = new ArrayList<>();
        for (int i = 0; i < numOfDraws; i++)
            drawnCards.add(draw());
        return drawnCards;
    }

    @Override
    public int size() {
        return cards.size();
    }
}
