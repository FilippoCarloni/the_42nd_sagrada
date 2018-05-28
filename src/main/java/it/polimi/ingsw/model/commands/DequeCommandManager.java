package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.commands.basiccommands.Pass;
import it.polimi.ingsw.model.commands.basiccommands.Pick;
import it.polimi.ingsw.model.commands.basiccommands.Place;
import it.polimi.ingsw.model.commands.basiccommands.Tool;
import it.polimi.ingsw.model.gameboard.cards.ToolCard;
import it.polimi.ingsw.model.gamedata.ConcreteGameData;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_COMMAND_NOT_EXISTS;
import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_GENERIC_MESSAGE;

public class DequeCommandManager implements CommandManager {

    private Deque<JSONObject> undoCommands;
    private Deque<JSONObject> redoCommands;
    private GameData gameData;

    public DequeCommandManager(GameData gameData) {
        this.gameData = gameData;
        undoCommands = new ArrayDeque<>();
        redoCommands = new ArrayDeque<>();
    }

    private List<Command> allocateCommands(Player player, String cmd) {
        List<Command> commands = new ArrayList<>();
        commands.add(new Pick(player, gameData, cmd));
        commands.add(new Place(player, gameData, cmd));
        commands.add(new Pass(player, gameData, cmd));
        commands.add(new Tool(player, gameData, cmd));
        for (ToolCard c : gameData.getTools())
            commands.addAll(c.getCommands(player, gameData, cmd));
        return commands;
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
            sb.append("\n    ").append(ERR_COMMAND_NOT_EXISTS);
        for (String s : errorMessages)
            sb.append("\n    ").append(s);
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
            gameData = new ConcreteGameData(undoCommands.pop());
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
            gameData = new ConcreteGameData(redoCommands.pop());
        }
    }
}
