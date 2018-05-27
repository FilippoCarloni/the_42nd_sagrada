package it.polimi.ingsw.model.commands.conditions;

import it.polimi.ingsw.model.gamedata.GameData;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_TOOL_ACTIVE;

public class ToolNotActive extends Condition {

    private static final ConditionPredicate predicate = (gs, args) -> gs.getActiveToolID() == 0;

    public ToolNotActive(GameData gameData) {
        super(gameData, new int[0], predicate, ERR_TOOL_ACTIVE);
    }
}
