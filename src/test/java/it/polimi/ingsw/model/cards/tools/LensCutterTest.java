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
    void test() {
        Game g = init("gen_2p_02");
        List<Player> players = g.getData().getPlayers();
        int index = (int) (Math.random() * g.getData().getRoundTrack().getDice().size()) + 1;
        wrappedIllegalCommand(g, players.get(0), "tool 2");
        wrappedIllegalCommand(g, players.get(0), "tool 5");
        wrappedLegalCommand(g, players.get(0), "pick 1");
        Die playerDie = g.getData().getPickedDie();
        Die roundTrackDie = g.getData().getRoundTrack().getDice().get(index - 1);
        wrappedLegalCommand(g, players.get(0), "tool 5");
        wrappedIllegalCommand(g, players.get(0), "place 1 2");
        wrappedIllegalCommand(g, players.get(0), "select 0");
        wrappedIllegalCommand(g, players.get(0), "select " + (g.getData().getRoundTrack().getDice().size() + 1));
        wrappedLegalCommand(g, players.get(0), "select " + index);
        assertEquals(playerDie, g.getData().getRoundTrack().getDice().get(index - 1));
        assertEquals(roundTrackDie, g.getData().getPickedDie());
        assertEquals(0, g.getData().getActiveToolID());
        assertEquals(0, g.getData().getPassiveToolID());
    }
}
