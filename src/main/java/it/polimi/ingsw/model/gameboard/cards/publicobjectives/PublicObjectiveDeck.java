package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractDeck;

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
        add(new ColorDiagonals(13));
        add(new ColorVariety(14));
        add(new ColumnColorVariety(15));
        add(new ColumnShadeVariety(16));
        add(new DarkShades(17));
        add(new LightShades(18));
        add(new MediumShades(19));
        add(new RowColorVariety(20));
        add(new RowShadeVariety(21));
        add(new ShadeVariety(22));
    }
}
