package it.polimi.ingsw.model.commands.conditions.turnstateconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_DIE_NOT_PLACED;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_DIE_PLACED;

public class DiePlaced implements Condition {

    private boolean value;

    public DiePlaced(boolean value) {
        this.value = value;
    }

    @Override
    public ConditionPredicate getPredicate() {
        if (value)
            return (gs, args) -> gs.isDiePlaced();
        return (gs, args) -> !gs.isDiePlaced();
    }

    @Override
    public String getErrorMessage() {
        if (value)
            return ERR_DIE_NOT_PLACED;
        return ERR_DIE_PLACED;
    }
}
