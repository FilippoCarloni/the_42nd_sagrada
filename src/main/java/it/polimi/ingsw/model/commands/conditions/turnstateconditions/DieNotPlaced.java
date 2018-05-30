package it.polimi.ingsw.model.commands.conditions.turnstateconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.gamedata.GameData;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_DIE_PLACED;

public class DieNotPlaced extends Condition {

    public DieNotPlaced(GameData gameData) {
        super(gameData, new int[0], ERR_DIE_PLACED);
    }

    @Override
    public ConditionPredicate getPredicate() {
        return (gs, args) -> !gs.isDiePlaced();
    }
}
