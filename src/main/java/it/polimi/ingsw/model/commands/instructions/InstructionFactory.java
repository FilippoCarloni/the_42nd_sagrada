package it.polimi.ingsw.model.commands.instructions;

import it.polimi.ingsw.model.commands.conditions.ConditionFactory;
import it.polimi.ingsw.model.commands.instructions.gamedatainstructions.AdvanceGame;
import it.polimi.ingsw.model.commands.instructions.gamedatainstructions.ReRollPool;
import it.polimi.ingsw.model.commands.instructions.gamedatainstructions.SwapFromRoundTrack;
import it.polimi.ingsw.model.commands.instructions.gamedatainstructions.TakeTwoTurns;
import it.polimi.ingsw.model.commands.instructions.pickeddieinstructions.*;
import it.polimi.ingsw.model.commands.instructions.toolsinstructions.ManageFavorPoints;
import it.polimi.ingsw.model.commands.instructions.toolsinstructions.SetToolCardActivated;
import it.polimi.ingsw.model.commands.instructions.toolsinstructions.TearDown;
import it.polimi.ingsw.model.commands.instructions.toolsinstructions.TearDownPassiveTools;
import it.polimi.ingsw.model.commands.instructions.windowframeinstructions.MoveDie;
import it.polimi.ingsw.model.utility.JSONTag;
import org.json.simple.JSONObject;

import static it.polimi.ingsw.model.commands.instructions.InstructionID.*;

public class InstructionFactory {

    private InstructionFactory() {}

    public static Instruction getInstruction(JSONObject obj) {
        switch (obj.get(JSONTag.INSTRUCTION).toString()) {
            case INS_PICK_DIE_FROM_POOL:
                return new PickDieFromPool();
            case INS_ADVANCE_GAME:
                return new AdvanceGame();
            case INS_PLACE_PICKED_DIE:
                return new PlacePickedDie();
            case INS_SET_TOOL_ACTIVATED:
                return new SetToolCardActivated();
            case INS_MANAGE_FAVOR_POINTS:
                return new ManageFavorPoints();
            case INS_TEAR_DOWN_PASSIVE_TOOLS:
                return new TearDownPassiveTools();
            case INS_SET_SHADE_OF_PICKED_DIE:
                return new SetShadeOfPickedDie(obj.get(ARGUMENT).toString());
            case INS_TEAR_DOWN:
                Object condition = obj.get(CONDITION);
                if (condition == null)
                    return new TearDown(null);
                return new TearDown(ConditionFactory.getCondition((JSONObject) condition));
            case INS_MOVE_DIE:
                return new MoveDie();
            case INS_SWAP_FROM_ROUND_TRACK:
                return new SwapFromRoundTrack();
            case INS_ROLL_DIE:
                return new RollDie();
            case INS_RE_ROLL_POOL:
                return new ReRollPool();
            case INS_TAKE_TWO_TURNS:
                return new TakeTwoTurns();
            case INS_PICK_DIE_FROM_BAG:
                return new PickDieFromDiceBag();
            case INS_RETURN_DIE_TO_BAG:
                return new ReturnDieToDiceBag();
            default:
                throw new IllegalArgumentException("Passed string doesn't identify any valid instruction.");
        }
    }
}
