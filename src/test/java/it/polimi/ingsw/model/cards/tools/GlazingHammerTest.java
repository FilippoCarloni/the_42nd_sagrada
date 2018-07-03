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
import static org.junit.jupiter.api.Assertions.assertFalse;

class GlazingHammerTest {

    /**
     * Testing
     * <ul>
     *     <li>Before drafting condition</li>
     *     <li>Undo unavailable</li>
     * </ul>
     */
    @Test
    void test() {
        if (USE_COMPLETE_RULES) {
            Game g = init("gen_2p_03");
            List<Player> players = g.getData().getPlayers();
            wrappedIllegalCommand(g, players.get(0), "tool 7");
            wrappedLegalCommand(g, players.get(0), "pass");
            wrappedLegalCommand(g, players.get(0), "pick 1");
            wrappedIllegalCommand(g, players.get(0), "tool 7");
            wrappedLegalCommand(g, players.get(0), "place 1 2");
            wrappedIllegalCommand(g, players.get(0), "tool 7");
            g.undoCommand();
            g.undoCommand();
            wrappedLegalCommand(g, players.get(0), "tool 7");
            assertFalse(g.isUndoAvailable());
            wrappedLegalCommand(g, players.get(0), "pick 1");
            // the die can't be placed in (1, 2)
            lightest(g, players);
            // the die can be placed in (1, 2)
            dark(g, players);
        }
    }

    private void lightest(Game g, List<Player> players) {
        g.getData().getPickedDie().setShade(Shade.LIGHTEST);
        wrappedIllegalCommand(g, players.get(0), "place 1 2");
    }

    private void dark(Game g, List<Player> players) {
        g.getData().getPickedDie().setShade(Shade.DARK);
        wrappedLegalCommand(g, players.get(0), "place 1 2");
        wrappedIllegalCommand(g, players.get(0), "tool 7");
        wrappedLegalCommand(g, players.get(0), "pass");
    }
}
