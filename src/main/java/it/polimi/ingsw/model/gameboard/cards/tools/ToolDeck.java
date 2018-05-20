package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.gameboard.cards.AbstractDeck;

public class ToolDeck extends AbstractDeck {

    public ToolDeck(ConcreteGameStatus status) {
        add(new GrozingPliers(status));
        add(new EglomiseBrush(status));
        add(new CopperFoilBurnisher(status));
        add(new Lathekin(status));
        add(new LensCutter(status));
        add(new FluxBrush(status));
        add(new GlazingHammer(status));
        add(new RunningPliers(status));
        add(new CorkBackedStraightedge(status));
        add(new GrindingStone(status));
        // tool card #11
        // tool card #12
    }
}
