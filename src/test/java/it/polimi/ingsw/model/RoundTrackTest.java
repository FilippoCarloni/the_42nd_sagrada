package it.polimi.ingsw.model;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RoundTrackTest {

    @Test
    void roundTrackTest1() {
        RoundTrack rt = new PaperRoundTrack();
        DiceBag db = new ArrayDiceBag();

        assertEquals(rt.getVisibleDice().size(), 0);

        rt.put(db.pick());
        rt.put(db.pick(3));

        assertEquals(rt.getVisibleDice().size(), 2);

        int size = 0;
        for (Die d : rt) size++;

        assertEquals(size, 4);
    }

    @Test
    void roundTrackTest2() {
        RoundTrack rt = new PaperRoundTrack();
        DiceBag db = new ArrayDiceBag();

        Die a = db.pick();
        a.setShade(Shade.findByValue(1));
        Die b = db.pick();
        b.setShade(Shade.findByValue(2));
        Die c = db.pick();
        c.setShade(Shade.findByValue(6));
        Die d = db.pick();
        d.setShade(Shade.findByValue(5));

        rt.put(a);
        rt.put(b);
        rt.put(c);
        rt.put(d);

        assertEquals(rt.getTotalScore(), 14);
        assertEquals(rt.getCurrentRoundNumber(), 5);

        Die swapped = db.pick();
        assertEquals(rt.getVisibleDice().get(0), a);
        rt.swap(swapped, a);
        assertNotEquals(rt.getVisibleDice().get(0), a);
        assertEquals(rt.getVisibleDice().get(0), swapped);

        rt.put(db.pick());
        rt.put(db.pick());
        rt.put(db.pick());
        rt.put(db.pick());
        rt.put(db.pick());
        assertEquals(rt.isGameFinished(), false);
        rt.put(db.pick());
        assertEquals(rt.isGameFinished(), true);
        rt.put(db.pick());
        rt.put(db.pick());

        assertEquals(rt.getCurrentRoundNumber(), Parameters.TOTAL_NUMBER_OF_ROUNDS);
        assertEquals(rt.getTotalNumberOfRounds(), Parameters.TOTAL_NUMBER_OF_ROUNDS);
    }

    @Test
    void testJSON() {
        RoundTrack rt = new PaperRoundTrack();
        DiceBag db = new ArrayDiceBag();

        rt.put(db.pick());
        rt.put(db.pick(3));
        rt.put(db.pick());
        rt.put(db.pick(2));

        RoundTrack clonedRt = new PaperRoundTrack(rt.encode());

        assertEquals(rt.getCurrentRoundNumber(), clonedRt.getCurrentRoundNumber());

        List<Die> rtDice = new ArrayList<>();
        for (Die d : rt) rtDice.add(d);
        List<Die> clonedRtDice = new ArrayList<>();
        for (Die d : clonedRt) clonedRtDice.add(d);
        for (int i = 0; i < rtDice.size(); i++)
            assertEquals(rtDice.get(i), clonedRtDice.get(i));

        List<Die> rtDiceVisible = new ArrayList<>(rt.getVisibleDice());
        List<Die> clonedRtDiceVisible = new ArrayList<>(clonedRt.getVisibleDice());
        for (int i = 0; i < rtDiceVisible.size(); i++)
            assertEquals(rtDiceVisible.get(i), clonedRtDiceVisible.get(i));
    }
}
