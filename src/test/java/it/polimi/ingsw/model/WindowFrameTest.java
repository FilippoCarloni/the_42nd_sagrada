package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.windowframes.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class WindowFrameTest {

    @Test
    void placeAndMoveTest() {

        Deck d = new WindowFrameDeck();
        DiceBag db = new ArrayDiceBag();
        WindowFrame w = (WindowFrame) d.draw();

        assertEquals(PaperWindowFrame.class, w.getClass());

        assertEquals(null, w.getDie(0, 0));
        assertThrows(NullPointerException.class, () -> w.put(null, 0, 0));

        Die die = db.pick();
        w.put(die, 0, 0);
        assertEquals(false, w.isEmpty(0, 0));
        assertEquals(die, w.getDie(0, 0));
        assertNotEquals(db.pick(), w.getDie(0, 0));
        assertThrows(IllegalArgumentException.class, () -> w.put(db.pick(), 0, 0));
        w.put(db.pick(), 0, 1);

        assertThrows(NullPointerException.class, () -> w.move(null, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> w.move(die, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> w.move(0, 0, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> w.move(db.pick(), 2, 2));
        assertThrows(IllegalArgumentException.class, () -> w.move(1, 1, 0, 0));

        w.move(0, 0, 1, 1);
        assertEquals(die, w.getDie(1, 1));
        w.move(die, 1, 2);
        assertNotEquals(null, w.getName());
        assertNotEquals(null, w.getShadeConstraints());
        assertNotEquals(null, w.getColorConstraints());
        assertEquals(HashMap.class, w.getColorConstraints().getClass());
        assertEquals(HashMap.class, w.getShadeConstraints().getClass());
        assertTrue(w.getDifficulty() > 0);
    }

    @Test
    void drawTest() {
        Deck d = new WindowFrameDeck();
        assertEquals(d.size(), 24);
        d.draw(d.size());
        assertEquals(d.size(), 0);
        assertThrows(NoSuchElementException.class, d::draw);
        assertThrows(IllegalArgumentException.class, () -> d.draw(-1));
    }

    @Test
    void coordinateTest() {
        assertThrows(IllegalArgumentException.class, () -> new Coordinate(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate(0, 5));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate(4, 0));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate(0, -1));
        assertEquals(new Coordinate(0, 1).getRow(), 0);
        assertEquals(new Coordinate(0, 1).getColumn(), 1);
        assertEquals(new Coordinate(2, 3), new Coordinate(2, 3));
        assertNotEquals(new Coordinate(2, 4), new Coordinate(0, 0));
    }
}
