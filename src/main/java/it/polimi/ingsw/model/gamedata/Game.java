package it.polimi.ingsw.model.gamedata;

import it.polimi.ingsw.model.commands.IllegalCommandException;
import it.polimi.ingsw.model.players.Player;
import org.json.simple.JSONObject;

import java.util.Map;

public interface Game {
    void executeCommand(Player player, String command) throws IllegalCommandException;
    void undoCommand();
    boolean isUndoAvailable();
    boolean isGameEnded();
    Map<Player, Integer> getScore();
    JSONObject getData();
}
