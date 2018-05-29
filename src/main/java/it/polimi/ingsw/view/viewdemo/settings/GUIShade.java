package it.polimi.ingsw.view.viewdemo.settings;

public enum GUIShade {

    LIGHTEST(1),
    LIGHTER(2),
    LIGHT(3),
    DARK(4),
    DARKER(5),
    DARKEST(6);

    private int value;

    GUIShade(int value){
        this.value = value;
    }

    public static GUIShade findByValue(int value){
        for(GUIShade s : GUIShade.values()){
            if(s.value == value)
                return s;
        }
        return null;
    }
}
