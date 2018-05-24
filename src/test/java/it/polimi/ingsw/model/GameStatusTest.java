package it.polimi.ingsw.model;

import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameStatusTest {

    private List<Player> getPlayers() {
        Player a = new ConcretePlayer("a");
        Player b = new ConcretePlayer("b");
        Player c = new ConcretePlayer("c");
        ArrayList<Player> players = new ArrayList<>();
        players.add(a);
        players.add(b);
        players.add(c);
        return players;
    }

    private void assertEquality(ConcreteGameStatus gs, ConcreteGameStatus gsClone) {
        assertEquals(gs.getTools().get(0).getID(), gsClone.getTools().get(0).getID());
        assertEquals(gs.getTools().get(1).getID(), gsClone.getTools().get(1).getID());
        assertEquals(gs.getTools().get(2).getID(), gsClone.getTools().get(2).getID());
        List<Player> players = gs.getPlayers();
        List<Player> playersCloned = gsClone.getPlayers();
        assertTrue(players.stream().map(Player::getUsername).collect(Collectors.toList()).containsAll(
                playersCloned.stream().map(Player::getUsername).collect(Collectors.toList())
        ));
        assertEquals(gs.getTurnManager().getCurrentPlayer().getUsername(), gsClone.getTurnManager().getCurrentPlayer().getUsername());
    }

    @Test
    void testJSON() {

        // TODO: should be tested more deeply
        try {
            ConcreteGameStatus gs = new ConcreteGameStatus(getPlayers());
            ConcreteGameStatus gsClone = new ConcreteGameStatus((JSONObject) new JSONParser().parse(gs.encode().toString()));
            assertEquality(gs, gsClone);
        } catch (ParseException e) {
            assertFalse(true);
        }
    }
}
