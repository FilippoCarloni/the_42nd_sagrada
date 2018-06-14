package it.polimi.ingsw.model.gameboard.cards.publicobjectives.patterns;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Shade;
import org.json.simple.JSONArray;

import static it.polimi.ingsw.model.gameboard.cards.publicobjectives.patterns.PatternID.COLOR;
import static java.lang.Integer.parseInt;

/**
 * Implements the set pattern that identifies a particular set of dice on the window frame.
 */
public class SetPattern implements FramePattern {

    private boolean color;
    private JSONArray values;
    private Color[] colors;
    private Shade[] shades;

    SetPattern(String object, JSONArray values) {
        color = object.equals(COLOR);
        this.values = values;
        if (color) {
            colors = new Color[values.size()];
            int i = 0;
            for (Object o : values) {
                colors[i] = Color.findByLabel(o.toString());
                i++;
            }
        } else {
            shades = new Shade[values.size()];
            int i = 0;
            for (Object o : values) {
                shades[i] = Shade.findByValue(parseInt(o.toString()));
                i++;
            }
        }
    }

    @Override
    public int getNumOfPatterns(WindowFrame windowFrame) {
        int[] occurrences = new int[values.size()];
        for (Die die : windowFrame.getDice())
            updateOccurrences(occurrences, die);
        return getMin(occurrences);
    }

    private void updateOccurrences(int[] occurrences, Die die) {
        if (color) {
            assert colors != null;
            Color dieColor = die.getColor();
            for (int i = 0; i < colors.length; i++)
                if (dieColor.equals(colors[i]))
                    occurrences[i]++;
        } else {
            assert shades != null;
            Shade dieShade = die.getShade();
            for (int i = 0; i < shades.length; i++)
                if (dieShade.equals(shades[i]))
                    occurrences[i]++;
        }
    }

    private int getMin(int[] array) {
        assert array.length > 0;
        int min = array[0];
        for (int n : array)
            if (n < min)
                min = n;
        return min;
    }
}
