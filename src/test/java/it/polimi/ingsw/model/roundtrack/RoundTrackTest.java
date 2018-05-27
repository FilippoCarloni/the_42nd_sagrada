package it.polimi.ingsw.model.roundtrack;

import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.roundtrack.PaperRoundTrack;
import it.polimi.ingsw.model.gameboard.roundtrack.RoundTrack;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.utility.Shade;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTrackTest {

    /**
     * Checks if the size of visible dice and total dice is correct.
     * Checks if the current round number advances correctly.
     * Checks if tells correctly when the game should finish.
     */
    @Test
    void sizeAndBoundaryTest() {
        RoundTrack rt = new PaperRoundTrack();
        DiceBag db = new ArrayDiceBag();

        assertEquals(10, rt.getTotalNumberOfRounds());
        assertEquals(0, rt.getVisibleDice().size());
        assertEquals(0, rt.getDice().size());
        assertEquals(1, rt.getCurrentRoundNumber());

        rt.put(db.pick(1));
        assertEquals(2, rt.getCurrentRoundNumber());
        rt.put(db.pick(3));
        assertEquals(3, rt.getCurrentRoundNumber());

        assertEquals(2, rt.getVisibleDice().size());
        assertEquals(4, rt.getDice().size());

        for (int i = 0; i < 7; i++)
            rt.put(db.pick(1));
        assertFalse(rt.isGameFinished());
        rt.put(db.pick(1));
        assertTrue(rt.isGameFinished());
        assertThrows(IllegalArgumentException.class, () -> rt.put(db.pick(1)));
    }

    /**
     * Checks the correct behavior of the swap method.
     */
    @Test
    void roundTrackTest2() {
        RoundTrack rt = new PaperRoundTrack();
        DiceBag db = new ArrayDiceBag();
        List<Die> dice = db.pick(5);
        rt.put(dice);
        assertTrue(dice.containsAll(rt.getDice()));

        Die swapped = db.pick();
        Die roundTrackDie = rt.getDice().get(0);

        assertThrows(IllegalArgumentException.class, () -> rt.swap(rt.getDice().get(1), roundTrackDie));
        assertThrows(IllegalArgumentException.class, () -> rt.swap(db.pick(), db.pick()));

        rt.swap(swapped, roundTrackDie);
        assertFalse(rt.getDice().contains(roundTrackDie));
        assertEquals(rt.getDice().get(0), swapped);
    }

    /**
     * Checks the correct behavior of the cloning constructor.
     */
    @Test
    void testJSON() {
        RoundTrack rt = new PaperRoundTrack();
        DiceBag db = new ArrayDiceBag();

        rt.put(db.pick(1));
        rt.put(db.pick(3));
        rt.put(db.pick(1));
        rt.put(db.pick(2));

        RoundTrack clonedRt = new PaperRoundTrack(rt.encode());

        assertEquals(rt.getCurrentRoundNumber(), clonedRt.getCurrentRoundNumber());

        List<Die> rtDice = new ArrayList<>(rt.getDice());
        List<Die> clonedRtDice = new ArrayList<>(clonedRt.getDice());
        for (int i = 0; i < rtDice.size(); i++)
            assertEquals(rtDice.get(i), clonedRtDice.get(i));

        List<Die> rtDiceVisible = new ArrayList<>(rt.getVisibleDice());
        List<Die> clonedRtDiceVisible = new ArrayList<>(clonedRt.getVisibleDice());
        for (int i = 0; i < rtDiceVisible.size(); i++)
            assertEquals(rtDiceVisible.get(i), clonedRtDiceVisible.get(i));
    }

    @Test
    void toStringTest() {
        String s = new PaperRoundTrack().toString();
        //System.out.println(s);
    }
}
