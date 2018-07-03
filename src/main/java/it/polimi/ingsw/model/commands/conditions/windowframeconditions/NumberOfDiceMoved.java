package it.polimi.ingsw.model.commands.conditions.windowframeconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.commands.conditions.argconditions.ArgComparison;

import static it.polimi.ingsw.model.commands.conditions.ConditionID.*;
import static it.polimi.ingsw.model.commands.conditions.ConditionID.EQUAL_TO;
import static it.polimi.ingsw.model.commands.conditions.ConditionID.SMALLER_THAN;
import static it.polimi.ingsw.model.utility.ExceptionMessage.BAD_JSON;
import static java.lang.Integer.parseInt;

/**
 * Checks the number of dice moved during this turn.
 */
public class NumberOfDiceMoved implements Condition {

    private String typeOfComparison;
    private String bound;

    /**
     * Generates a new checker based on the passed arguments.
     * @param typeOfComparison Identifies if the comparison should be >=, < or ==
     * @param bound Identifies the comparison boundary
     */
    public NumberOfDiceMoved(String typeOfComparison, String bound) {
        this.typeOfComparison = typeOfComparison;
        this.bound = bound;
    }

    private int getBound() {
        try {
            return parseInt(bound);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(BAD_JSON + " " + e.getMessage());
        }
    }

    @Override
    public ConditionPredicate getPredicate() {
        switch (typeOfComparison) {
            case GREATER_THAN:
                return (gd, args) -> gd.getDiceMoved().size() >= getBound();
            case SMALLER_THAN:
                return (gd, args) -> gd.getDiceMoved().size() < getBound();
            case EQUAL_TO:
                return (gd, args) -> gd.getDiceMoved().size() == getBound();
            default:
        }
        throw new IllegalArgumentException(BAD_JSON);
    }

    @Override
    public String getErrorMessage() {
        return ArgComparison.getErrorFromArgument(typeOfComparison);
    }
}
