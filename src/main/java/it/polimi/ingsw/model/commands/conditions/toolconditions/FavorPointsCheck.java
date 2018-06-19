package it.polimi.ingsw.model.commands.conditions.toolconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolCard;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_FAVOR_POINTS;
import static it.polimi.ingsw.model.utility.ExceptionMessage.NEGATIVE_INTEGER;

/**
 * Checks if the player has enough favor points to activate the chosen Tool Card.
 */
public class FavorPointsCheck implements Condition {

    @Override
    public ConditionPredicate getPredicate() {
        return (gs, args) -> {
            int cardFavorPoints = -1;
            for (ToolCard c : gs.getTools())
                if (c.getID() == args[0] + 1)
                    cardFavorPoints = c.getFavorPoints();
            if (cardFavorPoints < 0)
                throw new IllegalArgumentException(NEGATIVE_INTEGER);
            if (cardFavorPoints == 0)
                return gs.getTurnManager().getCurrentPlayer().getFavorPoints() >= 1;
            return gs.getTurnManager().getCurrentPlayer().getFavorPoints() >= 2;
        };
    }

    @Override
    public String getErrorMessage() {
        return ERR_FAVOR_POINTS;
    }
}
