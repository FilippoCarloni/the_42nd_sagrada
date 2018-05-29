package it.polimi.ingsw.view.viewdemo.settings;

import javafx.scene.paint.Color;

public enum GUIColor {

    RED("red", Color.RED),
    YELLOW("yellow", Color.YELLOW),
    GREEN("green", Color.GREEN),
    BLUE("blue", Color.BLUE),
    PURPLE("purple", Color.PURPLE);

    private String name;
    private Color color;

    GUIColor(String name, Color color){
        this.name = name;
        this.color = color;
    }

    public static GUIColor findByName(String name){
        for(GUIColor c : GUIColor.values()){
            if (c.name.equals(name))
                return c;
        }
        return null;
    }
    public Color getColor(){
        return this.color;
    }

}
