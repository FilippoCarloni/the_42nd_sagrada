package it.polimi.ingsw.model.gameboard.dice;

import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.model.utility.Shade;
import org.json.simple.JSONObject;

import java.util.Random;

import static java.lang.Integer.parseInt;

public class PlasticDie implements Die {

    private Color color;
    private Shade shade;
    private final int id;

    PlasticDie(int id) {
        this.id = id;
        this.color = Color.values()[new Random().nextInt(Color.values().length)];
        this.roll();
    }

    /**
     * Generates a clone of the die represented with JSON syntax.
     * @param obj A JSON Object that holds Die-like information
     */
    public PlasticDie(JSONObject obj) {
        this.id = parseInt(obj.get(JSONTag.DIE_ID).toString());
        this.color = Color.findByLabel((String) obj.get(JSONTag.COLOR));
        this.shade = Shade.findByID(obj.get(JSONTag.SHADE).toString());
    }

    @Override
    public void roll() {
        this.shade = Shade.values()[new Random().nextInt(Shade.values().length)];
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Shade getShade() {
        return shade;
    }

    @Override
    public void setColor(Color color) {
        if (color == null)
            throw new NullPointerException("Trying to set a null color.");
        this.color = color;
    }

    @Override
    public void setShade(Shade shade) {
        if (shade == null)
            throw new NullPointerException("Trying to set a null shade.");
        this.shade = shade;
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof Die && o instanceof PlasticDie && this.id == ((PlasticDie) o).id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.color.paint(this.shade.toString());
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject encode() {
        JSONObject obj = new JSONObject();
        obj.put(JSONTag.COLOR, color.getLabel());
        obj.put(JSONTag.SHADE, shade.getValue());
        obj.put(JSONTag.DIE_ID, id);
        return obj;
    }
}
