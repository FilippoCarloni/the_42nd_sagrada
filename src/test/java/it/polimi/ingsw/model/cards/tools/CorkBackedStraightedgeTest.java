package it.polimi.ingsw.model.cards.tools;

import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Shade;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.init;
import static it.polimi.ingsw.model.TestHelper.wrappedIllegalCommand;
import static it.polimi.ingsw.model.TestHelper.wrappedLegalCommand;
import static it.polimi.ingsw.model.utility.Parameters.USE_COMPLETE_RULES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CorkBackedStraightedgeTest {

    /**
     * Testing
     * <ul>
     *     <li>Correct tool activation</li>
     *     <li>Tool tear down</li>
     *     <li>Placing according to the tool rules</li>
     * </ul>
     */
    @Test
    void test() {
        if (USE_COMPLETE_RULES) {
            Game g = init("gen_2p_03");
            List<Player> players = g.getData().getPlayers();
            wrappedIllegalCommand(g, players.get(0), "tool 9");
            wrappedLegalCommand(g, players.get(0), "pick 1");
            wrappedIllegalCommand(g, players.get(0), "place 4 5");
            wrappedLegalCommand(g, players.get(0), "tool 9");
            wrappedIllegalCommand(g, players.get(0), "pass");
            assertNull(g.getCurrentPlayer().getWindowFrame().getDie(3, 4));
            wrappedLegalCommand(g, players.get(0), "place 4 5");
            wrappedIllegalCommand(g, players.get(0), "tool 9");
            assertEquals(Shade.LIGHT, g.getCurrentPlayer().getWindowFrame().getDie(3, 4).getShade());
            assertEquals(Color.PURPLE, g.getCurrentPlayer().getWindowFrame().getDie(3, 4).getColor());
            wrappedLegalCommand(g, players.get(0), "pass");
        }
    }
}
