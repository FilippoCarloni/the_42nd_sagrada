package it.polimi.ingsw.model.turns;

import it.polimi.ingsw.model.utility.JSONFactory;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static it.polimi.ingsw.model.TestHelper.getPlayerList;
import static org.junit.jupiter.api.Assertions.*;

class TurnManagerTest {

    private static void assertTurn(TurnManager turnManager, List<Player> players, int index, boolean roundStarting, boolean roundEnding, boolean isSecondTurn) {
        assertEquals(roundStarting, turnManager.isRoundStarting());
        assertEquals(roundEnding, turnManager.isRoundEnding());
        assertEquals(players.get(index), turnManager.getCurrentPlayer());
        assertEquals(isSecondTurn, turnManager.isSecondTurn());
        turnManager.advanceTurn();
    }

    /**
     * Throws exceptions for illegal arguments.
     */
    @Test
    void testIllegalArgument() {
        assertThrows(NullPointerException.class, () -> new ArrayTurnManager(null));
        assertThrows(IllegalArgumentException.class, () -> new ArrayTurnManager(getPlayerList(0, false)));
        assertThrows(IllegalArgumentException.class, () -> new ArrayTurnManager(getPlayerList(1, false)));
    }

    /**
     * Checks turn order of a 2-player game.
     */
    @Test
    void testTwoPlayers() {
        List<Player> players = getPlayerList(2, false);
        TurnManager tm = new ArrayTurnManager(players);
        for (int i = 0; i < Parameters.TOTAL_NUMBER_OF_ROUNDS; i++) {
            // player0 first
            assertTurn(tm, players, 0, true, false, false);
            assertTurn(tm, players, 1, false, false, false);
            assertTurn(tm, players, 1, false, false, true);
            assertTurn(tm, players, 0, false, true, true);
            // player1 first
            assertTurn(tm, players, 1, true, false, false);
            assertTurn(tm, players, 0, false, false, false);
            assertTurn(tm, players, 0, false, false, true);
            assertTurn(tm, players, 1, false, true, true);
        }
    }

    /**
     * Checks turn order of a 3-player game.
     */
    @Test
    void testThreePlayers() {
        List<Player> players = getPlayerList(3, false);
        TurnManager tm = new ArrayTurnManager(players);
        for (int i = 0; i < Parameters.TOTAL_NUMBER_OF_ROUNDS; i++) {
            // player0 first
            assertTurn(tm, players, 0, true, false, false);
            assertTurn(tm, players, 1, false, false, false);
            assertTurn(tm, players, 2, false, false, false);
            assertTurn(tm, players, 2, false, false, true);
            assertTurn(tm, players, 1, false, false, true);
            assertTurn(tm, players, 0, false, true, true);
            // player1 first
            assertTurn(tm, players, 1, true, false, false);
            assertTurn(tm, players, 2, false, false, false);
            assertTurn(tm, players, 0, false, false, false);
            assertTurn(tm, players, 0, false, false, true);
            assertTurn(tm, players, 2, false, false, true);
            assertTurn(tm, players, 1, false, true, true);
            // player2 first
            assertTurn(tm, players, 2, true, false, false);
            assertTurn(tm, players, 0, false, false, false);
            assertTurn(tm, players, 1, false, false, false);
            assertTurn(tm, players, 1, false, false, true);
            assertTurn(tm, players, 0, false, false, true);
            assertTurn(tm, players, 2, false, true, true);
        }
    }

    /**
     * Checks turn order of a 4-player game.
     */
    @Test
    void testFourPlayers() {
        List<Player> players = getPlayerList(4, false);
        TurnManager tm = new ArrayTurnManager(players);
        for (int i = 0; i < Parameters.TOTAL_NUMBER_OF_ROUNDS; i++) {
            // player0 first
            assertTurn(tm, players, 0, true, false, false);
            assertTurn(tm, players, 1, false, false, false);
            assertTurn(tm, players, 2, false, false, false);
            assertTurn(tm, players, 3, false, false, false);
            assertTurn(tm, players, 3, false, false, true);
            assertTurn(tm, players, 2, false, false, true);
            assertTurn(tm, players, 1, false, false, true);
            assertTurn(tm, players, 0, false, true, true);
            // player1 first
            assertTurn(tm, players, 1, true, false, false);
            assertTurn(tm, players, 2, false, false, false);
            assertTurn(tm, players, 3, false, false, false);
            assertTurn(tm, players, 0, false, false, false);
            assertTurn(tm, players, 0, false, false, true);
            assertTurn(tm, players, 3, false, false, true);
            assertTurn(tm, players, 2, false, false, true);
            assertTurn(tm, players, 1, false, true, true);
            // player2 first
            assertTurn(tm, players, 2, true, false, false);
            assertTurn(tm, players, 3, false, false, false);
            assertTurn(tm, players, 0, false, false, false);
            assertTurn(tm, players, 1, false, false, false);
            assertTurn(tm, players, 1, false, false, true);
            assertTurn(tm, players, 0, false, false, true);
            assertTurn(tm, players, 3, false, false, true);
            assertTurn(tm, players, 2, false, true, true);
            // player3 first
            assertTurn(tm, players, 3, true, false, false);
            assertTurn(tm, players, 0, false, false, false);
            assertTurn(tm, players, 1, false, false, false);
            assertTurn(tm, players, 2, false, false, false);
            assertTurn(tm, players, 2, false, false, true);
            assertTurn(tm, players, 1, false, false, true);
            assertTurn(tm, players, 0, false, false, true);
            assertTurn(tm, players, 3, false, true, true);
        }
    }

    /**
     * Tests the 2-turns-in-a-row functionality used by one tool card.
     */
    @Test
    void testTwoTurns() {
        List<Player> players = getPlayerList(2, false);
        TurnManager tm = new ArrayTurnManager(players);
        assertEquals(players.get(0), tm.getCurrentPlayer());
        tm.takeTwoTurns();
        tm.advanceTurn();
        assertEquals(players.get(0), tm.getCurrentPlayer());
        assertThrows(NoSuchElementException.class, tm::takeTwoTurns);
        tm.advanceTurn();
        assertEquals(players.get(1), tm.getCurrentPlayer());
        tm.advanceTurn();
        assertEquals(players.get(1), tm.getCurrentPlayer());
        tm.advanceTurn();
        assertEquals(players.get(1), tm.getCurrentPlayer());
        tm.takeTwoTurns();
        tm.advanceTurn();
        assertEquals(players.get(1), tm.getCurrentPlayer());
        tm.advanceTurn();
        assertEquals(players.get(0), tm.getCurrentPlayer());
        tm.advanceTurn();
        assertEquals(players.get(0), tm.getCurrentPlayer());
    }

    /**
     * Tests JSON cloning correctness.
     */
    @Test
    void testJSON() {
        List<Player> players = getPlayerList(3, false);
        TurnManager tm = new ArrayTurnManager(players);
        TurnManager tmClone = JSONFactory.getTurnManager(tm.encode());
        for (int i = 0; i < Parameters.TOTAL_NUMBER_OF_ROUNDS * 3; i++) { // 30 total turns: it's like a 15-round game
            assertTurn(tmClone, players, players.indexOf(tm.getCurrentPlayer()), tm.isRoundStarting(), tm.isRoundEnding(), tm.isSecondTurn());
            tm.advanceTurn();
        }
        tm.takeTwoTurns();
        tmClone.takeTwoTurns();
        assertTurn(tmClone, players, players.indexOf(tm.getCurrentPlayer()), tm.isRoundStarting(), tm.isRoundEnding(), tm.isSecondTurn());
        tm.advanceTurn();
        assertTurn(tmClone, players, players.indexOf(tm.getCurrentPlayer()), tm.isRoundStarting(), tm.isRoundEnding(), tm.isSecondTurn());
        tm.advanceTurn();
        assertTurn(tmClone, players, players.indexOf(tm.getCurrentPlayer()), tm.isRoundStarting(), tm.isRoundEnding(), tm.isSecondTurn());
    }
}
