package it.polimi.ingsw.model.commands.basiccommands;

import it.polimi.ingsw.model.commands.AbstractCommand;
import it.polimi.ingsw.model.commands.CommandRegExp;
import it.polimi.ingsw.model.commands.conditions.ConditionFactory;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;

public class Place extends AbstractCommand {

    public Place(Player player, GameData gameData, String cmd) {
        super(player, gameData, cmd);
        addCondition(ConditionFactory.getDiePicked(gameData));
        addCondition(ConditionFactory.getDieNotPlaced(gameData));
        addCondition(ConditionFactory.getToolNotActive(gameData));
        addCondition(ConditionFactory.getValidCoordinates(gameData, getArgs()));
        addCondition(ConditionFactory.getFreeSlot(gameData, getArgs()));
        addCondition(ConditionFactory.getPlacingRuleCheck(gameData, getArgs()));
    }

    @Override
    public String getRegExp() {
        return CommandRegExp.PLACE;
    }

    @Override
    public void executionWhenLegal() {
        getGameData().getTurnManager().getCurrentPlayer().getWindowFrame()
                .put(getGameData().getPickedDie(), getArgs()[0], getArgs()[1]);
        getGameData().setPickedDie(null);
        getGameData().setDiePlaced(true);
    }

    @Override
    public boolean undoable() {
        return true;
    }
}
