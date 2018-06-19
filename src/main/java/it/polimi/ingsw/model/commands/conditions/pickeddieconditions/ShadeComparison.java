package it.polimi.ingsw.model.commands.conditions.pickeddieconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.utility.Shade;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_CANNOT_DECREASE;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_CANNOT_INCREASE;
import static it.polimi.ingsw.model.commands.conditions.ConditionID.*;
import static it.polimi.ingsw.model.utility.ExceptionMessage.BAD_JSON;

/**
 * Compares the shade of the picked die to a particular value.
 */
public class ShadeComparison implements Condition {

    private String typeOfComparison;
    private String bound;

    /**
     * Generates a new shade checker instance from the given parameters.
     * @param typeOfComparison A comparison type string (identifies >=, < or ==)
     * @param bound The bound that should be checked
     */
    public ShadeComparison(String typeOfComparison, String bound) {
        this.typeOfComparison = typeOfComparison;
        this.bound = bound;
    }

    private int getBound() {
        if (bound.equals(MAXIMUM_SHADE))
            return Shade.getMaximumValue();
        else if (bound.equals(MINIMUM_SHADE))
            return Shade.getMinimumValue();
        throw new IllegalArgumentException(BAD_JSON);
    }

    @Override
    public ConditionPredicate getPredicate() {
        if (typeOfComparison.equals(GREATER_THAN))
            return (gd, args) -> gd.getPickedDie().getShade().getValue() > getBound();
        else if (typeOfComparison.equals(SMALLER_THAN))
            return (gd, args) -> gd.getPickedDie().getShade().getValue() < getBound();
        throw new IllegalArgumentException(BAD_JSON);
    }

    @Override
    public String getErrorMessage() {
        if (bound.equals(MAXIMUM_SHADE))
            return ERR_CANNOT_INCREASE;
        else if (bound.equals(MINIMUM_SHADE))
            return ERR_CANNOT_DECREASE;
        throw new IllegalArgumentException(BAD_JSON);
    }
}
