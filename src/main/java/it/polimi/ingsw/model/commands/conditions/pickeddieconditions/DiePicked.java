package it.polimi.ingsw.model.commands.conditions.pickeddieconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.gamedata.GameData;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_DIE_NOT_PICKED;

public class DiePicked extends Condition {

    public DiePicked(GameData gameData) {
        super(gameData, new int[0], ERR_DIE_NOT_PICKED);
    }

    @Override
    public ConditionPredicate getPredicate() {
        return (gs, args) -> gs.getPickedDie() != null;
    }
}
