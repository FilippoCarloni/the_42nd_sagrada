package it.polimi.ingsw.model.cards.tools;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.model.TestHelper.init;
import static it.polimi.ingsw.model.TestHelper.wrappedIllegalCommand;
import static it.polimi.ingsw.model.TestHelper.wrappedLegalCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LensCutterTest {

    @Test
    void lensCutterTest() {
        int index = (int) (Math.random() * 5);
        Game g = init("gen_2p_01");
        List<Player> players = g.getData().getPlayers();
        wrappedLegalCommand(g, players.get(0), "pass");
        wrappedLegalCommand(g, players.get(1), "pass");
        wrappedLegalCommand(g, players.get(1), "pass");
        wrappedLegalCommand(g, players.get(0), "pass");
        wrappedIllegalCommand(g, players.get(1), "tool 1");
        wrappedLegalCommand(g, players.get(1), "pick 1");
        Die playerDie = g.getData().getPickedDie();
        Die roundTrackDie = g.getData().getRoundTrack().getDice().get(index);
        wrappedLegalCommand(g, players.get(1), "tool 1");
        wrappedIllegalCommand(g, players.get(1), "select 0");
        wrappedIllegalCommand(g, players.get(1), "select 6");
        wrappedLegalCommand(g, players.get(1), "select " + (index + 1));
        assertEquals(playerDie, g.getData().getRoundTrack().getDice().get(index));
        assertEquals(roundTrackDie, g.getData().getPickedDie());
        g.undoCommand();
        g.undoCommand();
        g.undoCommand();
        wrappedLegalCommand(g, players.get(1), "pass");
        wrappedLegalCommand(g, players.get(0), "pass");
        wrappedLegalCommand(g, players.get(0), "pass");
        wrappedLegalCommand(g, players.get(1), "pass");
        wrappedLegalCommand(g, players.get(0), "pass");
        wrappedLegalCommand(g, players.get(1), "pass");
        wrappedLegalCommand(g, players.get(1), "pass");
        wrappedLegalCommand(g, players.get(0), "pass");

        // check on indexed greater than 9
        index = (int) (Math.random() * 15);
        System.out.println(index);
        wrappedLegalCommand(g, players.get(1), "pick 1");
        playerDie = g.getData().getPickedDie();
        roundTrackDie = g.getData().getRoundTrack().getDice().get(index);
        wrappedLegalCommand(g, players.get(1), "tool 1");
        wrappedIllegalCommand(g, players.get(1), "select 0");
        wrappedIllegalCommand(g, players.get(1), "select 16");
        wrappedLegalCommand(g, players.get(1), "select " + (index + 1));
        assertEquals(playerDie, g.getData().getRoundTrack().getDice().get(index));
        assertEquals(roundTrackDie, g.getData().getPickedDie());
    }
}
