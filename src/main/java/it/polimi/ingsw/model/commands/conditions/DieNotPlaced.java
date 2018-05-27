package it.polimi.ingsw.model.commands.conditions;

import it.polimi.ingsw.model.gamedata.GameData;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_DIE_PLACED;

public class DieNotPlaced extends Condition {

    private static final ConditionPredicate predicate = (gs, args) -> !gs.isDiePlaced();

    public DieNotPlaced(GameData gameData) {
        super(gameData, new int[0], predicate, ERR_DIE_PLACED);
    }
}
