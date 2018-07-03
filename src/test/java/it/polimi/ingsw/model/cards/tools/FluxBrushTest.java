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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FluxBrushTest {

    /**
     * Testing
     * <ul>
     *     <li>Tool activation</li>
     *     <li>Undo unavailable</li>
     *     <li>Pass without placing</li>
     *     <li>Place and tool tear down</li>
     * </ul>
     */
    @Test
    void test() {
        Game g = init("gen_2p_02");
        List<Player> players = g.getData().getPlayers();
        wrappedIllegalCommand(g, players.get(0), "tool 6");
        wrappedLegalCommand(g, players.get(0), "pick 1");
        assertTrue(g.isUndoAvailable());
        wrappedLegalCommand(g, players.get(0), "tool 6");
        assertFalse(g.isUndoAvailable());
        lightestShade(g, players);
        notLightestShade(g, players);
    }

    private void lightestShade(Game g, List<Player> players) {
        Die pickedDie = g.getData().getPickedDie();
        pickedDie.setShade(Shade.LIGHTEST);
        wrappedLegalCommand(g, players.get(0), "pass");
        assertNull(g.getData().getPickedDie());
        assertTrue(g.getData().getDicePool().contains(pickedDie));
        g.undoCommand();
    }

    private void notLightestShade(Game g, List<Player> players) {
        Die pickedDie = g.getData().getPickedDie();
        pickedDie.setShade(Shade.DARK);
        assertFalse(g.isUndoAvailable());
        wrappedLegalCommand(g, players.get(0), "place 1 2");
        assertTrue(g.isUndoAvailable());
        wrappedIllegalCommand(g, players.get(0), "tool 4");
        wrappedIllegalCommand(g, players.get(0), "tool 6");
        wrappedLegalCommand(g, players.get(0), "pass");
        assertNull(g.getData().getPickedDie());
        assertFalse(g.getData().getDicePool().contains(pickedDie));
    }
}
