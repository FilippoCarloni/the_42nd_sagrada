package it.polimi.ingsw.model.gameboard.utility;

public enum Color {

    RED("R", "red", "\u001B[31m", "\u001B[41m"),
    GREEN("G", "green", "\u001B[32m", "\u001B[42m"),
    YELLOW("Y", "yellow", "\u001B[33m", "\u001B[43m"),
    BLUE("B", "blue", "\u001B[34m", "\u001B[44m"),
    PURPLE("P", "purple", "\u001B[35m", "\u001B[45m");

    private String id;
    private String label;
    private String ansi;
    private String ansiBG;
    public static final String ANSI_RESET = "\u001B[0m";

    Color(String id, String label, String ansi, String ansiBG) {
        this.id = id;
        this.label = label;
        this.ansi = ansi;
        this.ansiBG = ansiBG;
    }

    public static Color findByID(String id) {
        for (Color c : Color.values())
            if (c.id.equals(id))
                return c;
        return null;
    }

    public String paint(String s) {
        return s == null ? "" : this.ansi + s + ANSI_RESET;
    }

    public String paintBG(String s) {
        return s == null ? "" : this.ansiBG + s + ANSI_RESET;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "[" + this.paintBG(" ") + "]";
    }
}
