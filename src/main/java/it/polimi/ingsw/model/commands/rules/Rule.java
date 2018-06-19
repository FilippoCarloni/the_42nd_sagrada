package it.polimi.ingsw.model.commands.rules;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

/**
 * Represents a Sagrada die placing rule.
 * Every placing move of a Sagrada player must obey some conditions.
 * These conditions can be divided in three categories:
 * <ul>
 *     <li>Color rules: a die cannot be placed in a cell that holds a color constraint
 *     different from the die color; a die cannot be places adjacent orthogonally
 *     to another same color die</li>
 *     <li>Shade rules: a die cannot be placed in a cell that holds a shade constraint
 *     different from the die shade; a die cannot be places adjacent orthogonally
 *     to another same shade die</li>
 *     <li>Placing rule: a die must be placed adjacent orthogonally or diagonally to
 *     another die; if the window frame does not contain any die, the die must be
 *     placed in a boundary cell</li>
 * </ul>
 */
public interface Rule {

    /**
     * Returns true if the die can be placed in a particular spot of the window frame.
     * This method is not intended to be used alone. You should use the static method of this interface.
     * @param die The die that should be placed
     * @param windowFrame The window frame that will hold the die
     * @param row The placing row
     * @param column The placing column
     * @return True if the operation is legal according to the Sagrada rules
     */
    boolean canBePlaced(Die die, WindowFrame windowFrame, int row, int column);
}
