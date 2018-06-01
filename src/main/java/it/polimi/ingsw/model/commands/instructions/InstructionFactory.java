package it.polimi.ingsw.model.commands.instructions;

import it.polimi.ingsw.model.commands.instructions.pickeddieinstructions.PickDieFromPool;
import it.polimi.ingsw.model.commands.instructions.pickeddieinstructions.PlacePickedDie;
import it.polimi.ingsw.model.commands.instructions.pickeddieinstructions.SetShadeOfPickedDie;
import it.polimi.ingsw.model.commands.instructions.toolsinstructions.ManageFavorPoints;
import it.polimi.ingsw.model.commands.instructions.toolsinstructions.SetToolCardActivated;
import it.polimi.ingsw.model.commands.instructions.toolsinstructions.TearDown;
import it.polimi.ingsw.model.commands.instructions.toolsinstructions.TearDownPassiveTools;
import it.polimi.ingsw.model.utility.JSONTag;
import org.json.simple.JSONObject;

public class InstructionFactory {

    private InstructionFactory() {}

    public static Instruction getInstruction(JSONObject obj) {
        switch (obj.get(JSONTag.INSTRUCTION).toString()) {
            case InstructionID.PICK_DIE_FROM_POOL:
                return new PickDieFromPool();
            case InstructionID.ADVANCE_GAME:
                return new AdvanceGame();
            case InstructionID.PLACE_PICKED_DIE:
                return new PlacePickedDie();
            case InstructionID.SET_TOOL_ACTIVATED:
                return new SetToolCardActivated();
            case InstructionID.MANAGE_FAVOR_POINTS:
                return new ManageFavorPoints();
            case InstructionID.TEAR_DOWN_PASSIVE_TOOLS:
                return new TearDownPassiveTools();
            case InstructionID.SET_SHADE_OF_PICKED_DIE:
                return new SetShadeOfPickedDie(obj.get(InstructionID.ARGUMENT).toString());
            case InstructionID.TEAR_DOWN:
                return new TearDown();
            case InstructionID.MOVE_DIE:
                return new MoveDie();
            default:
                throw new IllegalArgumentException("Passed string doesn't identify any valid instruction.");
        }
    }
}
