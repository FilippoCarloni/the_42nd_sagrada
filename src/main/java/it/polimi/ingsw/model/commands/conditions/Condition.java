package it.polimi.ingsw.model.commands.conditions;

import it.polimi.ingsw.model.commands.IllegalCommandException;
import it.polimi.ingsw.model.gamedata.GameData;

/**
 * Represents a generic condition that can be verified on the game data of a particular game state.
 */
public interface Condition {

    /**
     * Returns the predicate that the condition is trying to verify.
     * @return A ConditionPredicate instance
     */
    ConditionPredicate getPredicate();

    /**
     * Returns the error message that the condition suggests when not verified.
     * @return An error String that holds the information about the verification failure
     */
    String getErrorMessage();

    /**
     * Checks if the condition is verified by the given data.
     * @param gameData The GameData that encodes the information about the current game status
     * @param args The arguments provided to the condition
     * @throws IllegalCommandException The Condition is not verified by the given data
     */
    default void check(GameData gameData, int[] args) throws IllegalCommandException {
        if (!getPredicate().test(gameData, args))
            throw new IllegalCommandException(getErrorMessage());
    }
}
