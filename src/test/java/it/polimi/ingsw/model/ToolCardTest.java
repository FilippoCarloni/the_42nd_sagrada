package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolDeck;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.utility.Color;
import it.polimi.ingsw.model.gameboard.utility.Shade;
import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToolCardTest {

    private TestWrapper tw;

    @Test
    void printDeck() {
        List<Player> players = new ArrayList<>();
        players.add(new ConcretePlayer("foo"));
        players.add(new ConcretePlayer("baz"));
        Deck d = new ToolDeck(new ConcreteGameStatus(players));
        while (d.size() > 0)
            System.out.println(d.draw());
    }

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
        assertEquals(4, tw.getGameStatus().getTurnManager().getCurrentPlayer().getFavorPoints());
        tw.wrappedTrueAssertion(0, "tool 1");
        assertEquals(3, tw.getGameStatus().getTurnManager().getCurrentPlayer().getFavorPoints());
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
        assertEquals(1, tw.getGameStatus().getTurnManager().getCurrentPlayer().getFavorPoints());
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

    @Test
    void lathekin() {

        // TODO: pay attention to double die movement: you can move twice the SAME die

        init("Lathekin");
        tw.wrappedSetDicePool("B1P2");
        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedTrueAssertion(0, "place 1 3");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedTrueAssertion(0, "place 1 2");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedSetDicePool("Y3G4");
        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedTrueAssertion(0, "place 1 4");
        tw.wrappedTrueAssertion(0, "tool 1");
        tw.wrappedFalseAssertion(0, "move 1 3 4 1");
        tw.wrappedTrueAssertion(0, "move 1 2 2 2");
        tw.wrappedFalseAssertion(0, "pass");
        tw.wrappedTrueAssertion(0, "move 2 2 2 5");
        tw.wrappedTrueAssertion(0, "pass");
    }

    @Test
    void lensCutter() {
        init("Lens Cutter");
        tw.wrappedSetDicePool("R1B2Y3P4G5");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedSetDicePool("P6");
        tw.wrappedTrueAssertion(1, "pick 1");
        assertEquals(Color.PURPLE, tw.getGameStatus().getStateHolder().getDieHolder().getColor());
        assertEquals(Shade.DARKEST, tw.getGameStatus().getStateHolder().getDieHolder().getShade());
        tw.wrappedTrueAssertion(1, "tool 1");
        assertEquals(5, tw.getGameStatus().getStateHolder().getActiveToolID());
        tw.wrappedFalseAssertion(1, "select 0");
        tw.wrappedFalseAssertion(1, "select 6");
        Die d = tw.getGameStatus().getStateHolder().getDieHolder();
        tw.wrappedTrueAssertion(1, "select 1");
        assertEquals(Color.RED, tw.getGameStatus().getStateHolder().getDieHolder().getColor());
        assertEquals(Shade.LIGHTEST, tw.getGameStatus().getStateHolder().getDieHolder().getShade());
    }
}