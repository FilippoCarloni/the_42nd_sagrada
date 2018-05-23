package it.polimi.ingsw.model.gameboard.dice;

import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Shade;
import org.json.simple.JSONObject;

import java.util.Random;

public class PlasticDie implements Die {

    private Color color;
    private Shade shade;
    private int id;

    PlasticDie(int id) {
        this.id = id;
        this.color = Color.values()[new Random().nextInt(Color.values().length)];
        this.roll();
    }

    public PlasticDie(JSONObject obj) {
        this.id = (int) obj.get("id");
        this.color = Color.findByLabel((String) obj.get("color"));
        this.shade = Shade.findByID(obj.get("shade").toString());
    }

    public PlasticDie(PlasticDie die) {
        this.id = die.id;
        this.color = die.color;
        this.shade = die.shade;
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
        obj.put("color", color.getLabel());
        obj.put("shade", shade.getValue());
        obj.put("id", id);
        return obj;
    }
}
