package it.polimi.ingsw.model.commands.conditions.pickeddieconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_DIE_NOT_PICKED;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_DIE_PICKED;

public class DiePicked implements Condition {

    private boolean value;

    public DiePicked(boolean value) {
        this.value = value;
    }

    @Override
    public ConditionPredicate getPredicate() {
        return (gs, args) -> (gs.getPickedDie() != null) == value;
    }

    @Override
    public String getErrorMessage() {
        if (value)
            return ERR_DIE_NOT_PICKED;
        return ERR_DIE_PICKED;
    }
}
