package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

class CommandTest {

    // TODO: make this more elegant

    private TestWrapper tw;

    private void init() {
        String[] names = new String[] {"foo", "baz"};
        String[] windows = new String[] {"Aurora Sagradis", "Aurorae Magnificus"};
        String[] toolNames = new String[] {"Eglomise Brush", "Grinding Stone", "Copper Foil Burnisher"};
        tw = new TestWrapper(names, windows, toolNames);
    }

    @Test
    void commandManagerTest() {
        init();
        tw.wrappedSetDicePool("R3B1Y2G6G5");

        // ROUND 1
        // -------

        // turn 1
        tw.wrappedFalseAssertion(1, "pick 1");
        tw.wrappedFalseAssertion(0, "pick");
        tw.wrappedFalseAssertion(0, "pic");
        tw.wrappedFalseAssertion(0, "pick 0");
        tw.wrappedFalseAssertion(0, "pick 6");
        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedFalseAssertion(0, "pass");
        tw.wrappedFalseAssertion(0, "place 5 5");
        tw.wrappedFalseAssertion(0, "place 3 3");
        tw.wrappedTrueAssertion(0, "place 1 1");
        tw.wrappedTrueAssertion(0, "pass");
        // turn 2
        tw.wrappedFalseAssertion(0, "pass");
        tw.wrappedTrueAssertion(1, "pick 4");
        tw.wrappedFalseAssertion(1, "place 0 0");
        tw.wrappedTrueAssertion(1, "place 4 4");
        tw.wrappedTrueAssertion(1, "pass");
        // turn 3
        tw.wrappedTrueAssertion(1, "pick 1");
        tw.wrappedFalseAssertion(1, "place 4 1");
        tw.wrappedTrueAssertion(1, "place 4 3");
        tw.wrappedTrueAssertion(1, "tool 3");
        tw.wrappedFalseAssertion(1, "tool 1");
        tw.wrappedFalseAssertion(1, "move");
        tw.wrappedFalseAssertion(1, "move 0 1 1 1");
        tw.wrappedFalseAssertion(1, "move 5 5 1 1");
        tw.wrappedFalseAssertion(1, "move 1");
        tw.wrappedFalseAssertion(1, "move 4  3 3 3");
        tw.wrappedTrueAssertion(1, "move 4 3 3 3");
        tw.wrappedFalseAssertion(1, "tool 1");
        tw.wrappedTrueAssertion(1, "pass");

        System.out.println(tw.getGameStatus());
    }
}
