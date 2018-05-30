package it.polimi.ingsw.model.commands.basiccommands;

import it.polimi.ingsw.model.commands.AbstractCommand;
import it.polimi.ingsw.model.commands.CommandRegExp;
import it.polimi.ingsw.model.commands.conditions.ConditionFactory;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;

public class Pass extends AbstractCommand {

    public Pass(Player player, GameData gameData, String cmd) {
        super(player, gameData, cmd);
        addCondition(ConditionFactory.getDieNotPicked(gameData));
        addCondition(ConditionFactory.getToolNotActive(gameData));
    }

    @Override
    public String getRegExp() {
        return CommandRegExp.PASS;
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
