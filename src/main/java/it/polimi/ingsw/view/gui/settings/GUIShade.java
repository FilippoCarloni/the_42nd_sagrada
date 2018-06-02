package it.polimi.ingsw.view.gui.settings;

public enum GUIShade {

    LIGHTEST(1, GUIParameters.OFFSET_DIE_1),
    LIGHTER(2, GUIParameters.OFFSET_DIE_2),
    LIGHT(3, GUIParameters.OFFSET_DIE_3),
    DARK(4, GUIParameters.OFFSET_DIE_4),
    DARKER(5, GUIParameters.OFFSET_DIE_5),
    DARKEST(6, GUIParameters.OFFSET_DIE_6);

    private int value;
    private double[] coordinates;

    GUIShade(int value, double[] coordinates){
        this.value = value;
        this.coordinates = coordinates;
    }

    public static GUIShade findByValue(int value){
        for(GUIShade s : GUIShade.values()){
            if(s.value == value)
                return s;
        }
        return null;
    }
    public double[] getCoordinates(){
        return coordinates;
    }
}
