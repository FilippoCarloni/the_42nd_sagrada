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

public class ConcreteGame implements Game {

    private CommandManager commandManager;

    public ConcreteGame(List<Player> players) {
        if (players == null)
            throw new NullPointerException("Null players.");
        commandManager = new DequeCommandManager(new ConcreteGameData(players));
    }

    public ConcreteGame(GameData gameData) {
        if (gameData == null)
            throw new NullPointerException("Null game data.");
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
        return JSONFactory.getGameData(commandManager.getCurrentData().encode());
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
            throw new IllegalArgumentException("The passed player is not playing in this game.");
        return data;
    }
}
