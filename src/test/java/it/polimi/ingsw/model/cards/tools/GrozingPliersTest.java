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

    /**
     * Testing
     * <ul>
     *     <li>Increase and decrease commands</li>
     *     <li>Tool activation</li>
     *     <li>Favor points constraints</li>
     *     <li>Tool tear down</li>
     * </ul>
     */
    @Test
    void test() {
        Game g = init("gen_2p_01");
        List<Player> players = g.getData().getPlayers();
        // checks tool card availability
        preliminaryChecks(g, players);
        // activates the tool card
        toolActivation(g, players);
        // increases the value of the picked die and passes the turn
        firstIncrease(g, players);
        // decreases the value of the picked die and passes the turn
        firstDecrease(g, players);
        // decreases the value of the picked die and passes the turn
        secondDecrease(g, players);
        // checks the number of favor points according to the legality of tool usage
        finalCheck(g, players);
    }

    private void preliminaryChecks(Game g, List<Player> players) {
        wrappedIllegalCommand(g, players.get(0), "tool 1");
        assertEquals(2, g.getData().getTools().get(0).getID());
        assertEquals(0, g.getData().getActiveToolID());
        assertEquals(0, g.getData().getPassiveToolID());
    }

    private void toolActivation(Game g, List<Player> players) {
        wrappedLegalCommand(g, players.get(0), "pick 1");
        wrappedLegalCommand(g, players.get(0), "tool 1");
        assertEquals(1, g.getData().getActiveToolID());
        assertEquals(1, g.getData().getPassiveToolID());
    }

    private void firstIncrease(Game g, List<Player> players) {
        wrappedIllegalCommand(g, players.get(0), "place 1 1");
        assertEquals(Shade.LIGHTEST, g.getData().getPickedDie().getShade());
        wrappedIllegalCommand(g, players.get(0), "decrease"); // can't decrease a 1
        wrappedIllegalCommand(g, players.get(0), "tool 1");
        wrappedLegalCommand(g, players.get(0), "increase");
        assertEquals(Shade.LIGHTER, g.getData().getPickedDie().getShade());
        wrappedLegalCommand(g, players.get(0), "place 1 1");
        wrappedIllegalCommand(g, players.get(0), "tool 1");
        wrappedLegalCommand(g, players.get(0), "pass");
    }

    private void firstDecrease(Game g, List<Player> players) {
        wrappedLegalCommand(g, players.get(1), "pick 1");
        wrappedLegalCommand(g, players.get(1), "tool 1");
        wrappedLegalCommand(g, players.get(1), "decrease");
        assertEquals(4, g.getData().getPlayers().get(0).getFavorPoints());
        assertEquals(3, g.getData().getPlayers().get(1).getFavorPoints());
        assertEquals(3, g.getData().getTools().get(2).getFavorPoints());
        wrappedLegalCommand(g, players.get(1), "place 1 1");
        wrappedLegalCommand(g, players.get(1), "pass");
    }

    private void secondDecrease(Game g, List<Player> players) {
        wrappedLegalCommand(g, players.get(1), "pick 1");
        wrappedLegalCommand(g, players.get(1), "tool 1");
        assertEquals(Shade.DARKER, g.getData().getPickedDie().getShade());
        wrappedLegalCommand(g, players.get(1), "decrease");
        assertEquals(Shade.DARK, g.getData().getPickedDie().getShade());
        wrappedLegalCommand(g, players.get(1), "place 2 1");
        wrappedLegalCommand(g, players.get(1), "pass");
    }

    private void finalCheck(Game g, List<Player> players) {
        wrappedLegalCommand(g, players.get(0), "pass");
        wrappedIllegalCommand(g, players.get(1), "tool 1");
        wrappedLegalCommand(g, players.get(1), "pick 1");
        wrappedIllegalCommand(g, players.get(1), "tool 1");
        assertEquals(4, g.getData().getPlayers().get(0).getFavorPoints());
        assertEquals(1, g.getData().getPlayers().get(1).getFavorPoints());
        assertEquals(5, g.getData().getTools().get(2).getFavorPoints());
    }
}
