package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.publicobjectives.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.publicobjectives.PublicObjectiveDeck;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolDeck;
import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.utility.JSONFactory;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static it.polimi.ingsw.model.TestHelper.PRINT_PUBLIC_OBJECTIVES;
import static it.polimi.ingsw.model.TestHelper.fillMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PublicObjectiveCardTest {

    /**
     * Asserts the correct size of the deck.
     */
    @Test
    void isDeckCorrect() {
        Deck d = new PublicObjectiveDeck();
        assertEquals(d.size(), 10);
        d.draw(d.size());
        assertThrows(NoSuchElementException.class, d::draw);
    }

    /**
     * Evaluates score points on a generic frame and prints the values.
     */
    @Test
    void printCards() {
        Deck frames = new WindowFrameDeck();
        Deck d = new PublicObjectiveDeck();
        WindowFrame w = (WindowFrame) frames.draw();
        fillMap(w);
        if (PRINT_PUBLIC_OBJECTIVES)
            System.out.println(w);
        while (d.size() > 0) {
            PublicObjectiveCard c = (PublicObjectiveCard) d.draw();
            int vp = c.getValuePoints(w);
            assertTrue(vp >= 0);
            assertTrue(vp <= 25); // 5 (Color Column Variety) * #columns = 5 * 5 = 25 (maximum value possible for a single public objective)
            if (PRINT_PUBLIC_OBJECTIVES)
                System.out.println("Value points for " + c.getName() + ": " + c.getValuePoints(w));
        }
    }

    /**
     * Asserts that every card returns 0 score points on an empty window frame.
     */
    @Test
    void emptyMapTest() {
        WindowFrame w = (WindowFrame) new WindowFrameDeck().draw();
        Deck d = new PublicObjectiveDeck();
        while (d.size() > 0)
            assertTrue((((PublicObjectiveCard) d.draw()).getValuePoints(w) == 0));
    }

    /**
     * Tests the correct cloning procedure for public objective cards.
     */
    @Test
    void testJSON() {
        Deck d = new PublicObjectiveDeck();
        WindowFrame w = (WindowFrame) new WindowFrameDeck().draw();
        fillMap(w);
        while (d.size() > 0) {
            PublicObjectiveCard card = (PublicObjectiveCard) d.draw();
            PublicObjectiveCard cardClone = JSONFactory.getPublicObjectiveCard(card.encode());
            assertEquals(card.getName(), cardClone.getName());
            assertEquals(card.getDescription(), cardClone.getDescription());
            assertEquals(card.getID(), cardClone.getID());
            assertEquals(card.encode(), cardClone.encode());
            assertEquals(card.getValuePoints(w), cardClone.getValuePoints(w));
        }
    }
}
