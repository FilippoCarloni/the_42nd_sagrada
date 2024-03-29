package it.polimi.ingsw.model.commands.conditions;

/**
 * Holds the JSON tags for condition fetching.
 */
public class ConditionID {

    private ConditionID() {}

    static final String CON_ARG_COMPARISON = "arg_comparison";
    static final String CON_DIE_PICKED = "die_picked";
    static final String CON_SHADE_COMPARISON = "shade_comparison";
    static final String CON_DIE_PLACED = "die_placed";
    static final String CON_TOOL_NOT_ACTIVE = "tool_not_active";
    static final String CON_TOOL_NOT_ACTIVATED = "tool_not_activated";
    static final String CON_ENOUGH_FAVOR_POINTS = "favor_points_check";
    static final String CON_FIRST_TURN = "first_turn";
    static final String CON_PLACING_RULE_CHECK = "placing_rule_check";
    static final String CON_VALID_COORDINATES = "valid_coordinates";
    static final String CON_FREE_SLOT = "free_slot";
    static final String CON_MOVE_RULE_CHECK = "move_rule_check";
    static final String CON_NUM_OF_DICE_MOVED = "num_of_dice_moved";
    static final String CON_NOT_DIE_ALREADY_MOVED = "not_die_already_moved";
    static final String CON_SHARE = "share";

    // auxiliary attributes
    static final String INDEX = "index";
    static final String BOUND = "bound";
    public static final String GREATER_THAN = "greater_than";
    public static final String SMALLER_THAN = "smaller_than";
    public static final String EQUAL_TO = "equal_to";
    static final String TYPE_OF_COMPARISON = "type_of_comparison";
    public static final String DICE_POOL_SIZE = "dice_pool_size";
    public static final String ROUND_TRACK_SIZE = "round_track_size";
    public static final String TOOL_DECK_SIZE = "tool_deck_size";
    public static final String MAXIMUM_SHADE = "maximum_shade";
    public static final String MINIMUM_SHADE = "minimum_shade";
    static final String PLACING = "placing";
    public static final String COLOR = "color";
    public static final String SHADE = "shade";
    static final String VALUE = "value";
    static final String ROW_INDEX = "row_index";
    static final String COLUMN_INDEX = "column_index";
    public static final String ROUND_TRACK = "round_track";
    public static final String DICE_POOL = "dice_pool";
    public static final String DICE_MOVED = "dice_moved";
    static final String OBJECT = "object";
    static final String POOL = "pool";
    static final String VALUE_IF_EMPTY = "value_if_empty";
}
