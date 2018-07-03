package it.polimi.ingsw.model.gamedata;

import it.polimi.ingsw.model.commands.CommandManager;
import it.polimi.ingsw.model.commands.DequeCommandManager;
import it.polimi.ingsw.model.commands.IllegalCommandException;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.JSONFactory;
import it.polimi.ingsw.model.utility.JSONTag;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.model.utility.ExceptionMessage.NOT_MATCHING_PARAMETER;
import static it.polimi.ingsw.model.utility.ExceptionMessage.NULL_PARAMETER;

/**
 * Implements the Game interface unifying the game data and the command manager with a generic structure.
 * <br>
 * NOTE: the game data represents the raw data of the game (can be treated as a database of the game information)
 * while the command manager represents the rule manager (can be treated as the game logic)
 */
public class ConcreteGame implements Game {

    private CommandManager commandManager;

    /**
     * Generates a new game status (data + rule logic) from a valid list of players.
     * @param players A List of players
     */
    public ConcreteGame(List<Player> players) {
        commandManager = new DequeCommandManager(new ConcreteGameData(players));
    }

    /**
     * Generates a game status (data + rule logic) from the provided game data.
     * @param gameData Current status of the game data
     */
    public ConcreteGame(GameData gameData) {
        if (gameData == null) throw new NullPointerException(NULL_PARAMETER);
        commandManager = new DequeCommandManager(gameData);
    }

    @Override
    public Player getCurrentPlayer() {
        return JSONFactory.getPlayer(commandManager.getCurrentData().getTurnManager().getCurrentPlayer().encode());
    }

    @Override
    public void executeCommand(Player player, String command) throws IllegalCommandException {
        commandManager.executeCommand(player, command);
    }

    @Override
    public void undoCommand() {
        commandManager.undoCommand();
    }

    @Override
    public boolean isUndoAvailable() {
        return commandManager.isUndoAvailable();
    }

    @Override
    public void redoCommand() {
        commandManager.redoCommand();
    }

    @Override
    public boolean isRedoAvailable() {
        return commandManager.isRedoAvailable();
    }

    @Override
    public boolean isGameEnded() {
        return commandManager.getCurrentData().getRoundTrack().isGameFinished();
    }

    @Override
    public Map<Player, Integer> getScore() {
        return commandManager.getCurrentData().getCurrentScore();
    }

    @Override
    public GameData getData() {
        return commandManager.getCurrentData();
    }

    @Override
    public JSONObject getData(Player player) {
        JSONObject data = commandManager.getCurrentData().encode();
        data.remove(JSONTag.DICE_BAG);
        JSONObject turnManager = (JSONObject) data.get(JSONTag.TURN_MANAGER);
        JSONArray players = (JSONArray) turnManager.get(JSONTag.PLAYERS);
        boolean found = false;
        for (Object o : players) {
            JSONObject jsonPlayer = (JSONObject) o;
            if (jsonPlayer.get(JSONTag.USERNAME).toString().equals(player.getUsername())) {
                found = true;
            } else {
                jsonPlayer.remove(JSONTag.PRIVATE_OBJECTIVE);
            }
        }
        if (!found)
            throw new IllegalArgumentException(NOT_MATCHING_PARAMETER);
        return data;
    }
}
