package it.polimi.ingsw.model.utility;

/**
 * Represents the color of a Sagrada die.
 */
public enum Color {

    RED("R", "red"),
    GREEN("G", "green"),
    YELLOW("Y", "yellow"),
    BLUE("B", "blue"),
    PURPLE("P", "purple");

    private String id;
    private String label;

    Color(String id, String label) {
        this.id = id;
        this.label = label;
    }

    /**
     * Returns a color from a unique identifier.
     * The possible identifiers are:
     *   - R : color red
     *   - G : color green
     *   - Y : color yellow
     *   - B : color blue
     *   - P : color purple
     * @param id A string ID
     * @return A Color enum value, or null if the ID doesn't match with any color
     */
    public static Color findByID(String id) {
        for (Color c : Color.values())
            if (c.id.equals(id))
                return c;
        return null;
    }

    /**
     * Returns a color from a unique label.
     * The possible labels are:
     *   - red    : color red
     *   - green  : color green
     *   - yellow : color yellow
     *   - blue   : color blue
     *   - purple : color purple
     * @param label A string label
     * @return A Color enum value, or null if the label doesn't match with any color
     */
    public static Color findByLabel(String label) {
        for (Color c : Color.values())
            if (c.label.equals(label))
                return c;
        return null;
    }

    /**
     * Returns the label of the current color.
     * @return A unique string that identifies the color
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the ID of the current color.
     * It can be used for window frame constraints definition.
     * @return A unique string ID that identifies the color
     */
    public String getID() {
        return id;
    }

    @Override
    public String toString() {
        return "[" + this.id + " ]";
    }
}
