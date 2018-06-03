package it.polimi.ingsw.model.cards.tools;

import it.polimi.ingsw.model.gameboard.dice.Die;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FluxRemover {

    @Test
    void test() {
        if (USE_COMPLETE_RULES) {
            Game g = init("gen_2p_04");
            List<Player> players = g.getData().getPlayers();
            wrappedIllegalCommand(g, players.get(0), "tool 11");
            wrappedLegalCommand(g, players.get(0), "pick 1");
            wrappedIllegalCommand(g, players.get(0), "select 4");
            wrappedLegalCommand(g, players.get(0), "tool 11");
            assertEquals(11, g.getData().getPassiveToolID());
            assertEquals(0, g.getData().getActiveToolID());
            Die die = g.getData().getPickedDie();
            assertFalse(g.getData().getDicePool().contains(die));
            if (g.getData().getPickedDie().getColor().equals(Color.RED)) {
                wrappedLegalCommand(g, players.get(0), "pass");
                assertTrue(g.getData().getDicePool().contains(die));
            } else if (g.getData().getPickedDie().getShade().equals(Shade.LIGHTEST)) {
                wrappedIllegalCommand(g, players.get(0), "place 1 2");
                wrappedIllegalCommand(g, players.get(0), "select 0");
                wrappedIllegalCommand(g, players.get(0), "select 7");
                wrappedLegalCommand(g, players.get(0), "select 1");
                g.undoCommand();
                wrappedLegalCommand(g, players.get(0), "select 6");
                wrappedIllegalCommand(g, players.get(0), "select 5");
                wrappedLegalCommand(g, players.get(0), "place 1 2");
                wrappedLegalCommand(g, players.get(0), "pass");
                assertFalse(g.getData().getDicePool().contains(die));
            } else {
                wrappedLegalCommand(g, players.get(0), "place 1 2");
                wrappedIllegalCommand(g, players.get(0), "select 2");
                g.undoCommand();
                wrappedLegalCommand(g, players.get(0), "select 2");
                assertEquals(Shade.LIGHTER, g.getData().getPickedDie().getShade());
                wrappedIllegalCommand(g, players.get(0), "select 3");
                wrappedIllegalCommand(g, players.get(0), "tool 11");
                wrappedLegalCommand(g, players.get(0), "place 1 2");
                wrappedLegalCommand(g, players.get(0), "pass");
                assertFalse(g.getData().getDicePool().contains(die));
            }
        }
    }
}
