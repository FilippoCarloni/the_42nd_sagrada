package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.gameboard.cards.AbstractDeck;

import java.util.List;

public class ToolDeck extends AbstractDeck {

    public ToolDeck() {
        List<ToolCard> tools = ToolCardFactory.getTools();
        for (ToolCard c : tools)
            add(c);
    }
}
