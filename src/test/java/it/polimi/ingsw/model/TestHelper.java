package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commands.IllegalCommandException;
import it.polimi.ingsw.model.gamedata.ConcreteGame;
import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.JSONFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestHelper {

    private TestHelper() {}

    public static Game init(String fileName) {
        try {
            String path = "src/test/java/res/model/" + fileName + ".json";
            String content = new String(Files.readAllBytes(Paths.get(path)));
            return new ConcreteGame(JSONFactory.getGameData((JSONObject) new JSONParser().parse(content)));
        } catch (IOException e) {
            throw new IllegalArgumentException("Bad file name.");
        } catch (ParseException e) {
            throw new IllegalArgumentException("Bad JSON file.");
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
}
