package it.polimi.ingsw.model.commands.rules;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.windowframes.Coordinate;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

import static it.polimi.ingsw.model.utility.ExceptionMessage.NULL_PARAMETER;

/**
 * Decorates a Rule with the Sagrada placing rule:
 *   - if the window frame is empty, the first die must be placed on a border slot
 *   - every die placed after the first must be orthogonally or diagonally adjacent to
 *     a previously placed die
 */
public class PlacingRule extends RuleDecorator {

    public PlacingRule() {
        super(null);
    }

    public PlacingRule(Rule decoratedRule) {
        super(decoratedRule);
    }

    private boolean ifMapEmpty(WindowFrame windowFrame, int row, int column) {
        return (windowFrame.getDice().isEmpty() && (
                (row == 0 && Coordinate.validateColumn(column)) ||
                (column == 0 && Coordinate.validateRow(row)) ||
                (row == Parameters.MAX_ROWS - 1 && Coordinate.validateColumn(column)) ||
                (column == Parameters.MAX_COLUMNS - 1 && Coordinate.validateRow(row)))
        );
    }

    private boolean check(Die die, WindowFrame windowFrame, int row, int column) {
        if (die == null || windowFrame == null)
            throw new NullPointerException(NULL_PARAMETER);
        return ifMapEmpty(windowFrame, row, column) ||
            (windowFrame.getDie(row - 1, column - 1) != null) ||
            (windowFrame.getDie(row - 1, column) != null) ||
            (windowFrame.getDie(row - 1, column + 1) != null) ||
            (windowFrame.getDie(row, column - 1) != null) ||
            (windowFrame.getDie(row, column + 1) != null) ||
            (windowFrame.getDie(row + 1, column - 1) != null) ||
            (windowFrame.getDie(row + 1, column) != null) ||
            (windowFrame.getDie(row + 1, column + 1) != null);
    }

    @Override
    public boolean canBePlaced(Die die, WindowFrame windowFrame, int row, int column) {
        return check(die, windowFrame, row, column) &&
                (decoratedRule == null || decoratedRule.canBePlaced(die, windowFrame, row, column));
    }
}
