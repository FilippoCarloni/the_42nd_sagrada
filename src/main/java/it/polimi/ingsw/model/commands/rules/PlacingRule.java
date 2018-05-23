package it.polimi.ingsw.model.commands.rules;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

public class PlacingRule extends RuleDecorator {

    PlacingRule() {
        super(null);
    }

    PlacingRule(Rule decoratedRule) {
        super(decoratedRule);
    }

    private boolean check(Die die, WindowFrame windowFrame, int row, int column) {
        if (die == null || windowFrame == null)
            throw new NullPointerException("Problem in the checking placing rule.");

        int numOfDiceOnMap = 0;
        for (Die d : windowFrame) numOfDiceOnMap++;
        if (numOfDiceOnMap == 0 && (
                (row == 0 && column >= 0 && column < Parameters.MAX_COLUMNS) ||
                (column == 0 && row >= 0 && row < Parameters.MAX_ROWS) ||
                (row == Parameters.MAX_ROWS - 1 && column >= 0 && column < Parameters.MAX_COLUMNS) ||
                (column == Parameters.MAX_COLUMNS - 1 && row >= 0 && row < Parameters.MAX_ROWS)
        )) return true;

        try {
            if (windowFrame.getDie(row - 1, column - 1) != null) return true;
        } catch (IllegalArgumentException e) {}
        try {
            if (windowFrame.getDie(row - 1, column) != null) return true;
        } catch (IllegalArgumentException e) {}
        try {
            if (windowFrame.getDie(row - 1, column + 1) != null) return true;
        } catch (IllegalArgumentException e) {}
        try {
            if (windowFrame.getDie(row, column - 1) != null) return true;
        } catch (IllegalArgumentException e) {}
        try {
            if (windowFrame.getDie(row, column + 1) != null) return true;
        } catch (IllegalArgumentException e) {}
        try {
            if (windowFrame.getDie(row + 1, column - 1) != null) return true;
        } catch (IllegalArgumentException e) {}
        try {
            if (windowFrame.getDie(row + 1, column) != null) return true;
        } catch (IllegalArgumentException e) {}
        try {
            if (windowFrame.getDie(row + 1, column + 1) != null) return true;
        } catch (IllegalArgumentException e) {}
        return false;
    }

    @Override
    public boolean canBePlaced(Die die, WindowFrame windowFrame, int row, int column) {
        return check(die, windowFrame, row, column) &&
                (decoratedRule == null || decoratedRule.canBePlaced(die, windowFrame, row, column));
    }
}
