package it.polimi.ingsw.view.cli;

public enum CliColor {

    RED("red", "\u001B[31m"),
    GREEN( "green", "\u001B[32m"),
    YELLOW( "yellow", "\u001B[33m"),
    BLUE("blue", "\u001B[34m"),
    PURPLE( "purple", "\u001B[35m");

    private String label;
    private String ansi;
    public static final String ANSI_RESET = "\u001B[0m";

    CliColor( String label, String ansi) {
        this.label = label;
        this.ansi = ansi;
    }

    public static CliColor findByLabel(String label) {
        for (CliColor c : CliColor.values())
            if (c.label.equals(label))
                return c;
        throw new IllegalArgumentException("There's no label-matching color.");
    }

    public String paint(String s) {
        return s == null ? "" : this.ansi + s + ANSI_RESET;
    }

    @Override
    public String toString() {
        return "[" + this.paint("■") + "]";
    }
}