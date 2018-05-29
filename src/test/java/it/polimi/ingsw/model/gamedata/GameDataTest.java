package it.polimi.ingsw.model.gamedata;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.JSONFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameDataTest {

    @Test
    void testJSON() {

        // TODO: test more deeply when the implementation is stable

        int numPlayers = 3;
        List<Player> players = new ArrayList<>();
        Deck po = new PrivateObjectiveDeck();
        Deck wf = new WindowFrameDeck();
        for (int i = 0; i < numPlayers; i++) {
            Player p = new ConcretePlayer("player" + i);
            p.setWindowFrame((WindowFrame) wf.draw());
            p.setPrivateObjective((PrivateObjectiveCard) po.draw());
            players.add(p);
        }
        GameData data = new ConcreteGameData(players);
        try {
            GameData dataClone = JSONFactory.getGameData((JSONObject) new JSONParser().parse(data.encode().toString()));
            assertTrue(data.getRoundTrack().getDice().containsAll(dataClone.getRoundTrack().getDice()));
            assertTrue(data.getRoundTrack().getVisibleDice().containsAll(dataClone.getRoundTrack().getVisibleDice()));
            assertEquals(data.getTurnManager().getCurrentPlayer(), dataClone.getTurnManager().getCurrentPlayer());
            assertEquals(7, data.getDicePool().size());
            assertTrue(data.getDicePool().containsAll(dataClone.getDicePool()));
            for (int i : data.getCurrentScore().values())
                assertTrue(i == 0);
            for (int i : dataClone.getCurrentScore().values())
                assertTrue(i == 0);
            System.out.println(dataClone);

        } catch (ParseException e) {
            assertTrue(false);
        }
    }
}
