package it.polimi.ingsw.model.commands.rules;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

public interface Rule {

    boolean canBePlaced(Die die, WindowFrame windowFrame, int row, int column);

    static boolean checkAllRules(Die die, WindowFrame windowFrame, int row, int column) {
        return new ColorRule(new ShadeRule(new PlacingRule()))
                .canBePlaced(die, windowFrame, row, column);
    }

    static boolean checkExcludeColor(Die die, WindowFrame windowFrame, int row, int column) {
        return new ShadeRule(new PlacingRule())
                .canBePlaced(die, windowFrame, row, column);
    }

    static boolean checkExcludeShade(Die die, WindowFrame windowFrame, int row, int column) {
        return new ColorRule(new PlacingRule())
                .canBePlaced(die, windowFrame, row, column);
    }

    static boolean checkExcludePlacing(Die die, WindowFrame windowFrame, int row, int column) {
        return new ShadeRule(new ColorRule())
                .canBePlaced(die, windowFrame, row, column);
    }
}
