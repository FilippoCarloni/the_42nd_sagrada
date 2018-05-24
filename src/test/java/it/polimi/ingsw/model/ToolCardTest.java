package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.ToolCard;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolDeck;
import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.utility.Shade;
import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ToolCardTest {

    // TODO: correct the move command behavior to uncomment the tests

    private TestWrapper tw;

    @Test
    void testJSON() {
        init("Eglomise Brush");
        Deck d = new ToolDeck(tw.getGameStatus());
        DiceBag db = new ArrayDiceBag();
        WindowFrame w = (WindowFrame) new WindowFrameDeck().draw();
        for (int i = 0; i < Parameters.MAX_ROWS; i++)
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++)
                w.put(db.pick(), i, j);
        while (d.size() > 0) {
            ToolCard card = (ToolCard) d.draw();
            ToolCard cardClone = ToolCard.getCardFromJSON(card.encode(), tw.getGameStatus());
            assertEquals(card.getName(), cardClone.getName());
            assertEquals(card.getDescription(), cardClone.getDescription());
            assertEquals(card.getID(), cardClone.getID());
            assertEquals(card.encode(), cardClone.encode());
            assertEquals(card.getFavorPoints(), cardClone.getFavorPoints());
        }
    }

    private void init(String toolCard) {
        String[] names = new String[]{"foo", "baz"};
        String[] windows = new String[]{"Aurora Sagradis", "Aurorae Magnificus"};
        String[] toolNames = new String[]{toolCard};
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
        //assertEquals(3, tw.getGameStatus().getTurnManager().getCurrentPlayer().getFavorPoints());
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
        //assertEquals(1, tw.getGameStatus().getTurnManager().getCurrentPlayer().getFavorPoints());
        tw.wrappedFalseAssertion(0, "increase");
        tw.wrappedTrueAssertion(0, "decrease");
        assertEquals(Shade.DARKER, tw.getGameStatus().getStateHolder().getDieHolder().getShade());
    }

    /*@Test
    void eglomiseBrush() {
        init("Eglomise Brush");
        tw.wrappedSetDicePool("R2Y1");

        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedFalseAssertion(0, "place 1 3");
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
    }*/

    /*@Test
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
    }*/

    /*@Test
    void lathekin() {
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
        tw.wrappedFalseAssertion(0, "move 1 2 2 2");
        tw.wrappedTrueAssertion(0, "tool 1");
        tw.wrappedFalseAssertion(0, "move 1 3 4 1");
        tw.wrappedTrueAssertion(0, "move 1 2 2 2");
        tw.wrappedFalseAssertion(0, "pass");
        tw.wrappedFalseAssertion(0, "move 2 2 2 5");
        tw.wrappedTrueAssertion(0, "move 1 3 3 3");
        tw.wrappedTrueAssertion(0, "pass");
    }*/

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
        tw.wrappedTrueAssertion(1, "place 4 5 ");
        tw.wrappedTrueAssertion(1, "pass");
    }

    @Test
    void fluxBrush() {
        init("Flux Brush");
        tw.wrappedSetDicePool("R1");
        Die d = tw.getGameStatus().getDicePool().get(0);
        tw.wrappedTrueAssertion(0, "pick 1");
        assertTrue(tw.getGameStatus().getDicePool().isEmpty());
        assertEquals(d, tw.getGameStatus().getStateHolder().getDieHolder());
        tw.wrappedTrueAssertion(0, "tool 1");
        tw.wrappedTrueAssertion(0, "pass");
        assertEquals(d, tw.getGameStatus().getDicePool().get(0));
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedTrueAssertion(0, "tool 1");
        tw.wrappedFalseAssertion(0, "place 1 3");
        tw.wrappedTrueAssertion(0, "place 1 1");
        //assertEquals(1, tw.getGameStatus().getTurnManager().getCurrentPlayer().getFavorPoints());
        tw.wrappedTrueAssertion(0, "pass");
    }

    /*@Test
    void corkBackedStraightedge() {
        init("Cork-backed Straightedge");
        tw.wrappedSetDicePool("R1B2");
        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedFalseAssertion(0, "pick 1");
        tw.wrappedTrueAssertion(0, "place 1 2");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedFalseAssertion(0, "place 3 3");
        tw.wrappedTrueAssertion(0, "tool 1");
        tw.wrappedTrueAssertion(0, "place 3 3");
        tw.wrappedFalseAssertion(0, "place 1 3");
        assertEquals(Color.BLUE, tw.getGameStatus().getTurnManager().getCurrentPlayer().getWindowFrame().getDie(2, 2).getColor());
        assertEquals(Shade.LIGHTER, tw.getGameStatus().getTurnManager().getCurrentPlayer().getWindowFrame().getDie(2, 2).getShade());
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedSetDicePool("P1G3");
        tw.wrappedTrueAssertion(1, "pick 1");
        tw.wrappedTrueAssertion(1, "tool 1");
        tw.wrappedTrueAssertion(1, "place 4 5");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(1, "pick 1");
        tw.wrappedFalseAssertion(1, "place 1 2");
        tw.wrappedTrueAssertion(1, "tool 1");
        tw.wrappedTrueAssertion(1, "place 1 2");
        tw.wrappedFalseAssertion(1, "place 3 4");
        tw.wrappedTrueAssertion(1, "pass");
    }*/

    @Test
    void grindingStone() {
        init("Grinding Stone");
        tw.wrappedSetDicePool("R1G2B3");
        tw.wrappedFalseAssertion(0, "tool 1");
        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedTrueAssertion(0, "tool 1");
        assertEquals(Shade.DARKEST, tw.getGameStatus().getStateHolder().getDieHolder().getShade());
        tw.wrappedTrueAssertion(0, "place 1 2");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(1, "pick 1");
        tw.wrappedTrueAssertion(1, "tool 1");
        assertEquals(Shade.DARKER, tw.getGameStatus().getStateHolder().getDieHolder().getShade());
    }

    @Test
    void glazingHammer() {
        init("Glazing Hammer");
        tw.wrappedSetDicePool("R1B2Y3G4P5");
        tw.wrappedFalseAssertion(0, "tool 1");
        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedFalseAssertion(0, "tool 1");
        tw.wrappedTrueAssertion(0, "place 1 1");
        tw.wrappedFalseAssertion(0, "tool 1");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(1, "tool 1");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedTrueAssertion(0, "tool 1");
    }

    @Test
    void runningPliers() {
        init("Running Pliers");
        tw.wrappedTrueAssertion(0, "tool 1");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedFalseAssertion(1, "pass");
        tw.wrappedFalseAssertion(0, "tool 1");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(1, "pass");
    }

    /*@Test
    void fluxRemover() {
        init("Flux Remover");
        tw.wrappedSetDicePool("R1B3Y3G4P5");
        tw.wrappedFalseAssertion(0, "tool 1");
        tw.wrappedTrueAssertion(0, "pick 1");
        Die d = tw.getGameStatus().getStateHolder().getDieHolder();
        tw.wrappedTrueAssertion(0, "tool 1");
        assertNotEquals(null, tw.getGameStatus().getStateHolder().getDieHolder());
        assertNotEquals(d, tw.getGameStatus().getStateHolder().getDieHolder());
        tw.wrappedFalseAssertion(0, "pass");
        tw.wrappedFalseAssertion(0, "place 1 2");
        tw.wrappedFalseAssertion(0, "select 0");
        tw.wrappedFalseAssertion(0, "select 7");
        tw.wrappedTrueAssertion(0, "select 2");
        assertEquals(Shade.LIGHTER, tw.getGameStatus().getStateHolder().getDieHolder().getShade());
        tw.wrappedFalseAssertion(0, "select 3");
        tw.wrappedTrueAssertion(0, "place 1 2");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(1, "pick 1");
        tw.wrappedTrueAssertion(1, "tool 1");
        tw.wrappedTrueAssertion(1, "select 4");
        tw.wrappedTrueAssertion(1, "pass");
        assertEquals(4, tw.getGameStatus().getDicePool().size());
        long count = tw.getGameStatus().getDicePool().stream().filter(die -> die.getShade().getValue() == 4).count();
        assertTrue(count >= 1);
    }*/

    /*@Test
    void tapWheel1() {
        init("Tap Wheel");
        tw.wrappedSetDicePool("R1B4Y3R2B5");
        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedTrueAssertion(0, "place 1 1");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedTrueAssertion(0, "place 1 2");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(0, "tool 1");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(0, "tool 1");
        tw.wrappedTrueAssertion(0, "move 1 2 2 1");
        tw.wrappedFalseAssertion(0, "move 1 1 3 1");
        tw.wrappedTrueAssertion(0, "move 2 1 1 2");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(1, "pass");
    }*/

    /*@Test
    void tapWheel2() {
        init("Tap Wheel");
        tw.wrappedSetDicePool("R3B4Y3P2B5");
        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedTrueAssertion(0, "place 1 1");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(0, "pick 1");
        tw.wrappedTrueAssertion(0, "place 1 2");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(1, "pass");
        tw.wrappedTrueAssertion(0, "tool 1");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(0, "tool 1");
        tw.wrappedFalseAssertion(0, "move 1 1 2 3");
        tw.wrappedTrueAssertion(0, "move 1 2 2 1");
        tw.wrappedTrueAssertion(0, "move 2 1 1 2");
        tw.wrappedTrueAssertion(0, "pass");
        tw.wrappedTrueAssertion(1, "pass");
    }*/
}
