package it.polimi.ingsw.model.commands.conditions.toolconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_TOOL_ACTIVE;

public class ToolNotActive implements Condition {

    @Override
    public ConditionPredicate getPredicate() {
        return (gs, args) -> gs.getActiveToolID() == 0;
    }

    @Override
    public String getErrorMessage() {
        return ERR_TOOL_ACTIVE;
    }
}
