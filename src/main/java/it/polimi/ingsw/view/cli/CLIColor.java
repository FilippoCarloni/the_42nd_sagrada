package it.polimi.ingsw.view.cli;

import static it.polimi.ingsw.view.cli.CLIMessage.NOT_EXISTENT_PARAMETER;

/**
 * Represents the command line representation of Sagrada colors.
 * Colors are printed in the terminal with ansi escape codes.
 */
public enum CLIColor {

    RED("red", "\u001B[31m"),
    GREEN( "green", "\u001B[32m"),
    YELLOW( "yellow", "\u001B[33m"),
    BLUE("blue", "\u001B[34m"),
    PURPLE( "purple", "\u001B[35m");

    private String label;
    private String ansi;
    public static final String ANSI_RESET = "\u001B[0m";

    CLIColor(String label, String ansi) {
        this.label = label;
        this.ansi = ansi;
    }

    /**
     * Returns the CLI color from the unique label string.
     * Throws exception if there's no matching color.
     * @param label A unique string label
     * @return A CLIColor enum value
     */
    public static CLIColor findByLabel(String label) {
        for (CLIColor c : CLIColor.values())
            if (c.label.equals(label))
                return c;
        throw new IllegalArgumentException(NOT_EXISTENT_PARAMETER);
    }

    /**
     * Returns a string colored with ansi escape codes.
     * @param s The string that will be colored
     * @return A new colored string with the same text as s
     */
    public String paint(String s) {
        return s == null ? "" : this.ansi + s + ANSI_RESET;
    }

    @Override
    public String toString() {
        return "[" + this.paint("■") + "]";
    }
}