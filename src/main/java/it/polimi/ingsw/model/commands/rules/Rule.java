package it.polimi.ingsw.model.commands.rules;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

/**
 * Represents a Sagrada die placing rule.
 * Every placing move of a Sagrada player must obey some conditions.
 * These conditions can be divided in three categories:
 *     - Color rules: a die cannot be placed in a cell that holds a color constraint
 *       different from the die color; a die cannot be places adjacent orthogonally
 *       to another same color die
 *     - Shade rules: a die cannot be placed in a cell that holds a shade constraint
 *       different from the die shade; a die cannot be places adjacent orthogonally
 *       to another same shade die
 *     - Placing rule: a die must be placed adjacent orthogonally or diagonally to
 *       another die; if the window frame does not contain any die, the die must be
 *       placed in a boundary cell.
 */
public interface Rule {

    /**
     * Returns true if the die can be placed in a particular spot of the window frame.
     * It checks all the Sagrada rules.
     * @param die The die that should be placed
     * @param windowFrame The window frame that will hold the die
     * @param row The placing row
     * @param column The placing column
     * @return True if the operation is legal according to the Sagrada rules
     */
    static boolean checkAllRules(Die die, WindowFrame windowFrame, int row, int column) {
        return new PlacingRule(new ShadeRule(new ColorRule()))
                .canBePlaced(die, windowFrame, row, column);
    }

    /**
     * Returns true if the die can be placed in a particular spot of the window frame.
     * It checks all the Sagrada rules except from the Color one.
     * @param die The die that should be placed
     * @param windowFrame The window frame that will hold the die
     * @param row The placing row
     * @param column The placing column
     * @return True if the operation is legal according to the Sagrada rules
     */
    static boolean checkExcludeColor(Die die, WindowFrame windowFrame, int row, int column) {
        return new PlacingRule(new ShadeRule())
                .canBePlaced(die, windowFrame, row, column);
    }

    /**
     * Returns true if the die can be placed in a particular spot of the window frame.
     * It checks all the Sagrada rules except from the Shade one.
     * @param die The die that should be placed
     * @param windowFrame The window frame that will hold the die
     * @param row The placing row
     * @param column The placing column
     * @return True if the operation is legal according to the Sagrada rules
     */
    static boolean checkExcludeShade(Die die, WindowFrame windowFrame, int row, int column) {
        return new ColorRule(new PlacingRule())
                .canBePlaced(die, windowFrame, row, column);
    }

    /**
     * Returns true if the die can be placed in a particular spot of the window frame.
     * It checks all the Sagrada rules except from the placing one.
     * @param die The die that should be placed
     * @param windowFrame The window frame that will hold the die
     * @param row The placing row
     * @param column The placing column
     * @return True if the operation is legal according to the Sagrada rules
     */
    static boolean checkExcludePlacing(Die die, WindowFrame windowFrame, int row, int column) {
        return new ShadeRule(new ColorRule())
                .canBePlaced(die, windowFrame, row, column);
    }

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
