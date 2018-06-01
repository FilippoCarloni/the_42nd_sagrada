package it.polimi.ingsw.model.cards.tools;

import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.init;
import static it.polimi.ingsw.model.TestHelper.wrappedIllegalCommand;
import static it.polimi.ingsw.model.TestHelper.wrappedLegalCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GrozingPliersTest {

    /**
     * Tests correct behavior of increase and decrease commands.
     * Tests the situations in which a tool card can be activated (enough favor points, tool card already activated)
     */
    @Test
    void test() {
        Game g = init("gen_2p_01");
        List<Player> players = g.getData().getPlayers();
        wrappedIllegalCommand(g, players.get(0), "tool 1");
        assertEquals(2, g.getData().getTools().get(0).getID());
        assertEquals(0, g.getData().getActiveToolID());
        assertEquals(0, g.getData().getPassiveToolID());
        wrappedLegalCommand(g, players.get(0), "pick 1");
        wrappedLegalCommand(g, players.get(0), "tool 1");
        assertEquals(1, g.getData().getActiveToolID());
        assertEquals(1, g.getData().getPassiveToolID());
        wrappedIllegalCommand(g, players.get(0), "place 1 1");
        wrappedIllegalCommand(g, players.get(0), "decrease");
        wrappedIllegalCommand(g, players.get(0), "tool 1");
        assertEquals(1, g.getData().getPickedDie().getShade().getValue());
        wrappedLegalCommand(g, players.get(0), "increase");
        assertEquals(2, g.getData().getPickedDie().getShade().getValue());
        wrappedLegalCommand(g, players.get(0), "place 1 1");
        wrappedIllegalCommand(g, players.get(0), "tool 1");
        wrappedLegalCommand(g, players.get(0), "pass");
        wrappedLegalCommand(g, players.get(1), "pick 1");
        wrappedLegalCommand(g, players.get(1), "tool 1");
        wrappedLegalCommand(g, players.get(1), "decrease");
        assertEquals(4, g.getData().getPlayers().get(0).getFavorPoints());
        assertEquals(3, g.getData().getPlayers().get(1).getFavorPoints());
        assertEquals(3, g.getData().getTools().get(2).getFavorPoints());
        wrappedLegalCommand(g, players.get(1), "place 1 1");
        wrappedLegalCommand(g, players.get(1), "pass");
        wrappedLegalCommand(g, players.get(1), "pick 1");
        wrappedLegalCommand(g, players.get(1), "tool 1");
        assertEquals(5, g.getData().getPickedDie().getShade().getValue());
        wrappedLegalCommand(g, players.get(1), "decrease");
        assertEquals(4, g.getData().getPickedDie().getShade().getValue());
        wrappedLegalCommand(g, players.get(1), "place 2 1");
        wrappedLegalCommand(g, players.get(1), "pass");
        wrappedLegalCommand(g, players.get(0), "pass");
        wrappedIllegalCommand(g, players.get(1), "tool 1");
        wrappedLegalCommand(g, players.get(1), "pick 1");
        wrappedIllegalCommand(g, players.get(1), "tool 1");
        assertEquals(4, g.getData().getPlayers().get(0).getFavorPoints());
        assertEquals(1, g.getData().getPlayers().get(1).getFavorPoints());
        assertEquals(5, g.getData().getTools().get(2).getFavorPoints());
    }
}
