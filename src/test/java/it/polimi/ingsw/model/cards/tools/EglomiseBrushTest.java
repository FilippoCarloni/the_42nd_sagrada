package it.polimi.ingsw.model.cards.tools;

import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.init;
import static it.polimi.ingsw.model.TestHelper.wrappedIllegalCommand;
import static it.polimi.ingsw.model.TestHelper.wrappedLegalCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EglomiseBrushTest {

    /**
     * Testing
     * <ul>
     *     <li>Favor points check</li>
     *     <li>Tool activation</li>
     *     <li>Tool tear down</li>
     *     <li>Correct moving according to the tool rules</li>
     * </ul>
     */
    @Test
    void test() {
        Game g = init("gen_2p_01");
        List<Player> players = g.getData().getPlayers();
        wrappedLegalCommand(g, players.get(0), "tool 2");
        assertEquals(4, g.getCurrentPlayer().getFavorPoints());
        assertEquals(1, g.getData().getTools().get(0).getFavorPoints());
        g.undoCommand();
        wrappedLegalCommand(g, players.get(0), "pick 2");
        wrappedLegalCommand(g, players.get(0), "place 1 1");
        wrappedIllegalCommand(g, players.get(0), "move 1 1 4 3");
        wrappedLegalCommand(g, players.get(0), "tool 2");
        wrappedIllegalCommand(g, players.get(0), "move 1 1 1 3");
        wrappedIllegalCommand(g, players.get(0), "move 4 4 1 3");
        wrappedIllegalCommand(g, players.get(0), "move 1 1 2 3");
        assertFalse(g.getCurrentPlayer().getWindowFrame().isEmpty(0, 0));
        assertTrue(g.getCurrentPlayer().getWindowFrame().isEmpty(3, 2));
        wrappedIllegalCommand(g, players.get(0), "move 1 1 1 1");
        wrappedLegalCommand(g, players.get(0), "move 1 1 4 3");
        assertTrue(g.getCurrentPlayer().getWindowFrame().isEmpty(0, 0));
        assertFalse(g.getCurrentPlayer().getWindowFrame().isEmpty(3, 2));
        wrappedIllegalCommand(g, players.get(0), "move 4 3 1 1");
        wrappedLegalCommand(g, players.get(0), "pass");
    }
}
