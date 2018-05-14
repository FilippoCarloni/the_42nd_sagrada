package it.polimi.ingsw.model.gameboard.windowframes;

import it.polimi.ingsw.model.gameboard.cards.Drawable;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.utility.Color;
import it.polimi.ingsw.model.gameboard.utility.Shade;

import java.util.Map;

public interface WindowFrame extends Iterable<Die>, Drawable {

    String getName();
    Map<Coordinate, Color> getColorConstraints();
    Map<Coordinate, Shade> getShadeConstraints();
    int getDifficulty();
    boolean isEmpty(int row, int column);
    Die getDie(int row, int column);
    void put(Die die, int row, int column);
    void move(int oldRow, int oldColumn, int newRow, int newColumn);
    void move(Die die, int row, int column);
    Die pick(int row, int column);
}
