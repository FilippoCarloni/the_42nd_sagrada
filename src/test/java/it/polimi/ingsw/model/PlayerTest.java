package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.cards.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        assertEquals(null, p.getWindowFrame());
        assertEquals(null, p.getPrivateObjective());
        assertThrows(NullPointerException.class, () -> p.setPrivateObjective(null));
        assertThrows(NullPointerException.class, () -> p.setWindowFrame(null));

        p.setPrivateObjective(playerObjective);
        p.setWindowFrame(playerFrame);

        assertEquals(playerObjective.getID(), p.getPrivateObjective().getID());
        assertEquals(playerFrame.getName(), p.getWindowFrame().getName());
        assertEquals(username, p.getUsername());
    }

    /**
     * Checks the correct behavior of the cloning constructor.
     */
    @Test
    void testJSON() {
        Player p = new ConcretePlayer("foo");
        Player pClone = new ConcretePlayer(p.encode());

        assertEquals(p.getUsername(), pClone.getUsername());
        assertEquals(p.getWindowFrame(), pClone.getWindowFrame());
        assertEquals(p.getFavorPoints(), pClone.getFavorPoints());
        assertEquals(p.getPrivateObjective(), pClone.getPrivateObjective());

        p.setWindowFrame((WindowFrame) new WindowFrameDeck().draw());
        p.setPrivateObjective((PrivateObjectiveCard) new PrivateObjectiveDeck().draw());
        p.setFavorPoints(3);
        assertThrows(IllegalArgumentException.class, () -> p.setFavorPoints(-1));
        pClone = new ConcretePlayer(p.encode());

        assertEquals(p.getUsername(), pClone.getUsername());
        assertEquals(p.getWindowFrame().getName(), pClone.getWindowFrame().getName());
        assertEquals(p.getFavorPoints(), pClone.getFavorPoints());
        assertEquals(p.getPrivateObjective().getColor(), pClone.getPrivateObjective().getColor());
    }

    @Test
    void toStringTest() {
        Player foo = new ConcretePlayer("foo");
        foo.setPrivateObjective((PrivateObjectiveCard) new PrivateObjectiveDeck().draw());
        foo.setWindowFrame((WindowFrame) new WindowFrameDeck().draw());
        String s = foo.toString();
        //System.out.println(s);
    }
}
