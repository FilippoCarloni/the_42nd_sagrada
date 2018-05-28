package it.polimi.ingsw.model.cards.tools;

import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.Shade;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.init;
import static it.polimi.ingsw.model.TestHelper.wrappedIllegalCommand;
import static it.polimi.ingsw.model.TestHelper.wrappedLegalCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GrozingPliersTest {

    @Test
    void grozingPliersTest() {
        Game g = init("gen_2p_01");
        List<Player> players = g.getData().getPlayers();
        wrappedIllegalCommand(g, players.get(0), "tool 2");
        wrappedLegalCommand(g, players.get(0), "pick 1");
        assertEquals(Shade.LIGHTEST, g.getData().getPickedDie().getShade());
        wrappedLegalCommand(g, players.get(0), "tool 2");
        wrappedIllegalCommand(g, players.get(0), "decrease");
        wrappedLegalCommand(g, players.get(0), "increase");
        assertEquals(Shade.LIGHTER, g.getData().getPickedDie().getShade());
        g.undoCommand();
        g.undoCommand();
        g.undoCommand();
        wrappedLegalCommand(g, players.get(0), "pick 5");
        assertEquals(Shade.DARKER, g.getData().getPickedDie().getShade());
        wrappedLegalCommand(g, players.get(0), "tool 2");
        wrappedLegalCommand(g, players.get(0), "increase");
        assertEquals(Shade.DARKEST, g.getData().getPickedDie().getShade());
        g.undoCommand();
        wrappedLegalCommand(g, players.get(0), "decrease");
        assertEquals(Shade.DARK, g.getData().getPickedDie().getShade());
    }
}
