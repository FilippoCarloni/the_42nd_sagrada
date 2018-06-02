package it.polimi.ingsw.model.commands.conditions.windowframeconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_DIFFERENT_INDEX;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_INDEX_TOO_BIG;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_INDEX_TOO_SMALL;
import static it.polimi.ingsw.model.commands.conditions.ConditionID.*;
import static it.polimi.ingsw.model.commands.conditions.ConditionID.EQUAL_TO;
import static it.polimi.ingsw.model.commands.conditions.ConditionID.SMALLER_THAN;
import static java.lang.Integer.parseInt;

public class NumberOfDiceMoved implements Condition {

    private static final String DEFAULT_ERROR_MESSAGE = "Bad JSON format.";
    private String typeOfComparison;
    private String bound;

    public NumberOfDiceMoved(String typeOfComparison, String bound) {
        this.typeOfComparison = typeOfComparison;
        this.bound = bound;
    }

    private int getBound() {
        try {
            return parseInt(bound);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(DEFAULT_ERROR_MESSAGE + " " + e.getMessage());
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
        throw new IllegalArgumentException(DEFAULT_ERROR_MESSAGE);
    }

    @Override
    public String getErrorMessage() {
        switch (typeOfComparison) {
            case GREATER_THAN:
                return ERR_INDEX_TOO_SMALL;
            case SMALLER_THAN:
                return ERR_INDEX_TOO_BIG;
            case EQUAL_TO:
                return ERR_DIFFERENT_INDEX;
            default:
        }
        throw new IllegalArgumentException(DEFAULT_ERROR_MESSAGE);
    }
}
