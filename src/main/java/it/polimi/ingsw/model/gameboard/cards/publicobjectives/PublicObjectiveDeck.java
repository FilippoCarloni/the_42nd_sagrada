package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractDeck;

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
