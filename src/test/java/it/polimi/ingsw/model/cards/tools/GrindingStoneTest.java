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

class GrindingStoneTest {

    @Test
    void grindingStoneTest() {
        Game g = init("gen_2p_01");
        List<Player> players = g.getData().getPlayers();
        wrappedLegalCommand(g, players.get(0), "pick 1");
        assertEquals(0, g.getData().getTools().get(2).getFavorPoints());
        assertEquals(5, g.getCurrentPlayer().getFavorPoints());
        assertEquals(Shade.LIGHTEST, g.getData().getPickedDie().getShade());
        wrappedLegalCommand(g, players.get(0), "tool 3");
        assertEquals(1, g.getData().getTools().get(2).getFavorPoints());
        assertEquals(4, g.getCurrentPlayer().getFavorPoints());
        assertEquals(Shade.DARKEST, g.getData().getPickedDie().getShade());
        wrappedLegalCommand(g, players.get(0), "place 1 1");
        wrappedLegalCommand(g, players.get(0), "pass");
        wrappedIllegalCommand(g, players.get(1), "tool 1");
        wrappedLegalCommand(g, players.get(1), "pick 1");
        assertEquals(1, g.getData().getTools().get(2).getFavorPoints());
        assertEquals(5, g.getCurrentPlayer().getFavorPoints());
        assertEquals(Shade.LIGHTER, g.getData().getPickedDie().getShade());
        wrappedLegalCommand(g, players.get(1), "tool 3");
        assertEquals(3, g.getData().getTools().get(2).getFavorPoints());
        assertEquals(3, g.getCurrentPlayer().getFavorPoints());
        assertEquals(Shade.DARKER, g.getData().getPickedDie().getShade());
    }
}
