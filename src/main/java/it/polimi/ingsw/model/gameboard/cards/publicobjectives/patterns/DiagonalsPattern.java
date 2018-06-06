package it.polimi.ingsw.model.gameboard.cards.publicobjectives.patterns;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.utility.Shade;

public class DiagonalsPattern implements FramePattern {

    private boolean shadeOrColor;

    DiagonalsPattern(String object) {
        shadeOrColor = object.equals(PatternID.SHADE);
    }

    @Override
    public int getNumOfPatterns(WindowFrame windowFrame) {
        int patterns = 0;
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                if (shadeOrColor) {
                    if (checkShadeDiagonalPresence(windowFrame, i, j)) patterns++;
                } else {
                    if (checkColorDiagonalPresence(windowFrame, i, j)) patterns++;
                }
            }
        }
        return patterns;
    }

    private boolean checkColorDiagonalPresence(WindowFrame windowFrame, int row, int column) {
        if (windowFrame.getDie(row, column) == null) return false;
        Color color = windowFrame.getDie(row, column).getColor();
        Die die = windowFrame.getDie(row - 1, column - 1);
        if (die != null && die.getColor().equals(color)) return true;
        die = windowFrame.getDie(row - 1, column + 1);
        if (die != null && die.getColor().equals(color)) return true;
        die = windowFrame.getDie(row + 1, column - 1);
        if (die != null && die.getColor().equals(color)) return true;
        die = windowFrame.getDie(row + 1, column + 1);
        return (die != null && die.getColor().equals(color));
    }

    private boolean checkShadeDiagonalPresence(WindowFrame windowFrame, int row, int column) {
        if (windowFrame.getDie(row, column) == null) return false;
        Shade shade = windowFrame.getDie(row, column).getShade();
        Die die = windowFrame.getDie(row - 1, column - 1);
        if (die != null && die.getShade().equals(shade)) return true;
        die = windowFrame.getDie(row - 1, column + 1);
        if (die != null && die.getShade().equals(shade)) return true;
        die = windowFrame.getDie(row + 1, column - 1);
        if (die != null && die.getShade().equals(shade)) return true;
        die = windowFrame.getDie(row + 1, column + 1);
        return (die != null && die.getShade().equals(shade));
    }
}
