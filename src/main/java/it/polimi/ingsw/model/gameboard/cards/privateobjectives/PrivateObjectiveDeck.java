package it.polimi.ingsw.model.gameboard.cards.privateobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractDeck;
import it.polimi.ingsw.model.gameboard.utility.Color;

public class PrivateObjectiveDeck extends AbstractDeck {

    public PrivateObjectiveDeck() {
        for (Color c : Color.values())
            add(new PaperPrivateObjectiveCard(c));
    }
}
