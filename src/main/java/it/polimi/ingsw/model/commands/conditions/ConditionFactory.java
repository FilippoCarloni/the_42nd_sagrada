package it.polimi.ingsw.model.commands.conditions;

import it.polimi.ingsw.model.commands.conditions.argconditions.ArgComparison;
import it.polimi.ingsw.model.commands.conditions.pickeddieconditions.DiePicked;
import it.polimi.ingsw.model.commands.conditions.pickeddieconditions.ShadeComparison;
import it.polimi.ingsw.model.commands.conditions.toolconditions.FavorPointsCheck;
import it.polimi.ingsw.model.commands.conditions.toolconditions.ToolNotActivated;
import it.polimi.ingsw.model.commands.conditions.toolconditions.ToolNotActive;
import it.polimi.ingsw.model.commands.conditions.turnstateconditions.*;
import it.polimi.ingsw.model.commands.conditions.windowframeconditions.*;
import it.polimi.ingsw.model.utility.JSONTag;
import org.json.simple.JSONObject;

import static it.polimi.ingsw.model.commands.conditions.ConditionID.*;
import static it.polimi.ingsw.model.utility.ExceptionMessage.BAD_JSON;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

/**
 * Generates condition instances from JSON encoding.
 */
public final class ConditionFactory {

    private ConditionFactory() {}

    /**
     * Generates a condition instance form the information contained in a JSON object.
     * @param obj The JSON object that contains the information about the condition
     * @return An Condition instance
     */
    public static Condition getCondition(JSONObject obj) {

        switch (obj.get(JSONTag.CONDITION).toString()) {
            case CON_ARG_COMPARISON:
                return new ArgComparison(obj.get(TYPE_OF_COMPARISON).toString(), parseInt(obj.get(INDEX).toString()),obj.get(BOUND).toString());
            case CON_DIE_PICKED:
                return new DiePicked(parseBoolean(obj.get(VALUE).toString()));
            case CON_SHADE_COMPARISON:
                return new ShadeComparison(obj.get(TYPE_OF_COMPARISON).toString(), obj.get(BOUND).toString());
            case CON_ENOUGH_FAVOR_POINTS:
                return new FavorPointsCheck();
            case CON_TOOL_NOT_ACTIVATED:
                return new ToolNotActivated();
            case CON_TOOL_NOT_ACTIVE:
                return new ToolNotActive();
            case CON_DIE_PLACED:
                return new DiePlaced(parseBoolean(obj.get(VALUE).toString()));
            case CON_FIRST_TURN:
                return new FirstTurnOfRound(parseBoolean(obj.get(VALUE).toString()));
            case CON_FREE_SLOT:
                return new FreeSlot(parseInt(obj.get(ROW_INDEX).toString()), parseInt(obj.get(COLUMN_INDEX).toString()), parseBoolean(obj.get(VALUE).toString()));
            case CON_PLACING_RULE_CHECK:
                return new Place(parseBoolean(obj.get(PLACING).toString()), parseBoolean(obj.get(COLOR).toString()), parseBoolean(obj.get(SHADE).toString()));
            case CON_MOVE_RULE_CHECK:
                return new Move(parseBoolean(obj.get(PLACING).toString()), parseBoolean(obj.get(COLOR).toString()), parseBoolean(obj.get(SHADE).toString()));
            case CON_VALID_COORDINATES:
                return new ValidCoordinates();
            case CON_NUM_OF_DICE_MOVED:
                return new NumberOfDiceMoved(obj.get(TYPE_OF_COMPARISON).toString(), obj.get(BOUND).toString());
            case CON_NOT_DIE_ALREADY_MOVED:
                return new NotDieAlreadyMoved();
            case CON_SHARE:
                return new Share(obj.get(OBJECT).toString(), obj.get(POOL).toString(), parseBoolean(obj.get(VALUE_IF_EMPTY).toString()));
            default:
        }
        throw new IllegalArgumentException(BAD_JSON);
    }
}
