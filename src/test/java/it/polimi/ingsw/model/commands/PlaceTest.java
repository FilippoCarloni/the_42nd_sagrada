package it.polimi.ingsw.model.commands;
import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.init;
import static it.polimi.ingsw.model.TestHelper.wrappedIllegalCommand;
import static it.polimi.ingsw.model.TestHelper.wrappedLegalCommand;

class PlaceTest {

    /**
     * Testing generic functionality of place command
     */
    @Test
    void placeTest() {
        Game g = init("gen_2p_01");
        List<Player> players = g.getData().getPlayers();
        wrappedIllegalCommand(g, players.get(0), "place 1 1");
        wrappedLegalCommand(g, players.get(0), "pick 1");
        wrappedIllegalCommand(g, players.get(0), "place 1 6");
        wrappedLegalCommand(g, players.get(0), "place 1 1");
        g.undoCommand();
        wrappedLegalCommand(g, players.get(0), "place 1 1");
        wrappedIllegalCommand(g, players.get(0), "place 1 1");
        wrappedIllegalCommand(g, players.get(0), "pick 1");
        wrappedLegalCommand(g, players.get(0), "pass");
        wrappedLegalCommand(g, players.get(1), "pass");
        wrappedLegalCommand(g, players.get(1), "pass");
        wrappedLegalCommand(g, players.get(0), "pick 1");
        wrappedIllegalCommand(g, players.get(0), "place 1 1");
        wrappedIllegalCommand(g, players.get(0), "place 1 5");
        wrappedLegalCommand(g, players.get(0), "place 2 1");
    }

    /**
     * Testing placing rules compliant behaviour of place command
     */
    @Test
    void ruleTest() {
        Game g = init("gen_2p_01");
        List<Player> players = g.getData().getPlayers();
        wrappedLegalCommand(g, players.get(0), "pass");
        wrappedLegalCommand(g, players.get(1), "pick 2");
        wrappedLegalCommand(g, players.get(1), "place 4 3");
        g.undoCommand();
        wrappedLegalCommand(g, players.get(1), "place 1 4");
        g.undoCommand();
        wrappedLegalCommand(g, players.get(1), "place 4 1");
        g.undoCommand();
        wrappedIllegalCommand(g, players.get(1), "place 3 1");
        wrappedIllegalCommand(g, players.get(1), "place 3 3");
        g.undoCommand();
        wrappedLegalCommand(g, players.get(1), "pick 1");
        wrappedIllegalCommand(g, players.get(1), "place 4 1");
    }
}
