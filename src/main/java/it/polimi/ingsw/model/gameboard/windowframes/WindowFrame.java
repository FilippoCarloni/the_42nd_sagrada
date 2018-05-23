package it.polimi.ingsw.model.gameboard.windowframes;

import it.polimi.ingsw.model.gameboard.cards.Drawable;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.JSONSerializable;
import it.polimi.ingsw.model.utility.Shade;

import java.util.List;

/**
 * Represents the frame that the player has to fill with the drafted dice in order to create the stained-glass window.
 * It is represented as a grid with dimensions 4x5 (4 rows and 5 columns) that can hold dice.
 * It has placing constraints in some cells.
 * Each frame has a difficulty roughly proportional to the number of constraints that contains.
 */
public interface WindowFrame extends Drawable, JSONSerializable {

    /**
     * Returns the name of the frame. Every frame has a unique flavor that inspired the name.
     * @return A String that represents a unique name
     */
    String getName();

    /**
     * Returns the set of all dice contained in the frame.
     * @return A List of Die objects
     */
    List<Die> getDice();

    /**
     * Returns the color constraint of a cell.
     * @param row Row index between 0 and 3
     * @param column Column index between 0 and 4
     * @return A Color enum value; if not present, returns null
     */
    Color getColorConstraint(int row, int column);

    /**
     * Returns the shade constraint of a cell.
     * @param row Row index between 0 and 3
     * @param column Column index between 0 and 4
     * @return A Shade enum value; if not present, returns null
     */
    Shade getShadeConstraint(int row, int column);

    /**
     * Returns the difficulty of the map (higher is more difficult!).
     * @return An integer between 3 and 6
     */
    int getDifficulty();

    /**
     * Returns true if the cell at (row, column) contains a die.
     * @param row Row index between 0 and 3
     * @param column Column index between 0 and 4
     * @return A boolean value true only if a Die object is present in the cell
     */
    boolean isEmpty(int row, int column);

    /**
     * Returns the die contained in the cell (row, column). If there's no die, returns null.
     * @param row Row index between 0 and 3
     * @param column Column index between 0 and 4
     * @return A Die Object
     */
    Die getDie(int row, int column);

    /**
     * Puts a die in (row, column) position. The cell must be empty.
     * @param die Die that should be placed
     * @param row Row index between 0 and 3
     * @param column Column index between 0 and 4
     */
    void put(Die die, int row, int column);

    /**
     * Moves unconditionally a die from its old position to an empty one.
     * @param oldRow Row index between 0 and 3
     * @param oldColumn Column index between 0 and 4
     * @param newRow Row index between 0 and 3
     * @param newColumn Column index between 0 and 4
     */
    void move(int oldRow, int oldColumn, int newRow, int newColumn);

    /**
     * Removes a die from its position.
     * This method should only by Tool Cards and for testing purposes.
     * @param row Row index between 0 and 3
     * @param column Column index between 0 and 4
     * @return The Die that was placed in the (row, column) cell
     */
    Die pick(int row, int column);
}
