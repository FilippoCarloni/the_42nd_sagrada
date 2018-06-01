package it.polimi.ingsw.model.commands.conditions;

import it.polimi.ingsw.model.commands.IllegalCommandException;
import it.polimi.ingsw.model.gamedata.GameData;

public interface Condition {

    ConditionPredicate getPredicate();

    String getErrorMessage();

    default void check(GameData gameData, int[] args) throws IllegalCommandException {
        if (!getPredicate().test(gameData, args))
            throw new IllegalCommandException(getErrorMessage());
    }
}
