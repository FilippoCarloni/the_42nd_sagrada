package it.polimi.ingsw.model.commands;

public class CommandRegExp {

    private CommandRegExp() {}

    public static final String PICK = "pick \\d";
    public static final String PLACE = "place \\d \\d";
    public static final String PASS = "pass";
}
