package it.polimi.ingsw.model.utility;

/**
 * Represents the shade of a die. A shade consists of an integer between 1 and 6,
 * that corresponds with the value on the top face of a die.
 */
public enum Shade {

    LIGHTEST(1),
    LIGHTER(2),
    LIGHT(3),
    DARK(4),
    DARKER(5),
    DARKEST(6);

    private int value;

    /**
     * Generates a Shade enum value.
     * @param value The positive integer that is represented by the Shade
     */
    Shade(int value) {
        this.value = value;
    }

    /**
     * <p>Returns a shade from a unique integer value. The possible values are:</p>
     * <ol>
     *   <li>lightest shade</li>
     *   <li>lighter shade</li>
     *   <li>light shade</li>
     *   <li>dark shade</li>
     *   <li>darker shade</li>
     *   <li>darkest shade</li>
     * </ol>
     * @param value A shade integer
     * @return A Shade enum value, or null if the integer doesn't match with any shade
     */
    public static Shade findByValue(int value) {
        return findByID("" + value);
    }

    /**
     * <p>Returns a shade from a unique integer value, passed as string. The possible values are:</p>
     * <ol>
     *     <li>lightest shade</li>
     *     <li>lighter shade</li>
     *     <li>light shade</li>
     *     <li>dark shade</li>
     *     <li>darker shade</li>
     *     <li>darkest shade</li>
     * </ol>
     * @param s A string value
     * @return A Shade enum value, or null if the string doesn't match with any shade
     */
    public static Shade findByID(String s) {
        for (Shade shade : Shade.values())
            if (("" + shade.value).equals(s))
                return shade;
        return null;
    }

    /**
     * Returns the maximum value from all the possible shade values.
     * @return A positive integer
     */
    public static int getMaximumValue() {
        int max = 0;
        for (Shade s : Shade.values())
            if (s.getValue() > max)
                max = s.getValue();
        return max;
    }

    /**
     * Returns the minimum value from all the possible shade values.
     * @return A positive integer
     */
    public static int getMinimumValue() {
        int min = 7;
        for (Shade s : Shade.values())
            if (s.getValue() < min)
                min = s.getValue();
        return min;
    }

    /**
     * Returns the label of the shade.
     * The label is unique and can be used for window frame constraint definition.
     * The label corresponds to the integer value of the shade.
     * @return A string that identifies uniquely the shade
     */
    public String getLabel() {
        return "" + value;
    }

    /**
     * Returns the integer value of the shade.
     * @return An integer between 1 and 6
     */
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "[ " + this.value + "]";
    }
}
