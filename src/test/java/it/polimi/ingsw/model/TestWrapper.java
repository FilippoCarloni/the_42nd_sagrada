package it.polimi.ingsw.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class TestWrapper {

    private ConcreteGameStatus gs;

    TestWrapper(String[] names, String[] windows, String[] toolNames) {
        gs = new ConcreteGameStatus(names, windows, toolNames);
    }

    void wrappedTrueAssertion(int playerNumber, String cmd) {
        assertTrue(gs.isLegal(gs.getPlayers().get(playerNumber), cmd));
        gs.execute(gs.getPlayers().get(playerNumber), cmd);
    }

    void wrappedFalseAssertion(int playerNumber, String cmd) {
        assertFalse(gs.isLegal(gs.getPlayers().get(playerNumber), cmd));
        gs.execute(gs.getPlayers().get(playerNumber), cmd);
    }

    void wrappedSetDicePool(String dicePool) {
        gs.setDicePool(dicePool);
        assertEquals(dicePool.length() / 2, gs.getDicePool().size());
    }

    ConcreteGameStatus getGameStatus() {
        return gs;
    }
}