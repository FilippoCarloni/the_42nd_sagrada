package it.polimi.ingsw.model.cards.tools;

import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.init;
import static it.polimi.ingsw.model.TestHelper.wrappedIllegalCommand;
import static it.polimi.ingsw.model.TestHelper.wrappedLegalCommand;
import static it.polimi.ingsw.model.utility.Parameters.USE_COMPLETE_RULES;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TapWheelTest {

    /**
     * Testing
     * <ul>
     *     <li>Tool activation</li>
     *     <li>Tool card tear down on generic command execution</li>
     *     <li>Pass without moving</li>
     * </ul>
     */
    @Test
    void passDirectly() {
        if (USE_COMPLETE_RULES) {
            Game g = init("gen_2p_04");
            List<Player> players = g.getData().getPlayers();
            wrappedLegalCommand(g, players.get(0), "tool 12");
            g.undoCommand();
            wrappedLegalCommand(g, players.get(0), "pick 1");
            wrappedLegalCommand(g, players.get(0), "tool 12");
            wrappedLegalCommand(g, players.get(0), "place 1 2");
            wrappedIllegalCommand(g, players.get(0), "move 1 2 3 1");
            wrappedLegalCommand(g, players.get(0), "pass");
        }
    }

    /**
     * Testing
     * <ul>
     *     <li>Moving rules</li>
     *     <li>Move one die, then pass</li>
     * </ul>
     */
    @Test
    void moveOneDie() {
        if (USE_COMPLETE_RULES) {
            Game g = init("gen_2p_04");
            List<Player> players = g.getData().getPlayers();
            wrappedLegalCommand(g, players.get(0), "pick 3");
            wrappedLegalCommand(g, players.get(0), "place 3 1");
            wrappedLegalCommand(g, players.get(0), "tool 12");
            wrappedIllegalCommand(g, players.get(0), "move 3 1 3 2");
            wrappedIllegalCommand(g, players.get(0), "move 4 5 4 4");
            wrappedIllegalCommand(g, players.get(0), "move 1 1 3 1");
            wrappedLegalCommand(g, players.get(0), "move 1 1 4 1");
            wrappedIllegalCommand(g, players.get(0), "move 4 1 1 1");
            wrappedIllegalCommand(g, players.get(0), "move 2 1 1 2");
            wrappedLegalCommand(g, players.get(0), "pass");
        }
    }

    /**
     * Testing
     * <ul>
     *     <li>Move two dice</li>
     *     <li>Tool tear down</li>
     * </ul>
     */
    @Test
    void moveTwoDice() {
        if (USE_COMPLETE_RULES) {
            Game g = init("gen_2p_04");
            List<Player> players = g.getData().getPlayers();
            wrappedLegalCommand(g, players.get(0), "pick 5");
            wrappedLegalCommand(g, players.get(0), "place 1 2");
            wrappedLegalCommand(g, players.get(0), "tool 12");
            wrappedLegalCommand(g, players.get(0), "move 2 1 2 3");
            wrappedIllegalCommand(g, players.get(0), "move 1 1 2 1");
            assertEquals(0, g.getData().getActiveToolID());
            assertEquals(12, g.getData().getPassiveToolID());
            wrappedLegalCommand(g, players.get(0), "move 1 2 2 1");
            wrappedIllegalCommand(g, players.get(0), "move 1 1 2 1");
            assertEquals(0, g.getData().getActiveToolID());
            assertEquals(0, g.getData().getPassiveToolID());
            wrappedLegalCommand(g, players.get(0), "pass");
        }
    }

    /**
     * Testing
     * <ul>
     *     <li>Illegal die movement when there'n no matching color on the round track</li>
     * </ul>
     */
    @Test
    void noMatchingColor() {
        if (USE_COMPLETE_RULES) {
            Game g = init("gen_2p_05");
            List<Player> players = g.getData().getPlayers();
            wrappedLegalCommand(g, players.get(0), "tool 12");
            wrappedLegalCommand(g, players.get(0), "pick 1");
            wrappedIllegalCommand(g, players.get(0), "tool 12");
            g.undoCommand();
            g.undoCommand();
            wrappedLegalCommand(g, players.get(0), "pick 1");
            wrappedLegalCommand(g, players.get(0), "place 1 1");
            wrappedLegalCommand(g, players.get(0), "tool 12");
            wrappedIllegalCommand(g, players.get(0), "move 1 1 1 2");
        }
    }
}
