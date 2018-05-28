package it.polimi.ingsw.model.commands.basiccommands;

import it.polimi.ingsw.model.commands.AbstractCommand;
import it.polimi.ingsw.model.commands.conditions.DieNotPicked;
import it.polimi.ingsw.model.commands.conditions.ToolNotActive;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;

public class Pass extends AbstractCommand {

    public Pass(Player player, GameData gameData, String cmd) {
        super(player, gameData, cmd);
        addCondition(new DieNotPicked(gameData));
        addCondition(new ToolNotActive(gameData));
    }

    @Override
    public String getRegExp() {
        return "pass";
    }

    @Override
    public void executionWhenLegal() {
        getGameData().advance();
    }

    @Override
    public boolean undoable() {
        return false;
    }
}
