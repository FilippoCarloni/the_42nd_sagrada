package it.polimi.ingsw.view.cli;

import static it.polimi.ingsw.view.cli.CLIMessage.NOT_EXISTENT_PARAMETER;

/**
 * Represents the view information about Sagrada Dice shades.
 */
public enum CLIShade {

    LIGHTEST("⚀", 1),
    LIGHTER("⚁", 2),
    LIGHT("⚂", 3),
    DARK("⚃", 4),
    DARKER("⚄", 5),
    DARKEST("⚅", 6);

    private String label;
    private int value;

    CLIShade(String label, int value) {
        this.label = label;
        this.value = value;
    }

    /**
     * Returns the shade that corresponds to a particular integer value.
     * Throws exception if there's no corresponding shade.
     * @param value A positive integer value
     * @return A CLIShade enum value
     */
    public static CLIShade findByValue(int value) {
        for (CLIShade s : CLIShade.values())
            if (s.value == value)
                return s;
        throw new IllegalArgumentException(NOT_EXISTENT_PARAMETER);
    }

    @Override
    public String toString() {
        return "[" + this.label + "]";
    }
}