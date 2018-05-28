package it.polimi.ingsw.model.commands.basiccommands;

import it.polimi.ingsw.model.commands.AbstractCommand;
import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.DieNotPicked;
import it.polimi.ingsw.model.commands.conditions.DieNotPlaced;
import it.polimi.ingsw.model.commands.conditions.ToolNotActive;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_INDEX_TOO_BIG;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_INDEX_TOO_SMALL;

public class Pick extends AbstractCommand {

    public Pick(Player player, GameData gameData, String cmd) {
        super(player, gameData, cmd);
        addCondition(new DieNotPicked(gameData));
        addCondition(new Condition(gameData, getArgs(), (gs, args) -> args[0] < gs.getDicePool().size(), ERR_INDEX_TOO_BIG));
        addCondition(new Condition(gameData, getArgs(), (gs, args) -> args[0] >= 0, ERR_INDEX_TOO_SMALL));
        addCondition(new ToolNotActive(gameData));
        addCondition(new DieNotPlaced(gameData));
    }

    @Override
    public String getRegExp() {
        return "pick \\d";
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
