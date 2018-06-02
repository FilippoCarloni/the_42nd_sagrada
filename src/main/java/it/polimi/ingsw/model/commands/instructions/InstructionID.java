package it.polimi.ingsw.model.commands.instructions;

public final class InstructionID {

    private InstructionID() {}

    static final String INS_PICK_DIE_FROM_POOL = "pick_die_from_dice_pool";
    static final String INS_ADVANCE_GAME = "advance_game";
    static final String INS_PLACE_PICKED_DIE = "place_die";
    static final String INS_SET_TOOL_ACTIVATED = "activate_tool";
    static final String INS_MANAGE_FAVOR_POINTS = "manage_favor_points";
    static final String INS_TEAR_DOWN_PASSIVE_TOOLS = "tear_down_passive_tools";
    static final String INS_SET_SHADE_OF_PICKED_DIE = "set_shade_of_picked_die";
    static final String INS_TEAR_DOWN = "tear_down";
    static final String INS_MOVE_DIE = "move_die";
    static final String INS_SWAP_FROM_ROUND_TRACK = "swap_from_round_track";
    static final String INS_ROLL_DIE = "roll_die";

    // auxiliary attributes
    static final String ARGUMENT = "argument";
    public static final String INCREASE = "increase";
    public static final String DECREASE = "decrease";
    public static final String NO_CONSTRAINT = "no_constraint";
    static final String CONDITION = "condition";
}
