package it.polimi.ingsw.model.dice;

import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.JSONFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiceBagTest {

    /**
     * Checks if the dice bag contains the expected dice when created.
     */
    @Test
    void areDiceInTheBagCorrect() {
        DiceBag db = new ArrayDiceBag();
        ArrayList<Die> dice = new ArrayList<>(db.pick(90));
        assertEquals(18, dice.stream().
                map(Die::getColor).filter(c -> c == Color.RED).count());
        assertEquals(18, dice.stream().
                map(Die::getColor).filter(c -> c == Color.YELLOW).count());
        assertEquals(18, dice.stream().
                map(Die::getColor).filter(c -> c == Color.BLUE).count());
        assertEquals(18, dice.stream().
                map(Die::getColor).filter(c -> c == Color.GREEN).count());
        assertEquals(18, dice.stream().
                map(Die::getColor).filter(c -> c == Color.PURPLE).count());
        assertThrows(NoSuchElementException.class, db::pick);
    }

    /**
     * Checks if the insert method inserts the die correctly in the bag;
     * the die must be exactly the same that was previously picked.
     */
    @Test
    void insertTest() {
        DiceBag db = new ArrayDiceBag();
        assertThrows(IllegalArgumentException.class, () -> db.pick(0));
        assertThrows(NoSuchElementException.class, () -> db.pick(91));
        assertThrows(NullPointerException.class, () -> db.insert(null));
        Die d1 = db.pick();
        db.pick(89);
        assertThrows(NoSuchElementException.class, db::pick);
        db.insert(d1);
        assertThrows(IllegalArgumentException.class, () -> db.insert(d1));
        Die d2 = db.pick();
        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
        assertThrows(NoSuchElementException.class, db::pick);
    }

    /**
     * Checks the correct behavior of the cloning constructor.
     */
    @Test
    void testJSON() {
        DiceBag db = new ArrayDiceBag();
        db.pick();
        db.pick();
        db.insert(db.pick());

        DiceBag clonedDb = JSONFactory.getDiceBag(db.encode());

        List<Die> dbDice = db.pick(88);
        List<Die> clonedDbDice = clonedDb.pick(88);

        assertEquals(dbDice.size(), clonedDbDice.size());
        assertEquals(88, dbDice.size());

        for (Die d : dbDice)
            assertTrue(clonedDbDice.contains(d));

        assertThrows(NoSuchElementException.class, db::pick);
        assertThrows(NoSuchElementException.class, clonedDb::pick);
    }
}
