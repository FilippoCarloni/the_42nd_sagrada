package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.dice.ClothDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.utility.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiceBagTest {



    @Test
    void areDiceInTheBagCorrect() {
        DiceBag db = new ClothDiceBag();
        ArrayList<Die> dice = new ArrayList<>(db.pick(90));
        System.out.print("Dice bag test: ");
        for (Die d : dice) System.out.print(d);

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
}
