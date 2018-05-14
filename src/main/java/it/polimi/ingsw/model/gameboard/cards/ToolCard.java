package it.polimi.ingsw.model.gameboard.cards;

import it.polimi.ingsw.model.commands.Executable;

public interface ToolCard extends Card, Executable {
    int getID();
    int getFavorPoints();
    void addFavorPoints();
}
