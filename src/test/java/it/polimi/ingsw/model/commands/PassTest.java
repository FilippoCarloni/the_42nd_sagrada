package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PassTest {

    /**
     * Pass is not allowed if you picked a die.
     * Pass advances the status of the game.
     */
    @Test
    void passTest() {
        Game g = init("gen_2p_01");
        List<Player> players = g.getData().getPlayers();
        wrappedLegalCommand(g, players.get(0), "pick 1");
        assertTrue(g.isUndoAvailable());
        wrappedIllegalCommand(g, players.get(0), "pass");
        wrappedLegalCommand(g, players.get(0), "place 1 1");
        assertTrue(g.isUndoAvailable());
        wrappedLegalCommand(g, players.get(0), "pass");
        assertFalse(g.isUndoAvailable());
        wrappedIllegalCommand(g, players.get(0), "pick 1");
        wrappedLegalCommand(g, players.get(1), "pick 1");
        assertTrue(g.isUndoAvailable());
        wrappedLegalCommand(g, players.get(1), "tool 2");
        assertTrue(g.isUndoAvailable());
        wrappedIllegalCommand(g, players.get(1), "pass");
        wrappedLegalCommand(g, players.get(1), "increase");
        assertTrue(g.isUndoAvailable());
        wrappedLegalCommand(g, players.get(1), "place 1 2");
        assertTrue(g.isUndoAvailable());
    }
}
