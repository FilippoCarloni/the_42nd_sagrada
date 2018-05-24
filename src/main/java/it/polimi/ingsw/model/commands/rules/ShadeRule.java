package it.polimi.ingsw.model.commands.rules;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Shade;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

import java.util.ArrayList;
import java.util.List;

class ShadeRule extends RuleDecorator {

    ShadeRule() {
        super(null);
    }

    ShadeRule(Rule decoratedRule) {
        super(decoratedRule);
    }

    private boolean check(Die die, WindowFrame windowFrame, int row, int column) {
        if (die == null || windowFrame == null)
            throw new NullPointerException("Problem in the checking shade rule.");
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
