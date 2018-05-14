package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.utility.Shade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToolCardTest {

    private TestWrapper tw;

    private void init(String toolCard) {
        String[] names = new String[] {"foo", "baz"};
        String[] windows = new String[] {"Aurora Sagradis", "Aurorae Magnificus"};
        String[] toolNames = new String[] {toolCard};
        tw = new TestWrapper(names, windows, toolNames);
    }

    @Test
    void grozingPliersTest() {
        init("Grozing Pliers");
        tw.wrappedSetDicePool("R1R6");

        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedFalseAssertion(0, "tool 2");
        tw.wrappedTrueAssertion(0, "tool 1");
        tw.wrappedFalseAssertion(0, "tool 1");
        tw.wrappedFalseAssertion(0, "move 1 1 2 2");
        tw.wrappedFalseAssertion(0, "place 4 1");
        tw.wrappedFalseAssertion(0, "decrease");
        tw.wrappedTrueAssertion(0, "increase");
        assertEquals(Shade.LIGHTER, tw.getGameStatus().getStateHolder().getDieHolder().getShade());

        tw.wrappedTrueAssertion(0, "place 4 1");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedTrueAssertion(0, "tool 1");
        tw.wrappedFalseAssertion(0, "increase");
        tw.wrappedTrueAssertion(0, "decrease");
        assertEquals(Shade.DARKER, tw.getGameStatus().getStateHolder().getDieHolder().getShade());
    }

    @Test
    void eglomiseBrush() {
        init("Eglomise Brush");
        tw.wrappedSetDicePool("R2Y1");

        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedTrueAssertion(0, "place 3 1");
        tw.wrappedTrueAssertion(0, "tool 1");
        tw.wrappedFalseAssertion(0, "increase");
        tw.wrappedFalseAssertion(0, "move 3 1  3 1");
        tw.wrappedFalseAssertion(0, "move 3 1 4 3");
        tw.wrappedFalseAssertion(0, "move 3 1 3 3");
        tw.wrappedTrueAssertion(0, "move 3 1 1 3");
        tw.wrappedTrueAssertion(0, "pass");

        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(1, "pass");

        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedTrueAssertion(0, "place 1 2");
        tw.wrappedTrueAssertion(0, "pass");

        tw.wrappedSetDicePool("R3");

        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedTrueAssertion(0, "place 1 1");
        tw.wrappedTrueAssertion(0, "tool 1");
        tw.wrappedTrueAssertion(0, "move 1 1 1 4");
    }

    @Test
    void copperFoilBurnisher() {
        init("Copper Foil Burnisher");
        tw.wrappedSetDicePool("R2G2");

        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedTrueAssertion(0, "place 1 2");
        tw.wrappedTrueAssertion(0, "tool 1");
        tw.wrappedFalseAssertion(0, "decrease");
        tw.wrappedFalseAssertion(0, "move  1 2 3 1");
        tw.wrappedFalseAssertion(0, "move 1 2 1 3");
        tw.wrappedFalseAssertion(0, "move 1 2 3 3");
        tw.wrappedTrueAssertion(0, "move 1 2 2 1");
        tw.wrappedTrueAssertion(0, "pass");

        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(1, "pass");

        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedTrueAssertion(0, "place 1 2");
        tw.wrappedTrueAssertion(0, "tool 1");
        tw.wrappedTrueAssertion(0, "move 1 2 3 1");

        assertEquals(3, tw.getGameStatus().getTools().get(0).getFavorPoints());
        assertEquals(1, tw.getGameStatus().getTurnManager().getCurrentPlayer().getFavorPoints());
    }
}
