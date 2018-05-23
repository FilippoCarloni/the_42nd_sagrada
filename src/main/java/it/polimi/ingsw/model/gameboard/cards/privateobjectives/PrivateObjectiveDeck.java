package it.polimi.ingsw.model.gameboard.cards.privateobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractDeck;
import it.polimi.ingsw.model.utility.Color;

public class PrivateObjectiveDeck extends AbstractDeck {

    public PrivateObjectiveDeck() {
        int id = 23;
        for (Color c : Color.values())
            add(new PaperPrivateObjectiveCard(c, id++));
    }
}
