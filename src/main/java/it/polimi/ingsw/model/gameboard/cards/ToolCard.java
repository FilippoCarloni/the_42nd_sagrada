package it.polimi.ingsw.model.gameboard.cards;

import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.commands.Executable;

import java.util.List;

public interface ToolCard extends Card, Executable {
    int getID();
    int getFavorPoints();
    void addFavorPoints();
    List<Command> getCommands(String cmd);
}
