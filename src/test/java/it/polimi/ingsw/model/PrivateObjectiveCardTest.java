package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PrivateObjectiveCardTest {

    @Test
    void isDeckCorrect() {
        Deck d = new PrivateObjectiveDeck();
        assertEquals(d.size(), 5);
        while (d.size() > 0)
            d.draw();
        assertThrows(NoSuchElementException.class, d::draw);
    }

    @Test
    void areCalculationsCorrect() {
        DiceBag db = new ArrayDiceBag();
        PrivateObjectiveCard c = (PrivateObjectiveCard) new PrivateObjectiveDeck().draw();
        WindowFrame w = (WindowFrame) new WindowFrameDeck().draw();
        for (int i = 0; i < Parameters.MAX_ROWS; i++)
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++)
                w.put(db.pick(), i, j);
        int res = 0;
        for (Die d : w.getDice())
            if (d.getColor().equals(c.getColor()))
                res += d.getShade().getValue();
        assertEquals(c.getValuePoints(w), res);
    }

    @Test
    void testJSON() {
        Deck d = new PrivateObjectiveDeck();
        DiceBag db = new ArrayDiceBag();
        WindowFrame w = (WindowFrame) new WindowFrameDeck().draw();
        for (int i = 0; i < Parameters.MAX_ROWS; i++)
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++)
                w.put(db.pick(), i, j);
        while (d.size() > 0) {
            PrivateObjectiveCard card = (PrivateObjectiveCard) d.draw();
            PrivateObjectiveCard cardClone = PrivateObjectiveCard.getCardFromJSON(card.encode());
            assertEquals(card.getColor(), cardClone.getColor());
            assertEquals(card.getName(), cardClone.getName());
            assertEquals(card.getDescription(), cardClone.getDescription());
            assertEquals(card.getID(), cardClone.getID());
            assertEquals(card.encode(), cardClone.encode());
            assertEquals(card.getValuePoints(w), cardClone.getValuePoints(w));
        }
    }
}
