package it.polimi.ingsw.model.windowframes;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.windowframes.*;
import it.polimi.ingsw.model.utility.Parameters;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class WindowFrameTest {

    /**
     * Checks if placing and moving operations behave correctly.
     */
    @Test
    void placeAndMoveTest() {
        Deck d = new WindowFrameDeck();
        DiceBag db = new ArrayDiceBag();
        WindowFrame w = (WindowFrame) d.draw();

        assertEquals(null, w.getDie(0, 0));
        assertEquals(null, w.getDie(-1, 0));
        assertEquals(null, w.getColorConstraint(8, 0));
        assertEquals(null, w.getShadeConstraint(0, 9));
        assertThrows(NullPointerException.class, () -> w.put(null, 0, 0));

        Die die = db.pick();
        w.put(die, 0, 0);
        assertEquals(false, w.isEmpty(0, 0));
        assertEquals(die, w.getDie(0, 0));
        assertNotEquals(db.pick(), w.getDie(0, 0));
        assertThrows(IllegalArgumentException.class, () -> w.put(db.pick(), 0, 0));
        w.put(db.pick(), 0, 1);

        assertThrows(IllegalArgumentException.class, () -> w.move(0, 0, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> w.move(1, 1, 0, 0));

        w.move(0, 0, 1, 1);
        assertEquals(die, w.getDie(1, 1));
        assertEquals(2, w.getDice().size());

        assertEquals(die, w.pick(1, 1));
        assertEquals(1, w.getDice().size());
        assertEquals(null, w.pick(1, 1));
        assertTrue(w.getName().length() > 0);
        assertTrue(w.getDifficulty() >= 3);
        assertTrue(w.getDifficulty() <= 6);
    }

    /**
     * Checks if the deck contains the 24 classic Sagrada cards.
     */
    @Test
    void drawTest() {
        Deck d = new WindowFrameDeck();
        assertEquals(d.size(), 24);
        d.draw(d.size());
        assertEquals(d.size(), 0);
        assertThrows(NoSuchElementException.class, d::draw);
        assertThrows(IllegalArgumentException.class, () -> d.draw(-1));
    }

    /**
     * Checks the correct behavior of the simple Coordinate class.
     */
    @Test
    void coordinateTest() {
        assertThrows(IllegalArgumentException.class, () -> new Coordinate(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate(0, 5));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate(4, 0));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate(0, -1));
        assertEquals(new Coordinate(2, 3), new Coordinate(2, 3));
        assertNotEquals(new Coordinate(2, 4), new Coordinate(0, 0));
    }

    /**
     * Checks the correct behavior of the cloning constructor.
     */
    @Test
    void testJSON() {
        DiceBag db = new ArrayDiceBag();
        Deck d = new WindowFrameDeck();
        WindowFrame w = (WindowFrame) d.draw();
        w.put(db.pick(), 2, 3);
        w.put(db.pick(), 1, 1);
        w.put(db.pick(), 0, 0);
        w.put(db.pick(), 3, 4);
        w.put(db.pick(), 3, 3);

        WindowFrame clonedW = new PaperWindowFrame(w.encode());

        assertEquals(w.getName(), clonedW.getName());
        assertEquals(w.getDifficulty(), clonedW.getDifficulty());

        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                assertEquals(w.getDie(i, j), clonedW.getDie(i, j));
                assertEquals(w.getColorConstraint(i, j), clonedW.getColorConstraint(i, j));
                assertEquals(w.getShadeConstraint(i, j), clonedW.getShadeConstraint(i, j));
            }
        }
    }

    @Test
    void toStringTest() {
        WindowFrame w = (WindowFrame) new WindowFrameDeck().draw();
        w.put(new ArrayDiceBag().pick(), 0, 0);
        String s = w.toString();
        //System.out.println(s);
    }
}
