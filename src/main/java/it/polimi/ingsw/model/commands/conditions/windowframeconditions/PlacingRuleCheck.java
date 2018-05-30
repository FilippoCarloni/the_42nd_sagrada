package it.polimi.ingsw.model.commands.conditions.windowframeconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.commands.rules.Rule;
import it.polimi.ingsw.model.gamedata.GameData;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_RULE_ERROR;

public class PlacingRuleCheck extends Condition {

    public PlacingRuleCheck(GameData gameData, int[] args) {
        super(gameData, args, ERR_RULE_ERROR);
    }

    @Override
    public ConditionPredicate getPredicate() {
        return (gd, args) -> Rule.checkAllRules(gd.getPickedDie(), gd.getTurnManager().getCurrentPlayer().getWindowFrame(), args[0], args[1]);
    }
}
