package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.commands.rules.Rule;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

class RuleTest {

    @Test
    void placingTest() {
        DiceBag db = new ArrayDiceBag();
        Deck d = new WindowFrameDeck();
        WindowFrame w = (WindowFrame) d.draw();
        while (!w.getName().equals("Batllo")) {
            w = (WindowFrame) d.draw();
        }

        Die d1 = db.pick();
        d1.setShade(Shade.LIGHTEST);
        d1.setColor(Color.RED);

        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                if (i == 0 || j == 0 || i == Parameters.MAX_ROWS - 1 || j == Parameters.MAX_COLUMNS - 1)
                    assertTrue(Rule.checkExcludeShade(d1, w, i, j));
                else
                    assertFalse(Rule.checkExcludeShade(d1, w, i, j));
            }
        }
    }

    @Test
    void shadeAndColorTest() {
        DiceBag db = new ArrayDiceBag();
        Deck d = new WindowFrameDeck();
        WindowFrame w = (WindowFrame) d.draw();
        while (!w.getName().equals("Batllo")) {
            w = (WindowFrame) d.draw();
        }

        Die d1 = db.pick();
        d1.setShade(Shade.LIGHT);
        d1.setColor(Color.RED);
        Die d2 = db.pick();
        d2.setColor(Color.GREEN);
        d2.setShade(Shade.DARKEST);

        assertTrue(Rule.checkAllRules(d1, w, 2, 0));
        assertFalse(Rule.checkAllRules(d1, w, 3, 0));

        w.put(d1, 2, 0);

        assertTrue(Rule.checkExcludePlacing(d1, w, 3, 4));
        assertTrue(Rule.checkAllRules(d2, w, 2, 1));

        assertFalse(Rule.checkAllRules(d1, w, 2, 1));
        assertFalse(Rule.checkAllRules(d1, w, 1, 0));
        assertFalse(Rule.checkAllRules(d1, w, 3, 0));
        assertFalse(Rule.checkAllRules(d1, w, 3, 1));
        assertFalse(Rule.checkAllRules(d1, w, 3, 2));
    }

    @Test
    void deeperTest() {
        DiceBag db = new ArrayDiceBag();
        Deck d = new WindowFrameDeck();
        WindowFrame w = (WindowFrame) d.draw();
        while (!w.getName().equals("Industria")) {
            w = (WindowFrame) d.draw();
        }

        Die d1 = db.pick();
        d1.setShade(Shade.DARKEST);
        d1.setColor(Color.RED);
        Die d2 = db.pick();
        d2.setColor(Color.RED);
        d2.setShade(Shade.LIGHT);
        Die d3 = db.pick();
        d3.setColor(Color.BLUE);
        d3.setShade(Shade.LIGHT);

        assertTrue(Rule.checkAllRules(d1, w, 0, 1));
        w.put(d1, 0, 1);
        assertTrue(Rule.checkAllRules(d2, w, 1, 2));
        w.put(d2, 1, 2);
        assertTrue(Rule.checkAllRules(d3, w, 0, 3));
        w.put(d3, 0, 3);
        assertTrue(Rule.checkExcludeShade(w.pick(0, 3), w, 0, 2));
    }
}
