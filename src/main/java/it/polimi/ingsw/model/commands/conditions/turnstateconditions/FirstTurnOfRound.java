package it.polimi.ingsw.model.commands.conditions.turnstateconditions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.conditions.ConditionPredicate;
import it.polimi.ingsw.model.gamedata.GameData;

import static it.polimi.ingsw.model.commands.ErrorMessage.ERR_SECOND_TURN_OF_ROUND;

public class FirstTurnOfRound extends Condition {

    public FirstTurnOfRound(GameData gameData) {
        super(gameData, new int[0], ERR_SECOND_TURN_OF_ROUND);
    }

    @Override
    public ConditionPredicate getPredicate() {
        return (gameData, args) -> !gameData.getTurnManager().isSecondTurn();
    }
}
