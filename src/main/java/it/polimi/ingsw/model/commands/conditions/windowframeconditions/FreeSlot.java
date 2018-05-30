package it.polimi.ingsw.model.commands.conditions.windowframeconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.gamedata.GameData;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_SLOT_ALREADY_OCCUPIED;

public class FreeSlot extends Condition {

    public FreeSlot(GameData gameData, int[] args) {
        super(gameData, args, ERR_SLOT_ALREADY_OCCUPIED);
    }

    @Override
    public ConditionPredicate getPredicate() {
        return (gs, args) -> gs.getTurnManager().getCurrentPlayer().getWindowFrame().isEmpty(args[0], args[1]);
    }
}
