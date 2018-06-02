package it.polimi.ingsw.model.cards.tools;

import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.init;
import static it.polimi.ingsw.model.TestHelper.wrappedIllegalCommand;
import static it.polimi.ingsw.model.TestHelper.wrappedLegalCommand;
import static it.polimi.ingsw.model.utility.Parameters.USE_COMPLETE_RULES;

class RunningPliersTest {

    @Test
    void test() {
        if (USE_COMPLETE_RULES) {
            Game g = init("gen_2p_03");
            List<Player> players = g.getData().getPlayers();
            wrappedLegalCommand(g, players.get(0), "pick 1");
            wrappedIllegalCommand(g, players.get(0), "tool 8");
            wrappedLegalCommand(g, players.get(0), "place 1 2");
            wrappedLegalCommand(g, players.get(0), "tool 8");
            wrappedLegalCommand(g, players.get(0), "pick 2");
            wrappedIllegalCommand(g, players.get(0), "tool 7");
            wrappedLegalCommand(g, players.get(0), "place 2 2");
            wrappedLegalCommand(g, players.get(0), "pass");
            wrappedIllegalCommand(g, players.get(1), "tool 8");
            wrappedLegalCommand(g, players.get(1), "pass");
            wrappedLegalCommand(g, players.get(0), "pass");
            wrappedLegalCommand(g, players.get(1), "pass");
            wrappedLegalCommand(g, players.get(1), "pass");
            wrappedLegalCommand(g, players.get(0), "pass");
        }
    }
}
