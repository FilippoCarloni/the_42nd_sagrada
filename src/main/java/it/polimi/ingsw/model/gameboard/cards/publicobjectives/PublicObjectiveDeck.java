package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractDeck;

public class PublicObjectiveDeck extends AbstractDeck {

    public PublicObjectiveDeck() {
        add(new ColorDiagonals());
        add(new ColorVariety());
        add(new ColumnColorVariety());
        add(new ColumnShadeVariety());
        add(new DarkShades());
        add(new LightShades());
        add(new MediumShades());
        add(new RowColorVariety());
        add(new RowShadeVariety());
        add(new ShadeVariety());
    }
}
