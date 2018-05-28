package it.polimi.ingsw.model.commands;

public interface Command {
    boolean isValid();
    void execute() throws IllegalCommandException;
    boolean undoable();
}
