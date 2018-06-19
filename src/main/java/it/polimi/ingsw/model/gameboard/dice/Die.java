package it.polimi.ingsw.model.gameboard.dice;

import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.JSONSerializable;
import it.polimi.ingsw.model.utility.Shade;

/**
 * This is the building block of Sagrada game: the Die.
 * Dice are used by the players to build stained-glass windows.
 * Every Sagrada die has:
 * <ul>
 *     <li>color: represents the glass color of a particular piece of glass</li>
 *     <li>shade (numerical value): represents the opacity of the piece of glass</li>
 * </ul>
 */
public interface Die extends JSONSerializable {

    /**
     * Rolls the die. Color doesn't change, but shade value can.
     */
    void roll();

    /**
     * Returns the color of the die.
     * @return A Color enum value
     */
    Color getColor();

    /**
     * Returns the shade of the die.
     * @return A Shade enum value
     */
    Shade getShade();

    /**
     * Sets the color of the die.
     * @param c A Color enum value
     */
    void setColor(Color c);

    /**
     * Sets the shade of the die.
     * @param s A Shade enum value
     */
    void setShade(Shade s);
}
