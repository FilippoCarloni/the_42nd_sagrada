package it.polimi.ingsw.model.commands.conditions;

import it.polimi.ingsw.model.gamedata.GameData;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_DIE_NOT_PICKED;

public class DiePicked extends Condition {

    private static final ConditionPredicate predicate = (gs, args) -> gs.getPickedDie() != null;

    public DiePicked(GameData gameData) {
        super(gameData, new int[0], predicate, ERR_DIE_NOT_PICKED);
    }
}
