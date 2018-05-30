package it.polimi.ingsw.model.commands.conditions.indexconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.gamedata.GameData;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_INDEX_TOO_BIG;

public class IndexSmallerThan extends Condition {

    private int index;
    private int bound;

    public IndexSmallerThan(GameData gameData, int[] args, int index, int bound) {
        super(gameData, args, ERR_INDEX_TOO_BIG);
        this.index = index;
        this.bound = bound;
    }

    @Override
    public ConditionPredicate getPredicate() {
        return (gs, args) -> args[index] < bound;
    }
}
