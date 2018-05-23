package it.polimi.ingsw.model.gameboard.dice;

import it.polimi.ingsw.model.utility.JSONSerializable;

import java.util.List;

/**
 * Represents the Sagrada dice bag. It holds 90 dice that can be randomly picked from it.
 * Of the 90 dice,
 *     - 18 are RED,
 *     - 18 are YELLOW,
 *     - 18 are BLUE,
 *     - 18 are GREEN,
 *     - 18 are PURPLE
 */
public interface DiceBag extends JSONSerializable {

    /**
     * Picks a die randomly from the remaining dice of the bag.
     * No more than 90 dice can be picked from a single bag.
     * @return A Die object
     */
    Die pick();

    /**
     * Picks an arbitrary number of dice from the bag.
     * No more than 90 dice can be picked from a single bag.
     * @param numOfDice An integer between 1 and the number of remaining dice in the bag
     * @return A List of Die objects
     */
    List<Die> pick(int numOfDice);

    /**
     * Inserts a previously picked die in the bag.
     * This method should only be used by Tool Cards.
     * @param die A Die object picked from this DiceBag
     */
    void insert(Die die);
}
