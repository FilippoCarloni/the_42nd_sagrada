package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class CommandManagerTest {

    private List<Player> init() {
        Player foo = new ConcretePlayer("foo");
        Player baz = new ConcretePlayer("baz");
        Deck d;
        d = new WindowFrameDeck();
        WindowFrame w1 = (WindowFrame) d.draw();
        while (!w1.getName().equals("Aurora Sagradis")) w1 = (WindowFrame) d.draw();
        d = new WindowFrameDeck();
        WindowFrame w2 = (WindowFrame) d.draw();
        while (!w2.getName().equals("Aurorae Magnificus")) w2 = (WindowFrame) d.draw();
        foo.setWindowFrame(w1);
        baz.setWindowFrame(w2);
        List<Player> players = new ArrayList<>();
        players.add(foo);
        players.add(baz);
        return players;
    }

    @Test
    void commandManagerTest() {
        List<Player> players = init();
        ConcreteGameStatus gs = new ConcreteGameStatus(players);
        assertFalse(gs.isLegal(players.get(1), "pick 1"));
        assertTrue(gs.isLegal(players.get(0), "pick 1"));
        assertFalse(gs.isLegal(players.get(0), "hello world"));
        assertFalse(gs.isLegal(players.get(0), "place 1 1"));
        assertTrue(gs.isLegal(players.get(0), "pass"));
        gs.execute(players.get(0), "pick 1");
        gs.execute(players.get(1), "pass");
        gs.execute(players.get(0), "place 3 5");
        gs.execute(players.get(0), "pass");
        gs.execute(players.get(1), "pick 1");
        gs.execute(players.get(1), "place 4 5");
        gs.execute(players.get(1), "pass");
        gs.execute(players.get(1), "pick 1");
        gs.execute(players.get(1), "place 3 4");
        gs.execute(players.get(1), "pass");
        gs.execute(players.get(0), "pick 1");
        gs.execute(players.get(0), "place 4 4");
        gs.execute(players.get(0), "pass");
        assertTrue(gs.getDicePool().size() == 5);
    }
}
