package it.polimi.ingsw.model.commands.conditions.toolconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_TOOL_ACTIVATED;

/**
 * Checks if a Tool Card was not activated during this turn.
 */
public class ToolNotActivated implements Condition {

    @Override
    public ConditionPredicate getPredicate() {
        return (gs, args) -> !gs.isToolActivated();
    }

    @Override
    public String getErrorMessage() {
        return ERR_TOOL_ACTIVATED;
    }
}
