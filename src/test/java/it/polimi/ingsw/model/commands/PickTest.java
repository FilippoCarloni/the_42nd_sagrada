package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.init;
import static it.polimi.ingsw.model.TestHelper.wrappedIllegalCommand;
import static it.polimi.ingsw.model.TestHelper.wrappedLegalCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PickTest {

    /**
     * Testing basic picking functionality.
     */
    @Test
    void pickTest() {
        Game g = init("gen_2p_01");
        List<Player> players = g.getData().getPlayers();
        wrappedIllegalCommand(g, players.get(1), "pick 1");
        wrappedIllegalCommand(g, players.get(0), "pick 0");
        wrappedIllegalCommand(g, players.get(0), "pick 6");
        wrappedLegalCommand(g, players.get(0), "pick 1");
        wrappedIllegalCommand(g, players.get(0), "pick 2");
        Die d = g.getData().getPickedDie();
        g.undoCommand();
        wrappedLegalCommand(g, players.get(0), "pick 1");
        assertEquals(d, g.getData().getPickedDie());
        wrappedIllegalCommand(g, players.get(0), "pass");
        wrappedLegalCommand(g, players.get(0), "place 1 1");
        wrappedIllegalCommand(g, players.get(0), "pick 2");
        wrappedLegalCommand(g, players.get(0), "pass");
    }
}
