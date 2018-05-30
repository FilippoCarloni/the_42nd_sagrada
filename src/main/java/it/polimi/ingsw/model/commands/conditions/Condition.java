package it.polimi.ingsw.model.commands.conditions;

import it.polimi.ingsw.model.commands.IllegalCommandException;
import it.polimi.ingsw.model.gamedata.GameData;

public abstract class Condition {

    private GameData gameData;
    private int[] args;
    private String errorMessage;

    public Condition(GameData gameData, int[] args, String errorMessage) {
        assert gameData != null;
        assert args != null;
        this.gameData = gameData;
        this.args = args;
        this.errorMessage = errorMessage;
    }

    public abstract ConditionPredicate getPredicate();

    public void check() throws IllegalCommandException {
        if (!getPredicate().test(gameData, args))
            throw new IllegalCommandException(errorMessage);
    }
}
