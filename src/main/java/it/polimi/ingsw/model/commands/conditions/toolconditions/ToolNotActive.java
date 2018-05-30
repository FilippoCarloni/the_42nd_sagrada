package it.polimi.ingsw.model.commands.conditions.toolconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.gamedata.GameData;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_TOOL_ACTIVE;

public class ToolNotActive extends Condition {

    public ToolNotActive(GameData gameData) {
        super(gameData, new int[0], ERR_TOOL_ACTIVE);
    }

    @Override
    public ConditionPredicate getPredicate() {
        return (gs, args) -> gs.getActiveToolID() == 0;
    }
}
