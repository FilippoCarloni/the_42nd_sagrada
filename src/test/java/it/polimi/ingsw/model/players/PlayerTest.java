package it.polimi.ingsw.model.players;

import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import it.polimi.ingsw.model.utility.JSONFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    /**
     * Checks the correct behavior of getters and setters.
     */
    @Test
    void getterAndSetterTest() {
        WindowFrame playerFrame = (WindowFrame) new WindowFrameDeck().draw();
        PrivateObjectiveCard playerObjective = (PrivateObjectiveCard) new PrivateObjectiveDeck().draw();
        String username = "foo";
        Player p = new ConcretePlayer(username);
        // window frame and private objective aren't already set
        assertNull(p.getWindowFrame());
        assertNull(p.getPrivateObjective());
        assertThrows(NullPointerException.class, () -> p.setPrivateObjective(null));
        assertThrows(NullPointerException.class, () -> p.setWindowFrame(null));
        // setting private objective and window frame
        p.setPrivateObjective(playerObjective);
        p.setWindowFrame(playerFrame);
        // asserting private objective and window frame loading
        assertEquals(playerObjective.getID(), p.getPrivateObjective().getID());
        assertEquals(playerFrame.getName(), p.getWindowFrame().getName());
        assertEquals(username, p.getUsername());
        // checking favor points
        assertEquals(p.getWindowFrame().getDifficulty(), p.getFavorPoints());
        assertTrue(2 < p.getFavorPoints());
        p.setFavorPoints(2);
        assertEquals(2, p.getFavorPoints());
    }

    /**
     * Checks the correct behavior of the cloning constructor.
     */
    @Test
    void testJSON() {
        Player p = new ConcretePlayer("foo");
        Player pClone = JSONFactory.getPlayer(p.encode());
        assertEquals(p.getUsername(), pClone.getUsername());
        assertEquals(p.getWindowFrame(), pClone.getWindowFrame());
        assertEquals(p.getFavorPoints(), pClone.getFavorPoints());
        assertEquals(p.getPrivateObjective(), pClone.getPrivateObjective());
        p.setWindowFrame((WindowFrame) new WindowFrameDeck().draw());
        p.setPrivateObjective((PrivateObjectiveCard) new PrivateObjectiveDeck().draw());
        p.setFavorPoints(3);
        assertThrows(IllegalArgumentException.class, () -> p.setFavorPoints(-1));
        pClone = JSONFactory.getPlayer(p.encode());
        assertEquals(p.getUsername(), pClone.getUsername());
        assertEquals(p.getWindowFrame().getName(), pClone.getWindowFrame().getName());
        assertEquals(p.getFavorPoints(), pClone.getFavorPoints());
        assertEquals(p.getPrivateObjective().getColor(), pClone.getPrivateObjective().getColor());
    }
}
