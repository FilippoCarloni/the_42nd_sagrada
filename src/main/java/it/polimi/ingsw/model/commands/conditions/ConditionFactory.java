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
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

public final class ConditionFactory {

    private ConditionFactory() {}

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
                return new FreeSlot();
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
            default:
        }
        throw new IllegalArgumentException("Passed string doesn't identify any valid condition.");
    }
}
