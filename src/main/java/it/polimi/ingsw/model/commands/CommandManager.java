package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;

public interface CommandManager {
    GameData getCurrentData();
    void executeCommand(Player player, String cmd) throws IllegalCommandException;
    void undoCommand();
    boolean isUndoAvailable();
    void redoCommand();
    boolean isRedoAvailable();
}
