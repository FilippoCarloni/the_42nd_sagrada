package it.polimi.ingsw.model.commands.instructions;

public final class InstructionID {

    private InstructionID() {}

    public static final String PICK_DIE_FROM_POOL = "pick_die_from_dice_pool";
    public static final String ADVANCE_GAME = "advance_game";
    public static final String PLACE_PICKED_DIE = "place_die";
    public static final String SET_TOOL_ACTIVATED = "activate_tool";
    public static final String MANAGE_FAVOR_POINTS = "manage_favor_points";
    public static final String TEAR_DOWN_PASSIVE_TOOLS = "tear_down_passive_tools";
    public static final String SET_SHADE_OF_PICKED_DIE = "set_shade_of_picked_die";
    public static final String TEAR_DOWN = "tear_down";
    public static final String MOVE_DIE = "move_die";

    // helper tags
    public static final String ARGUMENT = "argument";
    public static final String INCREASE = "increase";
    public static final String DECREASE = "decrease";
    public static final String NO_CONSTRAINT = "no_constraint";
}
