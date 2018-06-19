package it.polimi.ingsw.model.commands.conditions;

import it.polimi.ingsw.model.gamedata.GameData;

/**
 * Represents a generic predicate that can be verified on GameData instances.
 */
@FunctionalInterface
public interface ConditionPredicate {

    /**
     * Returns true if the provided GameData verifies the predicate on passed arguments.
     * @param gameData The current GameData
     * @param args The argument provided by the player's command
     * @return A boolean value, true only if the condition is verified by the current data
     */
    boolean test(GameData gameData, int[] args);
}
