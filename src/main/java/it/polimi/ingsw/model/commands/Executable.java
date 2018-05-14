package it.polimi.ingsw.model.commands;

public interface Executable {
    boolean isLegal();
    void execute();
}
