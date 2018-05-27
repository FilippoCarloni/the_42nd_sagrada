package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import it.polimi.ingsw.model.gamedata.ConcreteGame;
import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Shade;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.init;
import static it.polimi.ingsw.model.TestHelper.wrappedIllegalCommand;
import static it.polimi.ingsw.model.TestHelper.wrappedLegalCommand;

class PlaceTest {

    /**
     * Testing generic functionality of place command
     */
    @Test
    void placeTest() {
        Deck d = new WindowFrameDeck();
        WindowFrame w = (WindowFrame) d.draw();
        while (!w.getName().equals("Batllo"))
            w = (WindowFrame) d.draw();
        List<Player> players = init(2);
        players.get(0).setWindowFrame(w);
        Game g = new ConcreteGame(players);
        wrappedIllegalCommand(g, players.get(0), "place 1 1");
        wrappedLegalCommand(g, players.get(0), "pick 1");
        wrappedIllegalCommand(g, players.get(0), "place 1 6");
        wrappedLegalCommand(g, players.get(0), "place 1 1");
        g.undoCommand();
        wrappedLegalCommand(g, players.get(0), "place 1 1");
        wrappedIllegalCommand(g, players.get(0), "place 1 1");
        wrappedIllegalCommand(g, players.get(0), "pick 1");
    }

    /**
     * Testing placing rules compliant behaviour of place command
     */
    @Test
    void ruleTest() {
        Deck d = new WindowFrameDeck();
        WindowFrame w = (WindowFrame) d.draw();
        while (!w.getName().equals("Gravitas"))
            w = (WindowFrame) d.draw();
        List<Player> players = init(2);
        players.get(0).setWindowFrame(w);
        Game g = new ConcreteGame(players);
        wrappedLegalCommand(g, players.get(0), "pick 1");
        g.getData().getPickedDie().setColor(Color.BLUE);
        g.getData().getPickedDie().setShade(Shade.LIGHTEST);
        System.out.println(g.getData());
        wrappedLegalCommand(g, players.get(0), "place 1 1");
        g.undoCommand();
        wrappedLegalCommand(g, players.get(0), "place 1 4");
        g.undoCommand();
        wrappedLegalCommand(g, players.get(0), "place 4 1");
        g.undoCommand();
        wrappedIllegalCommand(g, players.get(0), "place 3 1");
        wrappedIllegalCommand(g, players.get(0), "place 3 3");
        g.getData().getPickedDie().setColor(Color.RED);
        wrappedIllegalCommand(g, players.get(0), "place 4 1");
    }
}
