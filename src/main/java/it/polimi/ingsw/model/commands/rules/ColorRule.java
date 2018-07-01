package it.polimi.ingsw.model.commands.rules;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.utility.ExceptionMessage.NULL_PARAMETER;

/**
 * Decorates a Rule with the Sagrada color rule:
 *   - two dice that share the same color can't be placed orthogonally adjacent
 *   - a die must respect the color constraint of the window frame
 */
public class ColorRule extends RuleDecorator {

    public ColorRule() {
        super(null);
    }

    public ColorRule(Rule decoratedRule) {
        super(decoratedRule);
    }

    private boolean check(Die die, WindowFrame windowFrame, int row, int column) {
        if (die == null || windowFrame == null)
            throw new NullPointerException(NULL_PARAMETER);
        checkLegalCoordinates(row, column);
        Color dieColor = die.getColor();
        List<Die> windowDice = new ArrayList<>();
        Die d;
        d = windowFrame.getDie(row, column - 1);
        if (d != null) windowDice.add(d);
        d = windowFrame.getDie(row - 1, column);
        if (d != null) windowDice.add(d);
        d = windowFrame.getDie(row, column + 1);
        if (d != null) windowDice.add(d);
        d = windowFrame.getDie(row + 1, column);
        if (d != null) windowDice.add(d);
        for (Die windowDie : windowDice)
            if (windowDie.getColor().equals(dieColor))
                return false;
        Color windowConstraint = windowFrame.getColorConstraint(row, column);
        return !(windowConstraint != null && windowConstraint != dieColor);
    }

    @Override
    public boolean canBePlaced(Die die, WindowFrame windowFrame, int row, int column) {
        return check(die, windowFrame, row, column) && (decoratedRule == null || decoratedRule.canBePlaced(die, windowFrame, row, column));
    }
}
