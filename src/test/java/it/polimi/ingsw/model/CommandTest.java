package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class CommandTest {

    private String[] names = {"foo", "baz"};
    private String[] windows = {"Aurora Sagradis", "Aurorae Magnificus"};
    private String[] toolNames = {"Eglomise Brush", "Grinding Stone", "Copper Foil Burnisher"};
    private ConcreteGameStatus gs = new ConcreteGameStatus(names, windows, toolNames);

    private void wrappedTrueAssertion(int playerNumber, String cmd) {
        assertTrue(gs.isLegal(gs.getPlayers().get(playerNumber), cmd));
        gs.execute(gs.getPlayers().get(playerNumber), cmd);
    }

    private void wrappedFalseAssertion(int playerNumber, String cmd) {
        assertFalse(gs.isLegal(gs.getPlayers().get(playerNumber), cmd));
        gs.execute(gs.getPlayers().get(playerNumber), cmd);
    }

    private void wrappedSetDicePool(String dicePool) {
        gs.setDicePool(dicePool);
        assertEquals(dicePool.length() / 2, gs.getDicePool().size());
    }

    @Test
    void commandManagerTest() {
        wrappedSetDicePool("R3B1Y2G6G5");

        // ROUND 1
        // -------

        // turn 1
        wrappedFalseAssertion(1, "pick 1");
        wrappedFalseAssertion(0, "pick");
        wrappedFalseAssertion(0, "pic");
        wrappedFalseAssertion(0, "pick 0");
        wrappedFalseAssertion(0, "pick 6");
        wrappedTrueAssertion(0, "pick 1");
        wrappedFalseAssertion(0, "pass");
        wrappedFalseAssertion(0, "place 5 5");
        wrappedFalseAssertion(0, "place 3 3");
        wrappedTrueAssertion(0, "place 1 1");
        wrappedTrueAssertion(0, "pass");
        // turn 2
        wrappedFalseAssertion(0, "pass");
        wrappedTrueAssertion(1, "pick 4");
        wrappedFalseAssertion(1, "place 0 0");
        wrappedTrueAssertion(1, "place 4 4");
        wrappedTrueAssertion(1, "pass");
        // turn 3
        wrappedTrueAssertion(1, "pick 1");
        wrappedFalseAssertion(1, "place 4 1");
        wrappedTrueAssertion(1, "place 4 3");
        wrappedTrueAssertion(1, "tool 3");
        wrappedFalseAssertion(1, "tool 1");
        wrappedFalseAssertion(1, "move");
        wrappedFalseAssertion(1, "move 0 1 1 1");
        wrappedFalseAssertion(1, "move 5 5 1 1");
        wrappedFalseAssertion(1, "move 1");
        wrappedFalseAssertion(1, "move 4  3 3 3");
        wrappedTrueAssertion(1, "move 4 3 3 3");
        wrappedFalseAssertion(1, "tool 1");
        wrappedTrueAssertion(1, "pass");

        System.out.println(gs);
    }
}
