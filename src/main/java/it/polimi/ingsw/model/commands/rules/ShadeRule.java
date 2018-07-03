package it.polimi.ingsw.model.commands.rules;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Shade;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

import java.util.List;

import static it.polimi.ingsw.model.utility.ExceptionMessage.NULL_PARAMETER;

/**
 * Decorates a Rule with the Sagrada shade rule:
 * <ul>
 *     <li>two dice that share the same shade can't be placed orthogonally adjacent</li>
 *     <li>a die must respect the shade constraint of the window frame</li>
 * </ul>
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
        checkLegalCoordinates(row, column);
        Shade dieShade = die.getShade();
        List<Die> windowDice = appendDice(windowFrame, row, column);
        for (Die windowDie : windowDice)
            if (windowDie.getShade().equals(dieShade))
                return false;
        Shade windowConstraint = windowFrame.getShadeConstraint(row, column);
        return !(windowConstraint != null && windowConstraint != dieShade);
    }

    @Override
    public boolean canBePlaced(Die die, WindowFrame windowFrame, int row, int column) {
        return check(die, windowFrame, row, column) && (decoratedRule == null || decoratedRule.canBePlaced(die, windowFrame, row, column));
    }
}
