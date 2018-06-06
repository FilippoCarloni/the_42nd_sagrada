package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractDeck;

import java.util.List;

/**
 * Generates a deck of public objective cards.
 * Details of the content:
 *     +----+----------------------+
 *     | ID | Name of the card     |
 *     |----|----------------------|
 *     | 13 | Color Diagonals      |
 *     | 14 | Color Variety        |
 *     | 15 | Column Color Variety |
 *     | 16 | Column Shade Variety |
 *     | 17 | Dark Shades          |
 *     | 18 | Light Shades         |
 *     | 19 | Medium Shades        |
 *     | 20 | Row Color Variety    |
 *     | 21 | Row Shade Variety    |
 *     | 22 | Shade Variety        |
 *     +----+----------------------+
 */
public class PublicObjectiveDeck extends AbstractDeck {

    public PublicObjectiveDeck() {
        List<PublicObjectiveCard> cards = PublicObjectiveFactory.getPublicObjectives();
        for (PublicObjectiveCard card : cards)
            add(card);
    }
}
