package it.polimi.ingsw.model.gamedata;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.JSONFactory;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.model.utility.Parameters;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameDataTest {

    private List<Player> initializePlayers(int numOfPlayers) {
        List<Player> players = new ArrayList<>();
        Deck po = new PrivateObjectiveDeck();
        Deck wf = new WindowFrameDeck();
        for (int i = 0; i < numOfPlayers; i++) {
            Player p = new ConcretePlayer("player" + i);
            p.setWindowFrame((WindowFrame) wf.draw());
            p.setPrivateObjective((PrivateObjectiveCard) po.draw());
            players.add(p);
        }
        return players;
    }

    @Test
    void testPlayerCorrectness() {
        List<Player> players = new ArrayList<>();
        players.add(new ConcretePlayer("foo"));
        assertThrows(IllegalArgumentException.class, () -> new ConcreteGame(players));
        players.add(new ConcretePlayer("baz"));
        assertThrows(IllegalArgumentException.class, () -> new ConcreteGame(players));
        players.add(new ConcretePlayer("foo"));
        assertThrows(IllegalArgumentException.class, () -> new ConcreteGame(players));
    }

    @Test
    void testJSON() {
        int numOfPlayers = (int) (Math.random() * 3 + 2);
        List<Player> players = initializePlayers(numOfPlayers);
        GameData data = new ConcreteGameData(players);
        try {
            GameData dataClone = JSONFactory.getGameData((JSONObject) new JSONParser().parse(data.encode().toString()));
            assertTrue(data.getRoundTrack().getDice().containsAll(dataClone.getRoundTrack().getDice()));
            assertTrue(data.getRoundTrack().getVisibleDice().containsAll(dataClone.getRoundTrack().getVisibleDice()));
            assertEquals(data.getTurnManager().getCurrentPlayer(), dataClone.getTurnManager().getCurrentPlayer());
            assertTrue(data.getTurnManager().getPlayers().containsAll(dataClone.getTurnManager().getPlayers()));
            assertEquals(numOfPlayers * 2 + 1, data.getDicePool().size());
            assertTrue(data.getDicePool().containsAll(dataClone.getDicePool()));
            List<Player> originalPlayers = data.getTurnManager().getPlayers();
            List<Player> clonedPlayers = dataClone.getTurnManager().getPlayers();
            for (int i = 0; i < originalPlayers.size(); i++) {
                WindowFrame frame = originalPlayers.get(i).getWindowFrame();
                WindowFrame clonedFrame = clonedPlayers.get(i).getWindowFrame();
                for (int j = 0; j < Parameters.MAX_ROWS; j++) {
                    for (int k = 0; k < Parameters.MAX_COLUMNS; k++) {
                        assertEquals(frame.getDie(j, k), clonedFrame.getDie(j, k));
                        assertEquals(frame.getColorConstraint(j, k), clonedFrame.getColorConstraint(j, k));
                        assertEquals(frame.getShadeConstraint(j, k), clonedFrame.getShadeConstraint(j, k));
                    }
                }
            }
            for (int i : data.getCurrentScore().values())
                assertTrue(i == 0);
            for (int i : dataClone.getCurrentScore().values())
                assertTrue(i == 0);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Bad JSON file.");
        }
    }

    @Test
    void testJSONCalledByPlayer() {
        List<Player> players = initializePlayers((int) (Math.random() * 3 + 2));
        Game g = new ConcreteGame(players);
        int index = (int) (Math.random() * players.size());
        JSONObject encodedData = g.getData(players.get(index));
        assertTrue(g.getData().encode().containsKey(JSONTag.DICE_POOL));
        assertFalse(encodedData.containsKey(JSONTag.DICE_BAG));
        JSONObject turnManager = (JSONObject) encodedData.get(JSONTag.TURN_MANAGER);
        JSONArray encodedPlayers = (JSONArray) turnManager.get(JSONTag.PLAYERS);
        for (Object o : encodedPlayers) {
            JSONObject encodedPlayer = (JSONObject) o;
            if (encodedPlayer.get(JSONTag.USERNAME).equals(players.get(index).getUsername()))
                assertTrue(encodedPlayer.containsKey(JSONTag.PRIVATE_OBJECTIVE));
            else
                assertFalse(encodedPlayer.containsKey(JSONTag.PRIVATE_OBJECTIVE));
        }
    }
}
