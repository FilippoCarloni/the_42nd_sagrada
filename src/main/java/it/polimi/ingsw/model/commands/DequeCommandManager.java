package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.gameboard.cards.tools.ToolCard;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.JSONFactory;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.model.utility.Parameters;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_COMMAND_NOT_EXISTS;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_GENERIC_MESSAGE;
import static it.polimi.ingsw.model.utility.ExceptionMessage.BAD_JSON;
import static it.polimi.ingsw.model.utility.ExceptionMessage.BROKEN_PATH;

/**
 * Implements the CommandManager interface with a Deque-like structure.
 */
public class DequeCommandManager implements CommandManager {

    private Deque<JSONObject> undoCommands;
    private Deque<JSONObject> redoCommands;
    private GameData gameData;

    /**
     * Generates a new command manager.
     * @param gameData Game data in a starting state
     */
    public DequeCommandManager(GameData gameData) {
        this.gameData = gameData;
        undoCommands = new ArrayDeque<>();
        redoCommands = new ArrayDeque<>();
    }

    private List<Command> allocateCommands(Player player, String cmd) {
        try {
            String path = Parameters.BASIC_COMMANDS_PATH;
            String content = new String(Files.readAllBytes(Paths.get(path)));
            JSONObject obj = (JSONObject) new JSONParser().parse(content);
            JSONArray commandsSER = (JSONArray) obj.get(JSONTag.COMMANDS);
            List<Command> commands = new ArrayList<>();
            for (Object o : commandsSER)
                commands.add(CommandFactory.getCommand((JSONObject) o, player, gameData, cmd));
            for (ToolCard tc : gameData.getTools()) {
                commands.add(tc.getActivator(player, gameData, cmd));
                if (tc.getID() == gameData.getActiveToolID() || tc.getID() == gameData.getPassiveToolID())
                    commands.addAll(tc.getCommands(player, gameData, cmd));
            }
            return commands;
        } catch (IOException e) {
            throw new IllegalArgumentException(BROKEN_PATH);
        } catch (ParseException e) {
            throw new IllegalArgumentException(BAD_JSON);
        }
    }

    private List<Command> getValidCommands(Player player, String cmd) {
        return allocateCommands(player, cmd).stream().filter(Command::isValid).collect(Collectors.toList());
    }

    @Override
    public GameData getCurrentData() {
        return gameData;
    }

    @Override
    public void executeCommand(Player player, String cmd) throws IllegalCommandException {
        List<String> errorMessages = new ArrayList<>();
        for (Command c : getValidCommands(player, cmd)) {
            try {
                undoCommands.push(gameData.encode());
                c.execute();
                redoCommands.clear();
                return;
            } catch (IllegalCommandException e) {
                undoCommands.pop();
                errorMessages.add(e.getMessage());
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ERR_GENERIC_MESSAGE);
        if (errorMessages.isEmpty())
            sb.append("\n\t").append(ERR_COMMAND_NOT_EXISTS);
        for (String s : errorMessages)
            sb.append("\n\t").append(s);
        throw new IllegalCommandException(sb.toString());
    }

    @Override
    public boolean isUndoAvailable() {
        return !undoCommands.isEmpty() && gameData.isUndoAvailable();
    }

    @Override
    public void undoCommand() {
        if (!undoCommands.isEmpty()) {
            redoCommands.push(gameData.encode());
            gameData = JSONFactory.getGameData(undoCommands.pop());
        }
    }

    @Override
    public boolean isRedoAvailable() {
        return !redoCommands.isEmpty();
    }

    @Override
    public void redoCommand() {
        if (!redoCommands.isEmpty()) {
            undoCommands.push(gameData.encode());
            gameData = JSONFactory.getGameData(redoCommands.pop());
        }
    }
}
