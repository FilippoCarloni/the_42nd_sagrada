package it.polimi.ingsw.view.gui.utility;

import javafx.scene.paint.Color;

/**
 * Enum, containing the colors that a Sagrada die can assume.
 */

public enum GUIColor {

    RED(23, Color.RED),
    GREEN(24, Color.GREEN),
    YELLOW(25, Color.YELLOW),
    BLUE(26, Color.BLUE),
    PURPLE(27, Color.PURPLE);

    private int id;
    private Color color;

    /**
     * Generate a new GUIColor enum value.
     * @param id: integer that uniquely identifies the color. It can assume the value of one into the
     *          private objective cards' id.
     * @param color: Color type that corresponds to the identifier.
     */
    GUIColor(int id, Color color){
        this.id = id;
        this.color = color;
    }

    /**
     * Used to find a GUIColor enum, from an unique identifier.
     * The possible identifier are:
     * <ol>
     *     <li>23</li>
     *     <li>24</li>
     *     <li>25</li>
     *     <li>26</li>
     *     <li>27</li>
     * </ol>
     * @param id: the identifier of the GUIColor to find.
     * @return a GUIColor enum.
     * @throws IllegalArgumentException if id is not one of the possible values.
     */
    public static GUIColor findById(int id){
        for(GUIColor c: GUIColor.values()){
            if(c.id == id)
                return c;
        }
        throw new IllegalArgumentException(id + GUIParameters.FIND_ERROR);
    }

    /**
     * Getter for the Color value of a GUIColor enum.
     * @return a Color value, used to color the private objective rectangles, into main board and into every screen
     *          that shows a private objective card.
     */
    public Color getColor(){
        return this.color;
    }

}
