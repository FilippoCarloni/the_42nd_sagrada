package it.polimi.ingsw.model.cards.tools;

import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.Shade;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.init;
import static it.polimi.ingsw.model.TestHelper.wrappedIllegalCommand;
import static it.polimi.ingsw.model.TestHelper.wrappedLegalCommand;
import static it.polimi.ingsw.model.utility.Parameters.USE_COMPLETE_RULES;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GrindingStoneTest {

    @Test
    void test() {
        if (USE_COMPLETE_RULES) {
            Game g = init("gen_2p_04");
            List<Player> players = g.getData().getPlayers();
            wrappedIllegalCommand(g, players.get(0), "tool 10");
            wrappedLegalCommand(g, players.get(0), "pick 1");
            wrappedLegalCommand(g, players.get(0), "place 3 1");
            wrappedLegalCommand(g, players.get(0), "pass");
            wrappedLegalCommand(g, players.get(0), "pick 3");
            assertEquals(Shade.LIGHT, g.getData().getPickedDie().getShade());
            wrappedIllegalCommand(g, players.get(0), "place 4 2");
            wrappedLegalCommand(g, players.get(0), "tool 10");
            assertEquals(Shade.DARK, g.getData().getPickedDie().getShade());
            wrappedLegalCommand(g, players.get(0), "place 4 2");
            wrappedIllegalCommand(g, players.get(0), "tool 10");
            wrappedLegalCommand(g, players.get(0), "pass");
        }
    }
}
