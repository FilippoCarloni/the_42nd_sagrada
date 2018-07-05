package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commands.IllegalCommandException;
import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
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
import static org.junit.jupiter.api.Assertions.*;

/**
 * Acts as a helper class for unit tests. It provides aggregate static methods that make tests
 * more readable and efficient.
 */
public class TestHelper {

    public static final boolean PRINT_PUBLIC_OBJECTIVES = true;
    private static final boolean PRINT_CMD_ERRORS = true;
    private static final String PASS = "pass";
    private static final int MAX_NUMBER_OF_REVERTS = 50;
    private static final String GAME_STATE_PATH = "src/test/java/res/model/";

    private TestHelper() {}

    /**
     * Generates a Game instance from a JSON file.
     * @param fileName Name of the JSON file
     * @return A Game instance from JSON information
     */
    public static Game init(String fileName) {
        try {
            String path = GAME_STATE_PATH + fileName + ".json";
            String content = new String(Files.readAllBytes(Paths.get(path)));
            return new ConcreteGame(JSONFactory.getGameData((JSONObject) new JSONParser().parse(content)));
        } catch (IOException e) {
            throw new IllegalArgumentException(BROKEN_PATH);
        } catch (ParseException e) {
            throw new IllegalArgumentException(BAD_JSON);
        }
    }

    /**
     * Executes a command and catches an IllegalCommandException.
     * @see IllegalCommandException
     * @param g Game on which the command is executed
     * @param player Player that executes the command
     * @param cmd Command string
     */
    public static void wrappedIllegalCommand(Game g, Player player, String cmd) {
        assertThrows(IllegalCommandException.class, () -> g.executeCommand(player, cmd));
        try {
            g.executeCommand(player, cmd);
        } catch (IllegalCommandException e) {
            if (PRINT_CMD_ERRORS)
                System.out.println("---> " + player.getUsername() + " on <" + cmd + ">\n" + e.getMessage());
        }
    }

    /**
     * Executes a command on a particular game state. The command is expected to be a legal one.
     * This method tests multiple "undo" and "redo" actions alongside with the given command,
     * asserting that the state remains as expected.
     * @param g Game on which the command is executed
     * @param player Player that executes the command
     * @param cmd Command string
     */
    public static void wrappedLegalCommand(Game g, Player player, String cmd) {
        try {
            g.executeCommand(player, cmd);
            if (!cmd.equals(PASS)) {
                g.undoCommand();
                assertTrue(g.isRedoAvailable());
                g.executeCommand(player, cmd);
                assertFalse(g.isRedoAvailable());
            }
            String encodedGame = g.getData().encode().toString();
            int reverts = (int) (Math.random() * MAX_NUMBER_OF_REVERTS);
            for (int i = 0; i < reverts; i++)
                g.undoCommand();
            for (int i = 0; i < reverts; i++)
                g.redoCommand();
            assertEquals(encodedGame, g.getData().encode().toString());
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

    /**
     * Asserts that two window frames have identical content.
     * @param w1 First window frame
     * @param w2 Second window frame
     */
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

    /**
     * Generates a list of players. They can optionally be initialized with private objectives and window frames.
     * @param numOfPlayers Size of the player list
     * @param initialize True if the players should be initialized with a random private objective and a window frame
     * @return A List of (not) initialized players
     */
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

    /**
     * Fills a window frame with random dice.
     * @param w The window frame to be filled
     */
    public static void fillMap(WindowFrame w) {
        DiceBag db = new ArrayDiceBag();
        for (int i = 0; i < Parameters.MAX_ROWS; i++)
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++)
                w.put(db.pick(), i, j);
    }
}
