package it.polimi.ingsw.model.commands.rules;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

import java.util.ArrayList;
import java.util.List;

class ColorRule extends RuleDecorator {

    ColorRule() {
        super(null);
    }

    ColorRule(Rule decoratedRule) {
        super(decoratedRule);
    }

    private boolean check(Die die, WindowFrame windowFrame, int row, int column) {
        if (die == null || windowFrame == null)
            throw new NullPointerException("Problem in the checking color rule.");
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
        return check(die, windowFrame, row, column) &&
                (decoratedRule == null || decoratedRule.canBePlaced(die, windowFrame, row, column));
    }
}
