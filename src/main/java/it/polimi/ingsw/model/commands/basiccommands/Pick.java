package it.polimi.ingsw.model.commands.basiccommands;

import it.polimi.ingsw.model.commands.AbstractCommand;
import it.polimi.ingsw.model.commands.CommandRegExp;
import it.polimi.ingsw.model.commands.conditions.ConditionFactory;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;

public class Pick extends AbstractCommand {

    public Pick(Player player, GameData gameData, String cmd) {
        super(player, gameData, cmd);
        addCondition(ConditionFactory.getDieNotPicked(gameData));
        addCondition(ConditionFactory.getIndexSmallerThan(gameData, getArgs(), 0, gameData.getDicePool().size()));
        addCondition(ConditionFactory.getIndexGreaterThan(gameData, getArgs(), 0, 0));
        addCondition(ConditionFactory.getToolNotActive(gameData));
        addCondition(ConditionFactory.getDieNotPlaced(gameData));
    }

    @Override
    public String getRegExp() {
        return CommandRegExp.PICK;
    }

    @Override
    public void executionWhenLegal() {
        getGameData().setPickedDie(getGameData().getDicePool().remove(getArgs()[0]));
    }

    @Override
    public boolean undoable() {
        return true;
    }
}
