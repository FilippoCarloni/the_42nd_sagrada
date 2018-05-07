package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.dice.ClothDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.roundtrack.PaperRoundTrack;
import it.polimi.ingsw.model.gameboard.roundtrack.RoundTrack;
import it.polimi.ingsw.model.gameboard.utility.Parameters;
import it.polimi.ingsw.model.gameboard.utility.Shade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RoundTrackTest {

    @Test
    void roundTrackTest1() {
        RoundTrack rt = new PaperRoundTrack();
        DiceBag db = new ClothDiceBag();

        assertEquals(rt.getVisibleDice().size(), 0);

        rt.put(db.pick());
        rt.put(db.pick(3));

        assertEquals(rt.getVisibleDice().size(), 2);

        int size = 0;
        for (Die d : rt) size++;

        assertEquals(size, 4);

        System.out.println(rt);
    }

    @Test
    void roundTrackTest2() {
        RoundTrack rt = new PaperRoundTrack();
        DiceBag db = new ClothDiceBag();

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

        assertEquals(rt.getVisibleDice().get(0), a);
        rt.swap(db.pick(), a);
        assertNotEquals(rt.getVisibleDice().get(0), a);

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
}
