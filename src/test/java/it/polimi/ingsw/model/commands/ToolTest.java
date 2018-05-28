package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.gamedata.ConcreteGame;
import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.*;

class ToolTest {

    /**
     * Checks if the player can use a tool card during his turn,
     * but only one and at the right moment.
     */
    @Test
    void test() {
        List<Player> players = init(2);
        Game g = new ConcreteGame(players);
        setToolCard(g.getData(), "Grinding Stone");
        wrappedIllegalCommand(g, players.get(0), "tool 1");
        wrappedLegalCommand(g, players.get(0), "pick 1");
        wrappedLegalCommand(g, players.get(0), "tool 1");
        wrappedIllegalCommand(g, players.get(0), "tool 1");
    }
}
