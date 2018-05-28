package it.polimi.ingsw.model.commands.conditions;

import it.polimi.ingsw.model.gamedata.GameData;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_FAVOR_POINTS;

public class FavorPointsCheck extends Condition {

    private static final ConditionPredicate predicate = (gs, args) -> {
        int cardFavorPoints = gs.getTools().get(args[0]).getFavorPoints();
        if (cardFavorPoints == 0)
            return gs.getTurnManager().getCurrentPlayer().getFavorPoints() >= 1;
        return gs.getTurnManager().getCurrentPlayer().getFavorPoints() >= 2;
    };

    public FavorPointsCheck(GameData gameData, int[] args) {
        super(gameData, args, predicate, ERR_FAVOR_POINTS);
    }

}
