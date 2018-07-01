package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commands.IllegalCommandException;
import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import it.polimi.ingsw.model.gamedata.ConcreteGame;
import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.JSONFactory;
import it.polimi.ingsw.model.utility.Parameters;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.utility.ExceptionMessage.BAD_JSON;
import static it.polimi.ingsw.model.utility.ExceptionMessage.BROKEN_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestHelper {

    private TestHelper() {}

    public static Game init(String fileName) {
        try {
            String path = "src/test/java/res/model/" + fileName + ".json";
            String content = new String(Files.readAllBytes(Paths.get(path)));
            return new ConcreteGame(JSONFactory.getGameData((JSONObject) new JSONParser().parse(content)));
        } catch (IOException e) {
            throw new IllegalArgumentException(BROKEN_PATH);
        } catch (ParseException e) {
            throw new IllegalArgumentException(BAD_JSON);
        }
    }

    public static void wrappedIllegalCommand(Game g, Player player, String cmd) {
        assertThrows(IllegalCommandException.class, () -> g.executeCommand(player, cmd));
        try {
            g.executeCommand(player, cmd);
        } catch (IllegalCommandException e) {
            System.out.println("---> " + player.getUsername() + " on <" + cmd + ">\n" + e.getMessage());
        }
    }

    public static void wrappedLegalCommand(Game g, Player player, String cmd) {
        try {
            g.executeCommand(player, cmd);
            if (!cmd.equals("pass")) {
                g.undoCommand();
                g.executeCommand(player, cmd);
            }
            for (int i = 0; i < 100; i++)
                g.undoCommand();
            for (int i = 0; i < 100; i++)
                g.redoCommand();
            encodingJSONTest(g);
        } catch (IllegalCommandException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static void encodingJSONTest(Game g) {
        try {
            JSONFactory.getGameData((JSONObject) new org.json.simple.parser.JSONParser().parse(g.getData().encode().toString()));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static void areWindowFramesEqual(WindowFrame w1, WindowFrame w2) {
        assertEquals(w1.getName(), w2.getName());
        assertEquals(w1.getDifficulty(), w2.getDifficulty());
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                assertEquals(w1.getDie(i, j), w2.getDie(i, j));
                assertEquals(w1.getColorConstraint(i, j), w2.getColorConstraint(i, j));
                assertEquals(w1.getShadeConstraint(i, j), w2.getShadeConstraint(i, j));
            }
        }
    }

    public static List<Player> getPlayerList(int numOfPlayers, boolean initialize) {
        assert numOfPlayers >= 0;
        Deck windowFrameDeck = new WindowFrameDeck();
        Deck privateObjectiveDeck = new PrivateObjectiveDeck();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numOfPlayers; i++) {
            Player p = new ConcretePlayer("player" + i);
            if (initialize) {
                p.setWindowFrame((WindowFrame) windowFrameDeck.draw());
                p.setPrivateObjective((PrivateObjectiveCard) privateObjectiveDeck.draw());
            }
            players.add(p);
        }
        return players;
    }
}
