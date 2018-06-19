package it.polimi.ingsw.model.commands.conditions.windowframeconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_DIE_ALREADY_MOVED;

/**
 * Checks if during this turn a die has not already been moved.
 */
public class NotDieAlreadyMoved implements Condition {

    @Override
    public ConditionPredicate getPredicate() {
        return (gameData, args) -> !gameData.getDiceMoved().contains(gameData.getTurnManager().getCurrentPlayer().getWindowFrame().getDie(args[0], args[1]));
    }

    @Override
    public String getErrorMessage() {
        return ERR_DIE_ALREADY_MOVED;
    }
}
