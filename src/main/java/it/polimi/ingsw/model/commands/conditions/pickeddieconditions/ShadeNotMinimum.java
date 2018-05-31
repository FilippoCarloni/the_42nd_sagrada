package it.polimi.ingsw.model.commands.conditions.pickeddieconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.utility.Shade;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_CANNOT_DECREASE;

public class ShadeNotMinimum extends Condition {

    public ShadeNotMinimum(GameData gameData) {
        super(gameData, new int[0], ERR_CANNOT_DECREASE);
    }

    @Override
    public ConditionPredicate getPredicate() {
        return (gameData, args) -> gameData.getPickedDie().getShade().getValue() > Shade.getMinimumValue();
    }
}
