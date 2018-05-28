package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.Shade;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ToolTest {

    /**
     * Checks if the player can use a tool card during his turn,
     * but only one and at the right moment.
     */
    @Test
    void test() {
        Game g = init("gen_2p_01");
        List<Player> players = g.getData().getPlayers();
        wrappedIllegalCommand(g, players.get(0), "tool 3");
        wrappedLegalCommand(g, players.get(0), "pick 1");
        Shade s = g.getData().getPickedDie().getShade();
        wrappedLegalCommand(g, players.get(0), "tool 3");
        assertTrue(!s.equals(g.getData().getPickedDie().getShade()));
        wrappedIllegalCommand(g, players.get(0), "tool 3");
        wrappedLegalCommand(g, players.get(0), "place 1 1");
        wrappedIllegalCommand(g, players.get(0), "tool 3");
        wrappedLegalCommand(g, players.get(0), "pass");
    }
}
