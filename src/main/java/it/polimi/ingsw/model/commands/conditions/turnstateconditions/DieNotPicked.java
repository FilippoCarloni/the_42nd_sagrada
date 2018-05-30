package it.polimi.ingsw.model.commands.conditions.turnstateconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.gamedata.GameData;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_DIE_PICKED;

public class DieNotPicked extends Condition {

    public DieNotPicked(GameData gameData) {
        super(gameData, new int[0], ERR_DIE_PICKED);
    }

    @Override
    public ConditionPredicate getPredicate() {
        return (gs, args) -> gs.getPickedDie() == null;
    }
}
