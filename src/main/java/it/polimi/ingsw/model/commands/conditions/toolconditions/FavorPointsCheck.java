package it.polimi.ingsw.model.commands.conditions.toolconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.gamedata.GameData;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_FAVOR_POINTS;

public class FavorPointsCheck extends Condition {

    public FavorPointsCheck(GameData gameData, int[] args) {
        super(gameData, args, ERR_FAVOR_POINTS);
    }

    @Override
    public ConditionPredicate getPredicate() {
        return (gs, args) -> {
            int cardFavorPoints = gs.getTools().get(args[0]).getFavorPoints();
            if (cardFavorPoints == 0)
                return gs.getTurnManager().getCurrentPlayer().getFavorPoints() >= 1;
            return gs.getTurnManager().getCurrentPlayer().getFavorPoints() >= 2;
        };
    }
}
