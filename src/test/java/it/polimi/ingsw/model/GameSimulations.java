package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.*;

class GameSimulations {

    /**
     * 2 player match, no tool cards.
     */
    @Test
    void game1() {
        Game g = init("gen_2p_01");
        List<Player> players = g.getData().getPlayers();
        wrappedLegalCommand(g, players.get(0), "pick 1");
        wrappedLegalCommand(g, players.get(0), "place 1 1");
        wrappedLegalCommand(g, players.get(0), "pass");
        wrappedLegalCommand(g, players.get(1), "pick 1");
        wrappedLegalCommand(g, players.get(1), "place 4 1");
        wrappedLegalCommand(g, players.get(1), "pass");
        wrappedLegalCommand(g, players.get(1), "pick 1");
        wrappedLegalCommand(g, players.get(1), "place 4 2");
        wrappedLegalCommand(g, players.get(1), "pass");
        wrappedLegalCommand(g, players.get(0), "pick 2");
        wrappedLegalCommand(g, players.get(0), "place 2 2");
        wrappedLegalCommand(g, players.get(0), "pass");
    }
}
