package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.utility.Parameters;
import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.turns.ConcreteTurnManager;
import it.polimi.ingsw.model.turns.TurnManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TurnManagerTest {

    @Test
    void testTwoPlayers() {
        Player a = new ConcretePlayer("a");
        Player b = new ConcretePlayer("b");
        ArrayList<Player> players = new ArrayList<>();

        players.add(a);
        players.add(b);
        TurnManager tm = new ConcreteTurnManager(players);
        tm.advanceTurn();

        for (int i = 0; i < Parameters.TOTAL_NUMBER_OF_ROUNDS; i++) {
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
        }
    }

    @Test
    void testThreePlayers() {
        Player a = new ConcretePlayer("a");
        Player b = new ConcretePlayer("b");
        Player c = new ConcretePlayer("c");
        ArrayList<Player> players = new ArrayList<>();
        players.add(a);
        players.add(b);
        players.add(c);
        TurnManager tm = new ConcreteTurnManager(players);
        tm.advanceTurn();

        for (int i = 0; i < Parameters.TOTAL_NUMBER_OF_ROUNDS; i++) {
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            tm.advanceTurn();
        }
    }

    @Test
    void testFourPlayers() {
        Player a = new ConcretePlayer("a");
        Player b = new ConcretePlayer("b");
        Player c = new ConcretePlayer("c");
        Player d = new ConcretePlayer("d");
        ArrayList<Player> players = new ArrayList<>();

        players.add(a);
        players.add(b);
        players.add(c);
        players.add(d);
        TurnManager tm = new ConcreteTurnManager(players);
        tm.advanceTurn();

        for (int i = 0; i < Parameters.TOTAL_NUMBER_OF_ROUNDS; i++) {
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(d, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(d, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(d, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(d, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(d, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(d, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(d, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            tm.advanceTurn();
            assertEquals(d, tm.getCurrentPlayer());
            tm.advanceTurn();
        }
    }
}
