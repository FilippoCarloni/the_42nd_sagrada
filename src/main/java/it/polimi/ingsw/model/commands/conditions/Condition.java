package it.polimi.ingsw.model.commands.conditions;

import it.polimi.ingsw.model.commands.IllegalCommandException;
import it.polimi.ingsw.model.gamedata.GameData;

public class Condition {

    private GameData gameData;
    private int[] args;
    private ConditionPredicate predicate;
    private String errorMessage;

    public Condition(GameData gameData, int[] args, ConditionPredicate predicate, String errorMessage) {
        assert gameData != null;
        assert args != null;
        assert predicate != null;
        this.gameData = gameData;
        this.args = args;
        this.predicate = predicate;
        this.errorMessage = errorMessage;
    }

    public void check() throws IllegalCommandException {
        if (!predicate.test(gameData, args))
            throw new IllegalCommandException(errorMessage);
    }
}
