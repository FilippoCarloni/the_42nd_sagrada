package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.dice.PlasticDie;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Shade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiceTest {

    /**
     * Checks the correct behavior of getters and setters.
     * Checks the correct behavior of the cloning constructor.
     */
    @Test
    void diceEqualityTest() {
        DiceBag db = new ArrayDiceBag();
        Die a = db.pick();
        Die b = db.pick();
        a.setColor(Color.BLUE);
        b.setColor(Color.BLUE);
        a.setShade(Shade.LIGHT);
        b.setShade(Shade.LIGHT);
        assertThrows(NullPointerException.class, () -> a.setShade(null));
        assertThrows(NullPointerException.class, () -> a.setColor(null));

        assertNotEquals(a, b); // different pointers
        Die c = new PlasticDie(b.encode()); // c clones b
        assertNotEquals(a, c);
        assertEquals(b, c); // c is equal to b, but they have different pointers
        assertEquals(b.hashCode(), c.hashCode()); // c and b share the hashCode
        assertNotEquals(a.hashCode(), b.hashCode());
        assertEquals(a.getColor(), Color.BLUE);
        assertEquals(a.getShade(), Shade.LIGHT);

        assertEquals(a, new PlasticDie(a.encode())); // a equals its clone
        assertEquals(Color.BLUE, new PlasticDie(a.encode()).getColor());
        assertEquals(Shade.LIGHT, new PlasticDie(a.encode()).getShade());
        assertEquals(a.hashCode(), new PlasticDie(a.encode()).hashCode());
    }

    @Test
    void toStringTest() {
        String s = new ArrayDiceBag().pick().toString();
        //System.out.println(s);
    }
}
