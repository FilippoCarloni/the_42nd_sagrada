package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gamedata.ConcreteGame;
import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameSimulations {

    /**
     * 2 player match, no tool cards.
     */
    @Test
    void game1() {
        List<Player> players = init(2);
        players.get(0).setWindowFrame(getFrame("Batllo"));
        players.get(1).setWindowFrame(getFrame("Gravitas"));
        Game g = new ConcreteGame(players);
        setDicePool(g.getData(), "R1B2Y5G4P5");
        wrappedLegalCommand(g, players.get(0), "pick 1");
        wrappedLegalCommand(g, players.get(0), "place 1 1");
        wrappedPass(g, players.get(0));
        wrappedLegalCommand(g, players.get(1), "pick 1");
        wrappedLegalCommand(g, players.get(1), "place 4 1");
        wrappedPass(g, players.get(1));
        wrappedLegalCommand(g, players.get(1), "pick 1");
        wrappedLegalCommand(g, players.get(1), "place 4 2");
        wrappedPass(g, players.get(1));
        wrappedLegalCommand(g, players.get(0), "pick 2");
        wrappedLegalCommand(g, players.get(0), "place 2 2");
        wrappedPass(g, players.get(0));
        assertTrue(0 == g.getData().getCurrentScore().get(players.get(0)));
        assertTrue(0 == g.getData().getCurrentScore().get(players.get(1)));

        System.out.println(g.getData());
    }
}
