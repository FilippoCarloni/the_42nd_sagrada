package it.polimi.ingsw.model.commands.conditions.windowframeconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.gameboard.windowframes.Coordinate;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_INVALID_COORDINATES;

/**
 * Generates a window frame coordinates validator.
 */
public class ValidCoordinates implements Condition {

    @Override
    public ConditionPredicate getPredicate() {
        return (gd, args) -> {
            boolean valid = true;
            for (int i = 0; valid && i < args.length; i++) {
                if (i % 2 == 0) {
                    valid = Coordinate.validateRow(args[i]);
                } else {
                    valid = Coordinate.validateColumn(args[i]);
                }
            }
            return valid;
        };
    }

    @Override
    public String getErrorMessage() {
        return ERR_INVALID_COORDINATES;
    }
}
