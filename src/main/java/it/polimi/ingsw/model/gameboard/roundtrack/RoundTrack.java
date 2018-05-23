package it.polimi.ingsw.model.gameboard.roundtrack;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.JSONSerializable;

import java.util.List;

/**
 * Represents the round track of the Sagrada game.
 * A round track holds up to ten dice sets:
 * a set of dice is placed on the round track at the end of the round.
 * When all the ten slots of the round track are filled with dice the game ends.
 */
public interface RoundTrack extends JSONSerializable {

    /**
     * Potentially only a fraction of the dice on the round track is visible.
     * This method returns up to ten dice that can be viewed by players.
     * @return A List of up to ten Die objects
     */
    List<Die> getVisibleDice();

    /**
     * Returns all the dice that are currently present on the round track.
     * @return A List of Die objects
     */
    List<Die> getDice();

    /**
     * Fills the next free slot on the round track with a set of dice.
     * @param dice A List of Die objects that should be placed on the round track
     */
    void put(List<Die> dice);

    /**
     * Swaps a die on the round track with a die that is not currently present on the round track.
     * @param playerDie A Die object not contained in the round track
     * @param roundTrackDie A die object currently contained in the round track
     */
    void swap(Die playerDie, Die roundTrackDie);

    /**
     * Returns the number of rounds played + 1.
     * @return An integer between 1 and 10
     */
    int getCurrentRoundNumber();

    /**
     * Returns the total number of rounds that can be played during a game.
     * @return An integer equal to 10
     */
    int getTotalNumberOfRounds();

    /**
     * Returns a boolean value that equals to true only when 10 rounds have been played.
     * @return A boolean value
     */
    boolean isGameFinished();
}
