package it.polimi.ingsw.model.cards.tools;

import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.init;
import static it.polimi.ingsw.model.TestHelper.wrappedIllegalCommand;
import static it.polimi.ingsw.model.TestHelper.wrappedLegalCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LathekinTest {

    /**
     * Testing
     * <ul>
     *     <li>Tool activation</li>
     *     <li>Tool tear down</li>
     *     <li>First die movement</li>
     *     <li>Second die movement</li>
     *     <li>Same die movement</li>
     *     <li>Favor points check</li>
     * </ul>
     */
    @Test
    void test() {
        Game g = init("gen_2p_02");
        List<Player> players = g.getData().getPlayers();
        wrappedLegalCommand(g, players.get(0), "pick 1");
        wrappedLegalCommand(g, players.get(0), "tool 4");
        wrappedIllegalCommand(g, players.get(0), "place 3 1");
        wrappedIllegalCommand(g, players.get(0), "move 1 1 4 1");
        wrappedIllegalCommand(g, players.get(0), "move 1 1 3 1");
        wrappedLegalCommand(g, players.get(0), "move 2 1 1 2");
        wrappedIllegalCommand(g, players.get(0), "pass");
        wrappedIllegalCommand(g, players.get(0), "place 2 1");
        wrappedIllegalCommand(g, players.get(0), "move 1 2 2 1");
        wrappedLegalCommand(g, players.get(0), "move 1 1 2 1");
        wrappedIllegalCommand(g, players.get(0), "pass");
        wrappedLegalCommand(g, players.get(0), "place 3 1");
        assertEquals(2, g.getData().getDiceMoved().size());
        wrappedLegalCommand(g, players.get(0), "pass");
        assertEquals(4, g.getData().getPlayers().get(0).getFavorPoints());
        assertEquals(1, g.getData().getTools().get(2).getFavorPoints());
        assertEquals(0, g.getData().getDiceMoved().size());
    }
}
