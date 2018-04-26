package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.dice.ClothDiceBag;
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
    void dieTest() {
        DiceBag db = new ClothDiceBag();
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
        assertEquals(a.getColor(), Color.BLUE);
        assertEquals(a.getShade(), Shade.LIGHT);
        assertEquals(a.hashCode(), 0);

        System.out.println("Die test: " + b + " = " + c);
    }
}
