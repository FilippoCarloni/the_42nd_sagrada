package it.polimi.ingsw.model.gameboard.cards.publicobjectives.patterns;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.utility.Shade;

import java.util.HashSet;
import java.util.Set;

public class DifferencePattern implements FramePattern {

    private boolean rowsOrColumns;
    private boolean colorOrShade;
    private Set<Color> colors;
    private Set<Shade> shades;

    DifferencePattern(String place, String object) {
        rowsOrColumns = place.equals(PatternID.ROWS);
        colorOrShade = object.equals(PatternID.COLOR);
    }

    @Override
    public int getNumOfPatterns(WindowFrame windowFrame) {
        if (rowsOrColumns) return cycleRows(windowFrame);
        return cycleColumns(windowFrame);
    }

    private int cycleColumns(WindowFrame windowFrame) {
        int patterns = 0;
        for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
            resetSet();
            for (int i = 0; i < Parameters.MAX_ROWS; i++) {
                Die die = windowFrame.getDie(i, j);
                if (die != null)
                    addTrait(die);
            }
            if (checkCompleteness())
                patterns++;
        }
        return patterns;
    }

    private int cycleRows(WindowFrame windowFrame) {
        int patterns = 0;
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            resetSet();
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                Die die = windowFrame.getDie(i, j);
                if (die != null)
                    addTrait(die);
            }
            if (checkCompleteness())
                patterns++;
        }
        return patterns;
    }

    private void resetSet() {
        if (colorOrShade) colors = new HashSet<>();
        else shades = new HashSet<>();
    }

    private void addTrait(Die die) {
        if (colorOrShade) colors.add(die.getColor());
        else shades.add(die.getShade());
    }

    private boolean checkCompleteness() {
        int target;
        if (rowsOrColumns) target = Parameters.MAX_COLUMNS;
        else target = Parameters.MAX_ROWS;
        return (colorOrShade && colors.size() >= target) ||
                (!colorOrShade && shades.size() >= target);
    }
}
