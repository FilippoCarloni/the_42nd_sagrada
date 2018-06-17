package it.polimi.ingsw.model.commands.rules;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Shade;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.utility.ExceptionMessage.NULL_PARAMETER;

/**
 * Decorates a Rule with the Sagrada shade rule:
 *   - two dice that share the same shade can't be placed orthogonally adjacent
 *   - a die must respect the shade constraint of the window frame
 */
public class ShadeRule extends RuleDecorator {

    public ShadeRule() {
        super(null);
    }

    public ShadeRule(Rule decoratedRule) {
        super(decoratedRule);
    }

    private boolean check(Die die, WindowFrame windowFrame, int row, int column) {
        if (die == null || windowFrame == null)
            throw new NullPointerException(NULL_PARAMETER);
        Shade dieShade = die.getShade();
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
            if (windowDie.getShade().equals(dieShade))
                return false;
        Shade windowConstraint = windowFrame.getShadeConstraint(row, column);
        return !(windowConstraint != null && windowConstraint != dieShade);
    }

    @Override
    public boolean canBePlaced(Die die, WindowFrame windowFrame, int row, int column) {
        return check(die, windowFrame, row, column) &&
                (decoratedRule == null || decoratedRule.canBePlaced(die, windowFrame, row, column));
    }
}
