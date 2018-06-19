package it.polimi.ingsw.model.commands.conditions.windowframeconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_SLOT_ALREADY_OCCUPIED;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_SLOT_UNOCCUPIED;

/**
 * Checks if the selected slot is (not) free from a die.
 */
public class FreeSlot implements Condition {

    private int rowIndex;
    private int columnIndex;
    private boolean value;

    /**
     * Generates a new free-slot checker.
     * @param rowIndex Row to check
     * @param columnIndex Column to check
     * @param value True if the slot should be empty, false otherwise
     */
    public FreeSlot(int rowIndex, int columnIndex, boolean value) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.value = value;
    }

    @Override
    public ConditionPredicate getPredicate() {
        return (gs, args) -> {
            boolean isEmpty = gs.getTurnManager().getCurrentPlayer().getWindowFrame().isEmpty(args[rowIndex], args[columnIndex]);
            return value == isEmpty;
        };
    }

    @Override
    public String getErrorMessage() {
        if (value)
            return ERR_SLOT_ALREADY_OCCUPIED;
        return ERR_SLOT_UNOCCUPIED;
    }
}
