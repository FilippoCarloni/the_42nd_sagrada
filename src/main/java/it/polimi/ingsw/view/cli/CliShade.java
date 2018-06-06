package it.polimi.ingsw.view.cli;

public enum CliShade {

    LIGHTEST("⚀", 1),
    LIGHTER("⚁", 2),
    LIGHT("⚂", 3),
    DARK("⚃", 4),
    DARKER("⚄", 5),
    DARKEST("⚅", 6);

    private String label;
    private int value;

    CliShade(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public static CliShade findByValue(int value) {
        for (CliShade s : CliShade.values())
            if (s.value == value)
                return s;
        return null;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "[" + this.label + "]";
    }
}