package it.polimi.ingsw.view.gui.settings;

import javafx.scene.paint.Color;

public enum GUIColor {

    RED(23, Color.RED),
    GREEN(24, Color.GREEN),
    YELLOW(25, Color.YELLOW),
    BLUE(26, Color.BLUE),
    PURPLE(27, Color.PURPLE);

    private int id;
    private Color color;

    GUIColor(int id, Color color){
        this.id = id;
        this.color = color;
    }

    public static GUIColor findById(int id){
        for(GUIColor c: GUIColor.values()){
            if(c.id == id)
                return c;
        }
        throw new IllegalArgumentException(id + " not valid, please insert a new one");
    }
    public Color getColor(){
        return this.color;
    }

}
