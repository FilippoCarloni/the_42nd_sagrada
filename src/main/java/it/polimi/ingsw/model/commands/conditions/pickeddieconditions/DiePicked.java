package it.polimi.ingsw.model.commands.conditions.pickeddieconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_DIE_NOT_PICKED;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_DIE_PICKED;

/**
 * Checks if a die was already picked from the dice pool this turn.
 */
public class DiePicked implements Condition {

    private boolean value;

    /**
     * Generates a new checker instance from the passed value.
     * @param value The value that the diePicked status of the current GameData should equal
     */
    public DiePicked(boolean value) {
        this.value = value;
    }

    @Override
    public ConditionPredicate getPredicate() {
        return (gs, args) -> {
            boolean diePicked = gs.getPickedDie() != null;
            boolean passiveToolActivated = gs.getPassiveToolID() != 0 && gs.getActiveToolID() == 0;
            return diePicked == value || (diePicked && passiveToolActivated);
        };
    }

    @Override
    public String getErrorMessage() {
        if (value)
            return ERR_DIE_NOT_PICKED;
        return ERR_DIE_PICKED;
    }
}
