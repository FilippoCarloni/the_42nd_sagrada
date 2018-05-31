package it.polimi.ingsw.model.gameboard.cards;

import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;

import java.util.List;

public interface ToolCard extends Card {

    int getFavorPoints();
    void addFavorPoints();
    boolean isEffectActive();
    Command getActivator(Player player, GameData gameData, String cmd);
    List<Command> getCommands(Player player, GameData gameData, String cmd);
}
