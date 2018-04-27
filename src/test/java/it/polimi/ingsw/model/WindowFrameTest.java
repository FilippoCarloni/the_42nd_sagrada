package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.windowframes.*;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WindowFrameTest {

    // TODO: add test for functionality

    @Test
    void printPatternTest() {
        Deck d = new WindowFrameDeck();
        while (d.size() > 0)
            System.out.println(d.draw());
    }

    @Test
    void exceptionTest() {
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
