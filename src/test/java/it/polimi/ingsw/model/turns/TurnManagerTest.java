package it.polimi.ingsw.model.turns;

import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.turns.ArrayTurnManager;
import it.polimi.ingsw.model.turns.TurnManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TurnManagerTest {

    @Test
    void testIllegalArgument() {
        Player p = new ConcretePlayer("player");
        ArrayList<Player> list = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> new ArrayTurnManager(list));
        list.add(p);
        assertThrows(IllegalArgumentException.class, () -> new ArrayTurnManager(list));
    }

    @Test
    void testTwoPlayers() {
        Player a = new ConcretePlayer("a");
        Player b = new ConcretePlayer("b");
        ArrayList<Player> players = new ArrayList<>();

        players.add(a);
        players.add(b);
        TurnManager tm = new ArrayTurnManager(players);

        for (int i = 0; i < Parameters.TOTAL_NUMBER_OF_ROUNDS; i++) {
            assertEquals(true, tm.isRoundStarting());
            assertEquals(false, tm.isRoundEnding());
            assertEquals(a, tm.getCurrentPlayer());
            assertEquals(false, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(false, tm.isRoundStarting());
            assertEquals(false, tm.isRoundEnding());
            assertEquals(b, tm.getCurrentPlayer());
            assertEquals(false, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            assertEquals(true, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            assertEquals(true, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            assertEquals(false, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            assertEquals(false, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            assertEquals(true, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(false, tm.isRoundStarting());
            assertEquals(true, tm.isRoundEnding());
            assertEquals(b, tm.getCurrentPlayer());
            assertEquals(true, tm.isSecondTurn());
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
        TurnManager tm = new ArrayTurnManager(players);

        for (int i = 0; i < Parameters.TOTAL_NUMBER_OF_ROUNDS; i++) {
            assertEquals(true, tm.isRoundStarting());
            assertEquals(false, tm.isRoundEnding());
            assertEquals(a, tm.getCurrentPlayer());
            assertEquals(false, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(false, tm.isRoundStarting());
            assertEquals(false, tm.isRoundEnding());
            assertEquals(b, tm.getCurrentPlayer());
            assertEquals(false, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            assertEquals(false, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            assertEquals(true, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            assertEquals(true, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            assertEquals(true, tm.isSecondTurn());
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
            assertEquals(false, tm.isRoundStarting());
            assertEquals(true, tm.isRoundEnding());
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
        TurnManager tm = new ArrayTurnManager(players);

        for (int i = 0; i < Parameters.TOTAL_NUMBER_OF_ROUNDS; i++) {
            assertEquals(true, tm.isRoundStarting());
            assertEquals(false, tm.isRoundEnding());
            assertEquals(a, tm.getCurrentPlayer());
            assertEquals(false, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(false, tm.isRoundStarting());
            assertEquals(false, tm.isRoundEnding());
            assertEquals(b, tm.getCurrentPlayer());
            assertEquals(false, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            assertEquals(false, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(d, tm.getCurrentPlayer());
            assertEquals(false, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(d, tm.getCurrentPlayer());
            assertEquals(true, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(c, tm.getCurrentPlayer());
            assertEquals(true, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(b, tm.getCurrentPlayer());
            assertEquals(true, tm.isSecondTurn());
            tm.advanceTurn();
            assertEquals(a, tm.getCurrentPlayer());
            assertEquals(true, tm.isSecondTurn());
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
            assertEquals(false, tm.isRoundStarting());
            assertEquals(true, tm.isRoundEnding());
            assertEquals(d, tm.getCurrentPlayer());
            tm.advanceTurn();
        }
    }

    @Test
    void testAdditionalFunctionality() {
        Player a = new ConcretePlayer("a");
        Player b = new ConcretePlayer("b");
        ArrayList<Player> players = new ArrayList<>();

        players.add(a);
        players.add(b);
        TurnManager tm = new ArrayTurnManager(players);

        assertEquals(a, tm.getCurrentPlayer());
        tm.takeTwoTurns();
        tm.advanceTurn();
        assertEquals(a, tm.getCurrentPlayer());
        assertThrows(NoSuchElementException.class, tm::takeTwoTurns);
        tm.advanceTurn();
        assertEquals(b, tm.getCurrentPlayer());
        tm.advanceTurn();
        assertEquals(b, tm.getCurrentPlayer());
        tm.advanceTurn();
        assertEquals(b, tm.getCurrentPlayer());
        tm.takeTwoTurns();
        tm.advanceTurn();
        assertEquals(b, tm.getCurrentPlayer());
        tm.advanceTurn();
        assertEquals(a, tm.getCurrentPlayer());
        tm.advanceTurn();
        assertEquals(a, tm.getCurrentPlayer());
    }

    @Test
    void testJSON() {
        Player a = new ConcretePlayer("a");
        Player b = new ConcretePlayer("b");
        Player c = new ConcretePlayer("c");
        ArrayList<Player> players = new ArrayList<>();
        players.add(a);
        players.add(b);
        players.add(c);
        TurnManager tm = new ArrayTurnManager(players);
        TurnManager tmClone = new ArrayTurnManager(tm.encode());

        for (int i = 0; i < 30; i++) {
            assertEquality(tm, tmClone);
            advance(tm, tmClone);
        }

        tm.takeTwoTurns();
        tmClone.takeTwoTurns();
        assertEquality(tm, tmClone);
        advance(tm, tmClone);
        assertEquality(tm, tmClone);
        advance(tm, tmClone);
        assertEquality(tm, tmClone);
    }

    private void assertEquality(TurnManager tm, TurnManager tmClone) {
        assertEquals(tm.getCurrentPlayer().getUsername(), tmClone.getCurrentPlayer().getUsername());
        assertEquals(tm.isSecondTurn(), tmClone.isSecondTurn());
        assertEquals(tm.isRoundEnding(), tmClone.isRoundEnding());
        assertEquals(tm.isRoundStarting(), tmClone.isRoundStarting());
    }

    private void advance(TurnManager tm, TurnManager tmClone) {
        tm.takeTwoTurns();
        tmClone.takeTwoTurns();
    }
}
