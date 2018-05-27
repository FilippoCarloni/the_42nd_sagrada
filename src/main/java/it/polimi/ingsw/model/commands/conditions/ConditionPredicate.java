package it.polimi.ingsw.model.commands.conditions;

import it.polimi.ingsw.model.gamedata.GameData;

@FunctionalInterface
public interface ConditionPredicate {
    boolean test(GameData gameData, int[] args);
}
