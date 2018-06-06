package it.polimi.ingsw.model.gameboard.cards.privateobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractDeck;

import java.util.List;

/**
 * Generates a deck of private objective cards.
 * Details of the content:
 *     +----+----------------------+
 *     | ID | Name of the card     |
 *     |----|----------------------|
 *     | 23 | Shades of Red        |
 *     | 24 | Shades of Green      |
 *     | 25 | Shades of Yellow     |
 *     | 26 | Shades of Blue       |
 *     | 27 | Shades of Purple     |
 *     +----+----------------------+
 */
public class PrivateObjectiveDeck extends AbstractDeck {

    public PrivateObjectiveDeck() {
        List<PrivateObjectiveCard> cards = PrivateObjectiveFactory.getPrivateObjectives();
        for (PrivateObjectiveCard c : cards)
            add(c);
    }
}
