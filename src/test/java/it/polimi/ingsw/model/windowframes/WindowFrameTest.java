package it.polimi.ingsw.model.windowframes;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.windowframes.*;
import it.polimi.ingsw.model.utility.JSONFactory;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static it.polimi.ingsw.model.TestHelper.areWindowFramesEqual;
import static org.junit.jupiter.api.Assertions.*;

class WindowFrameTest {

    private DiceBag db;

    private WindowFrame loadWindowFrame() {
        db = new ArrayDiceBag();
        return (WindowFrame) new WindowFrameDeck().draw();
    }

    /**
     * Checks the correct behavior when expected a null value returned.
     */
    @Test
    void nullTest() {
        WindowFrame w = loadWindowFrame();
        assertNull(w.getDie(0, 0));
        assertNull(w.getDie(-1, 0));
        assertNull(w.getColorConstraint(8, 0));
        assertNull(w.getShadeConstraint(0, 9));
        assertTrue(w.isEmpty(0, 0));
        assertThrows(NullPointerException.class, () -> w.put(null, 0, 0));
    }

    /**
     * Checks if placing operations behave correctly.
     * Checks if moving operations behave correctly.
     */
    @Test
    void placingTest() {
        // PLACING
        WindowFrame w = loadWindowFrame();
        Die die = db.pick();
        w.put(die, 0, 0);
        assertFalse(w.isEmpty(0, 0));
        assertEquals(die, w.getDie(0, 0));
        assertNotEquals(db.pick(), w.getDie(0, 0));
        assertThrows(IllegalArgumentException.class, () -> w.put(db.pick(), 0, 0));
        w.put(db.pick(), 0, 1);

        // MOVING
        assertThrows(IllegalArgumentException.class, () -> w.move(0, 0, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> w.move(1, 1, 0, 0));
        w.move(0, 0, 1, 1);
        assertEquals(die, w.getDie(1, 1));
        assertEquals(2, w.getDice().size());
        assertEquals(die, w.pick(1, 1)); // the die is removed from the frame
        assertEquals(1, w.getDice().size());
        assertNull(w.pick(1, 1));
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
        WindowFrame w = loadWindowFrame();
        w.put(db.pick(), 2, 3);
        w.put(db.pick(), 1, 1);
        w.put(db.pick(), 0, 0);
        w.put(db.pick(), 3, 4);
        w.put(db.pick(), 3, 3);
        WindowFrame clonedW = JSONFactory.getWindowFrame(w.encode());
        areWindowFramesEqual(w, clonedW);
    }
}
