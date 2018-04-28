package it.polimi.ingsw.model.gameboard.dice;

import it.polimi.ingsw.model.gameboard.utility.Color;
import it.polimi.ingsw.model.gameboard.utility.Shade;

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
        this.color = color;
    }

    @Override
    public void setShade(Shade shade) {
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
}
