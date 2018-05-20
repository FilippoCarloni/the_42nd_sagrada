package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.dice.PlasticDie;
import it.polimi.ingsw.model.gameboard.utility.Color;
import it.polimi.ingsw.model.gameboard.utility.Shade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DiceTest {

    @Test
    void diceEqualityTest() {
        DiceBag db = new ArrayDiceBag();
        Die a = db.pick();
        Die b = db.pick();
        a.setColor(Color.BLUE);
        b.setColor(Color.BLUE);
        a.setShade(Shade.LIGHT);
        b.setShade(Shade.LIGHT);

        assertNotEquals(a, b);

        Die c = new PlasticDie((PlasticDie) b);

        assertNotEquals(a, c);
        assertEquals(b, c);
        assertEquals(b.hashCode(), c.hashCode());
        assertNotEquals(a.hashCode(), b.hashCode());
        assertEquals(a.getColor(), Color.BLUE);
        assertEquals(a.getShade(), Shade.LIGHT);
    }
}
