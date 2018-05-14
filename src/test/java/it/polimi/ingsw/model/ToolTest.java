package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolDeck;
import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ToolTest {

    @Test
    void printCards() {
        List<Player> players = new ArrayList<>();
        players.add(new ConcretePlayer("foo"));
        players.add(new ConcretePlayer("baz"));
        Deck d = new ToolDeck(new ConcreteGameStatus(players));
        while (d.size() > 0)
            System.out.println(d.draw());
    }
}
