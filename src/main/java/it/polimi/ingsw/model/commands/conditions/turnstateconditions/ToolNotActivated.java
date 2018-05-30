package it.polimi.ingsw.model.commands.conditions.turnstateconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.gamedata.GameData;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_TOOL_ACTIVATED;

public class ToolNotActivated extends Condition {

    public ToolNotActivated(GameData gameData) {
        super(gameData, new int[0], ERR_TOOL_ACTIVATED);
    }

    @Override
    public ConditionPredicate getPredicate() {
        return (gs, args) -> !gs.isToolActivated();
    }
}
