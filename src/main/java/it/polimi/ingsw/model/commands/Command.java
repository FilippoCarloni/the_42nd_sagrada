package it.polimi.ingsw.model.commands;

public interface Command {

    boolean isValid();
    boolean isLegal();
    void execute();
}
