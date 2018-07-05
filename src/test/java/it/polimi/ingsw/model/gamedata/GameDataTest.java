package it.polimi.ingsw.model.gamedata;

import it.polimi.ingsw.model.gameboard.cards.Card;
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
import java.util.Set;
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.TestHelper.areWindowFramesEqual;
import static it.polimi.ingsw.model.TestHelper.getPlayerList;
import static it.polimi.ingsw.model.utility.ExceptionMessage.BAD_JSON;
import static org.junit.jupiter.api.Assertions.*;

class GameDataTest {

    /**
     * Checks the constructor initialization: players should be > 1 and <= 4 and everyone must have a window frame and a private objective.
     */
    @Test
    void testPlayerCorrectness() {
        assertThrows(IllegalArgumentException.class, () -> new ConcreteGame(new ArrayList<>()));
        assertThrows(IllegalArgumentException.class, () -> new ConcreteGame(getPlayerList(1, true)));
        assertThrows(IllegalArgumentException.class, () -> new ConcreteGame(getPlayerList(2, false)));
        assertThrows(IllegalArgumentException.class, () -> new ConcreteGame(getPlayerList(5, true)));
        List<Player> players = new ArrayList<>();
        Player p1 = new ConcretePlayer("foo");
        Player p2 = new ConcretePlayer("foo");
        players.add(p1);
        players.add(p2);
        p1.setWindowFrame((WindowFrame) new WindowFrameDeck().draw());
        p1.setPrivateObjective((PrivateObjectiveCard) new PrivateObjectiveDeck().draw());
        p2.setWindowFrame((WindowFrame) new WindowFrameDeck().draw());
        assertThrows(IllegalArgumentException.class, () -> new ConcreteGame(players));
        p2.setPrivateObjective((PrivateObjectiveCard) new PrivateObjectiveDeck().draw());
        assertThrows(IllegalArgumentException.class, () -> new ConcreteGame(players));
        // deeper check is needed, because there's no valid constructor for ConcreteGame(null)
        assertThrows(NullPointerException.class, () -> GameDataFactory.getTurnManager(null));
    }

    /**
     * Tests the correct cloning behavior.
     */
    @Test
    void testJSON() {
        int numOfPlayers = (int) (Math.random() * 3 + 2);
        List<Player> players = getPlayerList(numOfPlayers, true);
        GameData data = new ConcreteGameData(players);
        try {
            GameData dataClone = JSONFactory.getGameData((JSONObject) new JSONParser().parse(data.encode().toString()));
            assertTrue(data.getRoundTrack().getDice().containsAll(dataClone.getRoundTrack().getDice()));
            assertTrue(data.getRoundTrack().getVisibleDice().containsAll(dataClone.getRoundTrack().getVisibleDice()));
            assertEquals(data.getTurnManager().getCurrentPlayer(), dataClone.getTurnManager().getCurrentPlayer());
            assertTrue(data.getTurnManager().getPlayers().containsAll(dataClone.getTurnManager().getPlayers()));
            assertTrue(data.getPublicObjectives().containsAll(dataClone.getPublicObjectives()));
            assertEquals(numOfPlayers * 2 + 1, data.getDicePool().size());
            assertTrue(data.getDicePool().containsAll(dataClone.getDicePool()));
            List<Player> originalPlayers = data.getTurnManager().getPlayers();
            List<Player> clonedPlayers = dataClone.getTurnManager().getPlayers();
            for (int i = 0; i < originalPlayers.size(); i++) {
                if (originalPlayers.get(i).getPrivateObjective() == null) {
                    assertNull(originalPlayers.get(i).getPrivateObjective());
                    assertNull(clonedPlayers.get(i).getPrivateObjective());
                } else assertEquals(originalPlayers.get(i).getPrivateObjective().getID(), clonedPlayers.get(i).getPrivateObjective().getID());
                areWindowFramesEqual(originalPlayers.get(i).getWindowFrame(), clonedPlayers.get(i).getWindowFrame());
            }
            assertEquals(data.getCurrentScore().toString(), dataClone.getCurrentScore().toString());
        } catch (ParseException e) {
            throw new IllegalArgumentException(BAD_JSON);
        }
    }

    /**
     * Every player should receive a custom JSON that encodes the game data:
     * the dice bag is not present and only the player that made the request has the private object encoded.
     */
    @Test
    void testJSONCalledByPlayer() {
        List<Player> players = getPlayerList((int) (Math.random() * 3 + 2), true);
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
        assertThrows(IllegalArgumentException.class, () -> g.getData(new ConcretePlayer("AnotherPlayer")));
    }

    /**
     * Tests the picking methods called at the start of the game to initialize players'maps.
     */
    @Test
    void testPicking() {
        assertThrows(IllegalArgumentException.class, () -> Game.getWindowFrames(1));
        assertThrows(IllegalArgumentException.class, () -> Game.getPrivateObjectives(1));
        for (int i = 2; i <= Parameters.MAX_PLAYERS; i++) {
            List<WindowFrame> frames = Game.getWindowFrames(i);
            List<PrivateObjectiveCard> privateObjectiveCards = Game.getPrivateObjectives(i);
            assertEquals(i, privateObjectiveCards.size());
            assertEquals(i * Parameters.NUM_OF_WINDOWS_PER_PLAYER_BEFORE_CHOICE, frames.size());
            // asserts no duplications
            Set<String> frameNames = frames.stream().map(WindowFrame::getName).collect(Collectors.toSet());
            Set<String> cardNames = privateObjectiveCards.stream().map(Card::getName).collect(Collectors.toSet());
            assertEquals(i, cardNames.size());
            assertEquals(i * Parameters.NUM_OF_WINDOWS_PER_PLAYER_BEFORE_CHOICE, frameNames.size());
        }
        assertThrows(IllegalArgumentException.class, () -> Game.getWindowFrames(Parameters.MAX_PLAYERS + 1));
        assertThrows(IllegalArgumentException.class, () -> Game.getPrivateObjectives(Parameters.MAX_PLAYERS + 1));
    }
}
