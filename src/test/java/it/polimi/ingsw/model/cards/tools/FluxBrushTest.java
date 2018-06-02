package it.polimi.ingsw.model.cards.tools;

import it.polimi.ingsw.model.gameboard.dice.Die;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

class FluxBrushTest {

    @Test
    void test() {
        if (USE_COMPLETE_RULES) {
            Game g = init("gen_2p_02");
            List<Player> players = g.getData().getPlayers();
            wrappedIllegalCommand(g, players.get(0), "tool 6");
            wrappedLegalCommand(g, players.get(0), "pick 1");
            assertTrue(g.isUndoAvailable());
            wrappedLegalCommand(g, players.get(0), "tool 6");
            assertFalse(g.isUndoAvailable());
            Die pickedDie = g.getData().getPickedDie();
            if (g.getData().getPickedDie().getShade().equals(Shade.LIGHTEST)) {
                wrappedLegalCommand(g, players.get(0), "pass");
                assertTrue(g.getData().getPickedDie() == null);
                assertTrue(g.getData().getDicePool().contains(pickedDie));
            } else {
                assertFalse(g.isUndoAvailable());
                wrappedLegalCommand(g, players.get(0), "place 1 2");
                assertTrue(g.isUndoAvailable());
                wrappedIllegalCommand(g, players.get(0), "tool 4");
                wrappedIllegalCommand(g, players.get(0), "tool 6");
                wrappedLegalCommand(g, players.get(0), "pass");
                assertTrue(g.getData().getPickedDie() == null);
                assertFalse(g.getData().getDicePool().contains(pickedDie));
            }
        }
    }
}
