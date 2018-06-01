package it.polimi.ingsw.model.commands.conditions.windowframeconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_SLOT_ALREADY_OCCUPIED;

public class FreeSlot implements Condition {

    @Override
    public ConditionPredicate getPredicate() {
        return (gs, args) -> gs.getTurnManager().getCurrentPlayer().getWindowFrame().isEmpty(args[0], args[1]);
    }

    @Override
    public String getErrorMessage() {
        return ERR_SLOT_ALREADY_OCCUPIED;
    }
}
