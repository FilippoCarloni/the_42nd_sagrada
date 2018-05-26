package it.polimi.ingsw.model.gamedata;

import it.polimi.ingsw.model.commands.IllegalCommandException;
import it.polimi.ingsw.model.players.Player;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public class ConcreteGame implements Game {

    // private CommandManager commandManager
    private GameData gameData;

    public ConcreteGame(List<Player> players) {
        gameData = new ConcreteGameData(players);
        // commandManager = new ConcreteCommandManager(gameData)
    }

    @Override
    public void executeCommand(Player player, String command) throws IllegalCommandException {
        // commandManager.executeCommand(command)
        throw new IllegalCommandException("Still needs to be implemented");
    }

    @Override
    public void undoCommand() {
        // commandManager.undoCommand()
    }

    @Override
    public boolean isUndoAvailable() {
        // commandManager.isUndoAvailable()
        return false;
    }

    @Override
    public boolean isGameEnded() {
        return gameData.getRoundTrack().isGameFinished();
    }

    @Override
    public Map<Player, Integer> getScore() {
        return gameData.getCurrentScore();
    }

    @Override
    public JSONObject getData() {
        return gameData.encode();
    }
}
