package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.JSONFactory;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static it.polimi.ingsw.model.TestHelper.fillMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PrivateObjectiveCardTest {

    /**
     * Checks the correct size of the deck.
     */
    @Test
    void isDeckCorrect() {
        Deck d = new PrivateObjectiveDeck();
        assertEquals(d.size(), 5);
        while (d.size() > 0)
            d.draw();
        assertThrows(NoSuchElementException.class, d::draw);
    }

    /**
     * Checks the correct evaluation of final score on a randomly filled frame.
     */
    @Test
    void areCalculationsCorrect() {
        PrivateObjectiveCard c = (PrivateObjectiveCard) new PrivateObjectiveDeck().draw();
        WindowFrame w = (WindowFrame) new WindowFrameDeck().draw();
        fillMap(w);
        int res = 0;
        for (Die d : w.getDice())
            if (d.getColor().equals(c.getColor()))
                res += d.getShade().getValue();
        assertEquals(c.getValuePoints(w), res);
    }

    /**
     * Asserts the correct cloning procedure of a private objective card.
     */
    @Test
    void testJSON() {
        Deck d = new PrivateObjectiveDeck();
        WindowFrame w = (WindowFrame) new WindowFrameDeck().draw();
        fillMap(w);
        while (d.size() > 0) {
            PrivateObjectiveCard card = (PrivateObjectiveCard) d.draw();
            PrivateObjectiveCard cardClone = JSONFactory.getPrivateObjectiveCard(card.encode());
            assertEquals(card.getColor(), cardClone.getColor());
            assertEquals(card.getName(), cardClone.getName());
            assertEquals(card.getDescription(), cardClone.getDescription());
            assertEquals(card.getID(), cardClone.getID());
            assertEquals(card.encode(), cardClone.encode());
            assertEquals(card.getValuePoints(w), cardClone.getValuePoints(w));
        }
    }
}
