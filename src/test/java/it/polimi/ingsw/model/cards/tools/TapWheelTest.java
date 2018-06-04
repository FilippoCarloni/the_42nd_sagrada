package it.polimi.ingsw.model.cards.tools;

import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.init;
import static it.polimi.ingsw.model.TestHelper.wrappedIllegalCommand;
import static it.polimi.ingsw.model.TestHelper.wrappedLegalCommand;
import static it.polimi.ingsw.model.utility.Parameters.USE_COMPLETE_RULES;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TapWheelTest {

    @Test
    void test1() {
        if (USE_COMPLETE_RULES) {
            Game g = init("gen_2p_04");
            List<Player> players = g.getData().getPlayers();
            wrappedLegalCommand(g, players.get(0), "tool 12");
            g.undoCommand();
            wrappedLegalCommand(g, players.get(0), "pick 1");
            wrappedLegalCommand(g, players.get(0), "tool 12");
            wrappedLegalCommand(g, players.get(0), "place 1 2");
            wrappedIllegalCommand(g, players.get(0), "move 1 2 3 1");
            wrappedLegalCommand(g, players.get(0), "pass");
        }
    }

    @Test
    void test2() {
        if (USE_COMPLETE_RULES) {
            Game g = init("gen_2p_04");
            List<Player> players = g.getData().getPlayers();
            wrappedLegalCommand(g, players.get(0), "pick 3");
            wrappedLegalCommand(g, players.get(0), "place 3 1");
            wrappedLegalCommand(g, players.get(0), "tool 12");
            wrappedIllegalCommand(g, players.get(0), "move 3 1 3 2");
            wrappedIllegalCommand(g, players.get(0), "move 4 5 4 4");
            wrappedIllegalCommand(g, players.get(0), "move 1 1 3 1");
            wrappedLegalCommand(g, players.get(0), "move 1 1 4 1");
            wrappedIllegalCommand(g, players.get(0), "move 4 1 1 1");
            wrappedIllegalCommand(g, players.get(0), "move 2 1 1 2");
            wrappedLegalCommand(g, players.get(0), "pass");
        }
    }

    @Test
    void test3() {
        if (USE_COMPLETE_RULES) {
            Game g = init("gen_2p_04");
            List<Player> players = g.getData().getPlayers();
            wrappedLegalCommand(g, players.get(0), "pick 5");
            wrappedLegalCommand(g, players.get(0), "place 1 2");
            wrappedLegalCommand(g, players.get(0), "tool 12");
            wrappedLegalCommand(g, players.get(0), "move 2 1 2 3");
            wrappedIllegalCommand(g, players.get(0), "move 1 1 2 1");
            assertEquals(0, g.getData().getActiveToolID());
            assertEquals(12, g.getData().getPassiveToolID());
            wrappedLegalCommand(g, players.get(0), "move 1 2 2 1");
            wrappedIllegalCommand(g, players.get(0), "move 1 1 2 1");
            assertEquals(0, g.getData().getActiveToolID());
            assertEquals(0, g.getData().getPassiveToolID());
            wrappedLegalCommand(g, players.get(0), "pass");
            System.out.println(g.getData());
        }
    }

    @Test
    void test4() {
        if (USE_COMPLETE_RULES) {
            Game g = init("gen_2p_05");
            List<Player> players = g.getData().getPlayers();
            System.out.println(g.getData());
            wrappedLegalCommand(g, players.get(0), "tool 12");
            wrappedLegalCommand(g, players.get(0), "pick 1");
            wrappedIllegalCommand(g, players.get(0), "tool 12");
            g.undoCommand();
            g.undoCommand();
            wrappedLegalCommand(g, players.get(0), "pick 1");
            wrappedLegalCommand(g, players.get(0), "place 1 1");
            wrappedLegalCommand(g, players.get(0), "tool 12");
            wrappedIllegalCommand(g, players.get(0), "move 1 1 1 2");
        }
    }
}
