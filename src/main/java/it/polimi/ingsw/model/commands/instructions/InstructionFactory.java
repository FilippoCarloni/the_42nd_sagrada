package it.polimi.ingsw.model.commands.instructions;

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
            default:
                throw new IllegalArgumentException("Passed string doesn't identify any valid instruction.");
        }
    }
}
