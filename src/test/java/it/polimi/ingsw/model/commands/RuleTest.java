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
                    assertTrue(new ColorRule(new PlacingRule()).canBePlaced(d1, w, i, j));
                else
                    assertFalse(new ColorRule(new PlacingRule()).canBePlaced(d1, w, i, j));
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

        assertTrue(new ColorRule(new PlacingRule(new ShadeRule())).canBePlaced(d1, w, 0, 1));
        w.put(d1, 0, 1);
        assertTrue(new ColorRule(new PlacingRule(new ShadeRule())).canBePlaced(d2, w, 1, 2));
        w.put(d2, 1, 2);
        assertTrue(new ColorRule(new PlacingRule(new ShadeRule())).canBePlaced(d3, w, 0, 3));
        w.put(d3, 0, 3);
        assertTrue(new ColorRule(new PlacingRule()).canBePlaced(w.pick(0, 3), w, 0, 2));
    }
}
