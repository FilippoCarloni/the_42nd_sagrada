package it.polimi.ingsw.model.cards.tools;

import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.init;
import static it.polimi.ingsw.model.TestHelper.wrappedIllegalCommand;
import static it.polimi.ingsw.model.TestHelper.wrappedLegalCommand;

class CopperFoilBurnisherTest {

    /**
     * Testing
     * <ul>
     *     <li>Rule compliant die moving</li>
     *     <li>Tool card tear down</li>
     * </ul>
     */
    @Test
    void test() {
        Game g = init("gen_2p_01");
        List<Player> players = g.getData().getPlayers();
        wrappedLegalCommand(g, players.get(0), "pick 2");
        wrappedLegalCommand(g, players.get(0), "place 1 1");
        wrappedLegalCommand(g, players.get(0), "tool 3");
        wrappedIllegalCommand(g, players.get(0), "move 1 1 4 3");
        wrappedIllegalCommand(g, players.get(0), "move 1 1 2 2");
        wrappedIllegalCommand(g, players.get(0), "pass");
        wrappedIllegalCommand(g, players.get(0), "pick 1");
        wrappedLegalCommand(g, players.get(0), "move 1 1 4 1");
        g.undoCommand();
        wrappedLegalCommand(g, players.get(0), "move 1 1 3 5");
        g.undoCommand();
        wrappedLegalCommand(g, players.get(0), "move 1 1 4 2");
        wrappedIllegalCommand(g, players.get(0), "move 4 2 1 1");
        wrappedIllegalCommand(g, players.get(0), "tool 3");
        wrappedLegalCommand(g, players.get(0), "pass");
    }
}
