package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.gamedata.Game;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.TestHelper.init;
import static it.polimi.ingsw.model.TestHelper.wrappedIllegalCommand;
import static it.polimi.ingsw.model.TestHelper.wrappedLegalCommand;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EndGame {

    /**
     * Tests command inhibition when the game ends.
     */
    @Test
    void endGame() {
        Game g = init("gen_2p_01");
        while (!g.isGameEnded())
            wrappedLegalCommand(g, g.getCurrentPlayer(), "pass");
        assertNotNull(g.getData().getDicePool().get(0));
        wrappedIllegalCommand(g, g.getCurrentPlayer(), "pick 1");
        wrappedIllegalCommand(g, g.getCurrentPlayer(), "pass");
        assertFalse(g.isUndoAvailable());
        assertFalse(g.isRedoAvailable());
    }
}
