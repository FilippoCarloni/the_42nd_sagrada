package it.polimi.ingsw.view.gui.utility;

/**
 * Enum, used to draw dice on game board
 */

public enum GUIShade {

    LIGHTEST(1, GUIParameters.OFFSET_DIE_1),
    LIGHTER(2, GUIParameters.OFFSET_DIE_2),
    LIGHT(3, GUIParameters.OFFSET_DIE_3),
    DARK(4, GUIParameters.OFFSET_DIE_4),
    DARKER(5, GUIParameters.OFFSET_DIE_5),
    DARKEST(6, GUIParameters.OFFSET_DIE_6);

    private int value;
    private double[] coordinates;

    /**
     * Generate a new GUIShade enum value.
     * @param value: the value of the new GUIShade enum.
     * @param coordinates: the coordinates that the die's points will take into the canvas in which it will be drawn.
     */
    GUIShade(int value, double[] coordinates){
        this.value = value;
        this.coordinates = coordinates;
    }

    /**
     * Used to find a GUIShade enum, from an unique identifier.
     * The possible identifier are:
     * <ol>
     *    <li>1</li>
     *    <li>2</li>
     *    <li>3</li>
     *    <li>4</li>
     *    <li>5</li>
     *    <li>6</li>
     * </ol>
     * @param value: the integer value of the GUIShade to find.
     * @return a GUIShade enum.
     * @throws IllegalArgumentException if value is not one of the possible values.
     */
    public static GUIShade findByValue(int value){
        for(GUIShade s : GUIShade.values()){
            if(s.value == value)
                return s;
        }
        throw new IllegalArgumentException(value + GUIParameters.FIND_ERROR);
    }

    /**
     * Getter for coordinates of a GUIShade enum.
     * @return an array of double, containing coordinates that the die's points will take into the canvas
     *          in which it will be drawn.
     */
    public double[] getCoordinates(){
        return coordinates;
    }
}
