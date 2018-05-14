package it.polimi.ingsw.model.commands;

public interface Command extends Executable {
    boolean isValid();
}
