package it.polimi.ingsw.model.gameboard.utility;

public enum Shade {

    LIGHTEST("⚀", 1),
    LIGHTER("⚁", 2),
    LIGHT("⚂", 3),
    DARK("⚃", 4),
    DARKER("⚄", 5),
    DARKEST("⚅", 6);

    private String label;
    private int value;

    Shade(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public static Shade findByValue(int value) {
        for (Shade s : Shade.values())
            if (s.value == value)
                return s;
        return null;
    }

    public static Shade findByID(String s) {
        for (Shade shade : Shade.values())
            if (("" + shade.value).equals(s))
                return shade;
        return null;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "[" + this.label + "]";
    }
}
