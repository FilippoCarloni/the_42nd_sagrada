package it.polimi.ingsw.model.commands;

public interface CommandManager {
    boolean isValid(String cmd);
    boolean isLegal(String cmd);
    void execute(String cmd);
}
