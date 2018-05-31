package it.polimi.ingsw.model.commands.conditions;
import it.polimi.ingsw.model.commands.conditions.indexconditions.IndexGreaterThan;
import it.polimi.ingsw.model.commands.conditions.indexconditions.IndexSmallerThan;
import it.polimi.ingsw.model.commands.conditions.pickeddieconditions.DieNotPicked;
import it.polimi.ingsw.model.commands.conditions.pickeddieconditions.DiePicked;
import it.polimi.ingsw.model.commands.conditions.pickeddieconditions.ShadeNotMaximum;
import it.polimi.ingsw.model.commands.conditions.pickeddieconditions.ShadeNotMinimum;
import it.polimi.ingsw.model.commands.conditions.toolconditions.FavorPointsCheck;
import it.polimi.ingsw.model.commands.conditions.toolconditions.ToolNotActivated;
import it.polimi.ingsw.model.commands.conditions.toolconditions.ToolNotActive;
import it.polimi.ingsw.model.commands.conditions.turnstateconditions.*;
import it.polimi.ingsw.model.commands.conditions.windowframeconditions.FreeSlot;
import it.polimi.ingsw.model.commands.conditions.windowframeconditions.PlacingRuleCheck;
import it.polimi.ingsw.model.commands.conditions.windowframeconditions.ValidCoordinates;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.utility.JSONTag;
import org.json.simple.JSONObject;

import static java.lang.Integer.parseInt;

public final class ConditionFactory {

    private ConditionFactory() {}

    public static Condition getCondition(JSONObject obj, GameData gameData, int[] args) {

        // TODO: refactor condition handling for reducing the dimension of this switch case

        switch (obj.get(JSONTag.CONDITION).toString()) {
            case ConditionID.DIE_NOT_PICKED:
                return new DieNotPicked(gameData);
            case ConditionID.DIE_PICKED:
                return new DiePicked(gameData);
            case ConditionID.DIE_NOT_PLACED:
                return new DieNotPlaced(gameData);
            case ConditionID.TOOL_NOT_ACTIVE:
                return new ToolNotActive(gameData);
            case ConditionID.TOOL_NOT_ACTIVATED:
                return new ToolNotActivated(gameData);
            case ConditionID.INDEX_GREATER_THAN:
                int index = parseInt(obj.get(ConditionID.INDEX).toString());
                int bound = parseInt(obj.get(ConditionID.BOUND).toString());
                return new IndexGreaterThan(gameData, args, index, bound);
            case ConditionID.INDEX_SMALLER_THAN:
                index = parseInt(obj.get(ConditionID.INDEX).toString());
                String boundString = obj.get(ConditionID.BOUND).toString();
                if (boundString.equals(ConditionID.DICE_POOL_SIZE))
                    return new IndexSmallerThan(gameData, args, index, gameData.getDicePool().size());
                else if (boundString.equals(ConditionID.ROUND_TRACK_SIZE))
                    return new IndexSmallerThan(gameData, args, index, gameData.getRoundTrack().getDice().size());
                return new IndexSmallerThan(gameData, args, index, parseInt(boundString));
            case ConditionID.VALID_COORDINATES:
                return new ValidCoordinates(gameData, args);
            case ConditionID.FREE_SLOT:
                return new FreeSlot(gameData, args);
            case ConditionID.PLACING_RULE_CHECK:
                // TODO: add exclusion handling
                return new PlacingRuleCheck(gameData, args);
            case ConditionID.FIRST_TURN:
                return new FirstTurnOfRound(gameData);
            case ConditionID.SECOND_TURN:
                return new SecondTurnOfRound(gameData);
            case ConditionID.SHADE_NOT_MAXIMUM:
                return new ShadeNotMaximum(gameData);
            case ConditionID.SHADE_NOT_MINIMUM:
                return new ShadeNotMinimum(gameData);
            case ConditionID.ENOUGH_FAVOR_POINTS:
                return new FavorPointsCheck(gameData, args);
            default:
                throw new IllegalArgumentException("Passed string doesn't identify any valid condition.");
        }
    }
}
