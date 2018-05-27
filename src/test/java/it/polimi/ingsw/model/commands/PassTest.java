package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import it.polimi.ingsw.model.gamedata.ConcreteGame;
import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.*;

class PassTest {

    /**
     * Pass is not allowed if you picked a die.
     * Pass advances the status of the game.
     */
    @Test
    void passTest() {
        Deck d = new WindowFrameDeck();
        WindowFrame w = (WindowFrame) d.draw();
        while (!w.getName().equals("Batllo"))
            w = (WindowFrame) d.draw();
        List<Player> players = init(2);
        players.get(0).setWindowFrame(w);
        players.get(1).setWindowFrame(w);
        Game g = new ConcreteGame(players);
        wrappedLegalCommand(g, players.get(0), "pick 1");
        wrappedIllegalCommand(g, players.get(0), "pass");
        wrappedLegalCommand(g, players.get(0), "place 1 1");
        wrappedPass(g, players.get(0));
        wrappedIllegalCommand(g, players.get(0), "pick 1");
        wrappedLegalCommand(g, players.get(1), "pick 1");
        wrappedLegalCommand(g, players.get(1), "place 1 1");
        g.getData().setActiveToolID(2);
        wrappedIllegalCommand(g, players.get(1), "pass");
        wrappedIllegalCommand(g, players.get(1), "pick 1");

        // everything can be undone
        for (int i = 0; i < 100; i++)
            g.undoCommand();
        wrappedLegalCommand(g, players.get(0), "pick 1");
        wrappedIllegalCommand(g, players.get(0), "pass");
        wrappedLegalCommand(g, players.get(0), "place 1 1");
        wrappedPass(g, players.get(0));
        wrappedIllegalCommand(g, players.get(0), "pick 1");
        wrappedLegalCommand(g, players.get(1), "pick 1");
        wrappedLegalCommand(g, players.get(1), "place 1 1");
        g.getData().setActiveToolID(2);
        wrappedIllegalCommand(g, players.get(1), "pass");
        wrappedIllegalCommand(g, players.get(1), "pick 1");
    }
}
