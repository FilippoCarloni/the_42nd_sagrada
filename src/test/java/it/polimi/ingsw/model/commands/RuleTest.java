package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.commands.rules.ColorRule;
import it.polimi.ingsw.model.commands.rules.PlacingRule;
import it.polimi.ingsw.model.commands.rules.ShadeRule;
import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.utility.Shade;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RuleTest {

    /**
     * Checks correct exception handling on illegal parameters.
     */
    @Test
    void exceptionTest() {
        WindowFrame wf = (WindowFrame) new WindowFrameDeck().draw();
        Die die = new ArrayDiceBag().pick();
        assertThrows(NullPointerException.class, () -> new ColorRule().canBePlaced(null, null, 0, 0));
        assertThrows(NullPointerException.class, () -> new ShadeRule().canBePlaced(null, null, 0, 0));
        assertThrows(NullPointerException.class, () -> new PlacingRule().canBePlaced(null, null, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new ColorRule().canBePlaced(die, wf, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> new ColorRule().canBePlaced(die, wf, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> new ColorRule().canBePlaced(die, wf, 4, 0));
        assertThrows(IllegalArgumentException.class, () -> new ColorRule().canBePlaced(die, wf, 0, 5));
        assertThrows(IllegalArgumentException.class, () -> new ShadeRule().canBePlaced(die, wf, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> new ShadeRule().canBePlaced(die, wf, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> new ShadeRule().canBePlaced(die, wf, 4, 0));
        assertThrows(IllegalArgumentException.class, () -> new ShadeRule().canBePlaced(die, wf, 0, 5));
        assertThrows(IllegalArgumentException.class, () -> new PlacingRule().canBePlaced(die, wf, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> new PlacingRule().canBePlaced(die, wf, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> new PlacingRule().canBePlaced(die, wf, 4, 0));
        assertThrows(IllegalArgumentException.class, () -> new PlacingRule().canBePlaced(die, wf, 0, 5));
    }

    /**
     * Checks the Placing rule.
     */
    @Test
    void placingTest() {
        DiceBag db = new ArrayDiceBag();
        Deck d = new WindowFrameDeck();
        WindowFrame w = (WindowFrame) d.draw();
        // a die can be placed in every boundary slot if the map is empty
        Die d1 = db.pick();
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                if (i == 0 || j == 0 || i == Parameters.MAX_ROWS - 1 || j == Parameters.MAX_COLUMNS - 1)
                    assertTrue((new PlacingRule()).canBePlaced(d1, w, i, j));
                else
                    assertFalse((new PlacingRule()).canBePlaced(d1, w, i, j));
            }
        }
        // a die can be placed in a spot adjacent to another die
        int row = (int) (Math.random() * Parameters.MAX_ROWS);
        int column = (int) (Math.random() * Parameters.MAX_COLUMNS);
        w.put(d1, row, column);
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                if (inBoundary(i, j, row, column))
                    assertTrue((new PlacingRule()).canBePlaced(d1, w, i, j));
                else
                    assertFalse((new PlacingRule()).canBePlaced(d1, w, i, j));
            }
        }
    }

    private boolean inBoundary(int row, int column, int br, int bc) {
        boolean sameRow = row == br;
        boolean nextRow = row == br + 1;
        boolean prevRow = row == br - 1;
        boolean sameCol = column == bc;
        boolean nextCol = column == bc + 1;
        boolean prevCol = column == bc - 1;
        return  (prevRow && (sameCol || nextCol || prevCol)) ||
                (sameRow && (prevCol || nextCol)) ||
                (nextRow && (sameCol || nextCol || prevCol));
    }

    /**
     * Checks matching with window frame color constraints.
     */
    @Test
    void colorConstraintTest() {
        WindowFrame wf = (WindowFrame) new WindowFrameDeck().draw();
        Die die = new ArrayDiceBag().pick();
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                if (wf.getColorConstraint(i, j) == null || wf.getColorConstraint(i, j).equals(die.getColor()))
                    assertTrue((new ColorRule()).canBePlaced(die, wf, i, j));
                else
                    assertFalse((new ColorRule()).canBePlaced(die, wf, i, j));
            }
        }
    }

    /**
     * Checks matching with window frame shade constraints.
     */
    @Test
    void shadeConstraintTest() {
        WindowFrame wf = (WindowFrame) new WindowFrameDeck().draw();
        Die die = new ArrayDiceBag().pick();
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                if (wf.getShadeConstraint(i, j) == null || wf.getShadeConstraint(i, j).equals(die.getShade()))
                    assertTrue((new ShadeRule()).canBePlaced(die, wf, i, j));
                else
                    assertFalse((new ShadeRule()).canBePlaced(die, wf, i, j));
            }
        }
    }

    /**
     * Generic test on a particular window frame
     */
    @Test
    void customTest1() {
        /*  Batllo pattern:
            --6--
            -5B4-
            3GYP2
            14R53
         */
        DiceBag db = new ArrayDiceBag();
        Deck d = new WindowFrameDeck();
        WindowFrame w = (WindowFrame) d.draw();
        while (!w.getName().equals("Batllo")) w = (WindowFrame) d.draw();
        // initialize dice
        Die d1 = db.pick(); // 3R die
        d1.setShade(Shade.LIGHT);
        d1.setColor(Color.RED);
        Die d2 = db.pick(); // 6G die
        d2.setColor(Color.GREEN);
        d2.setShade(Shade.DARKEST);
        // assertions
        assertTrue(new PlacingRule(new ColorRule(new ShadeRule())).canBePlaced(d1, w, 2, 0));
        assertFalse(new PlacingRule(new ColorRule(new ShadeRule())).canBePlaced(d1, w, 3, 0));
        w.put(d1, 2, 0);
        assertTrue(new ColorRule(new ShadeRule()).canBePlaced(d1, w, 3, 4));
        assertTrue(new ColorRule(new PlacingRule(new ShadeRule())).canBePlaced(d2, w, 2, 1));
        assertFalse(new ShadeRule(new ColorRule(new PlacingRule())).canBePlaced(d1, w, 2, 1));
        assertFalse(new ShadeRule(new PlacingRule(new ColorRule())).canBePlaced(d1, w, 1, 0));
        assertFalse(new ColorRule(new PlacingRule(new ShadeRule())).canBePlaced(d1, w, 3, 0));
        assertFalse(new ColorRule(new PlacingRule(new ShadeRule())).canBePlaced(d1, w, 3, 1));
        assertFalse(new ColorRule(new PlacingRule(new ShadeRule())).canBePlaced(d1, w, 3, 2));
    }

    /**
     * Generic test on a particular window frame
     */
    @Test
    void customTest2() {
        /*  Industria pattern:
            1R3-6
            54R2-
            --5R1
            ---3R
         */
        DiceBag db = new ArrayDiceBag();
        Deck d = new WindowFrameDeck();
        WindowFrame w = (WindowFrame) d.draw();
        while (!w.getName().equals("Industria")) w = (WindowFrame) d.draw();
        // initialize dice
        Die d1 = db.pick(); // 6R
        d1.setShade(Shade.DARKEST);
        d1.setColor(Color.RED);
        Die d2 = db.pick(); // 3R
        d2.setColor(Color.RED);
        d2.setShade(Shade.LIGHT);
        Die d3 = db.pick(); // 3B
        d3.setColor(Color.BLUE);
        d3.setShade(Shade.LIGHT);
        // assertions
        assertTrue(new ColorRule(new PlacingRule(new ShadeRule())).canBePlaced(d1, w, 0, 1));
        w.put(d1, 0, 1);
        assertTrue(new ColorRule(new PlacingRule(new ShadeRule())).canBePlaced(d2, w, 1, 2));
        w.put(d2, 1, 2);
        assertTrue(new ColorRule(new PlacingRule(new ShadeRule())).canBePlaced(d3, w, 0, 3));
        w.put(d3, 0, 3);
        assertTrue(new ColorRule(new PlacingRule()).canBePlaced(w.pick(0, 3), w, 0, 2));
    }
}
