package it.polimi.ingsw.model.gameboard.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Represents a basic deck with generic Drawable objects as its card content.
 * Holds the implementation of the draw method that must be equal for every deck.
 * A concrete deck should be an AbstractDeck child and should fill itself with Drawable objects.
 */
public abstract class AbstractDeck implements Deck {

    private ArrayList<Drawable> cards;

    protected void add(Drawable card) {
        if (card == null) throw new NullPointerException("Cannot add a null card to the deck.");
        if (cards == null) cards = new ArrayList<>();
        cards.add(card);
    }

    @Override
    public Drawable draw() {
        if (cards.isEmpty()) throw new NoSuchElementException("The deck is empty.");
        return cards.remove(new Random().nextInt(cards.size()));
    }

    @Override
    public List<Drawable> draw(int numOfDraws) {
        if (numOfDraws <= 0) throw new IllegalArgumentException("You must draw at least one card.");
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
