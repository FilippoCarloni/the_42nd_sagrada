package it.polimi.ingsw.model.commands.conditions.turnstateconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_FIRST_TURN_OF_ROUND;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_SECOND_TURN_OF_ROUND;

/**
 * Checks is it's (or isn't) the first turn of current player during this round.
 */
public class FirstTurnOfRound implements Condition {

    private boolean value;

    /**
     * Generates a new turn number checker based on the passed value.
     * @param value True when checking if it is the first turn; false when checking for the second turn
     */
    public FirstTurnOfRound(boolean value) {
        this.value = value;
    }

    @Override
    public ConditionPredicate getPredicate() {
        if (value)
            return (gameData, args) -> !gameData.getTurnManager().isSecondTurn();
        return (gameData, args) -> gameData.getTurnManager().isSecondTurn();
    }

    @Override
    public String getErrorMessage() {
        if (value)
            return ERR_SECOND_TURN_OF_ROUND;
        return ERR_FIRST_TURN_OF_ROUND;
    }
}
