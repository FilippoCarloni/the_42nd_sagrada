package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.gameboard.cards.AbstractDeck;

public class ToolDeck extends AbstractDeck {

    public ToolDeck(ConcreteGameStatus status) {
        add(new GrozingPliers(status));
        add(new EglomiseBrush(status));
        add(new GrindingStone(status));
        add(new CopperFoilBurnisher(status));
    }
}
