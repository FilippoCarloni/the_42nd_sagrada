package it.polimi.ingsw.model.gameboard.cards.privateobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractDeck;

import java.util.List;

/**
 * Generates a deck of private objective cards.
 * Details of the content:
 * <ul>
 *     <li>23: Shades of red</li>
 *     <li>24: Shades of green</li>
 *     <li>25: Shades of yellow</li>
 *     <li>26: Shades of blue</li>
 *     <li>27: Shades of purple</li>
 * </ul>
 */
public class PrivateObjectiveDeck extends AbstractDeck {

    /**
     * Generates a new private objective deck instance.
     */
    public PrivateObjectiveDeck() {
        List<PrivateObjectiveCard> cards = PrivateObjectiveFactory.getPrivateObjectives();
        for (PrivateObjectiveCard c : cards)
            add(c);
    }
}
