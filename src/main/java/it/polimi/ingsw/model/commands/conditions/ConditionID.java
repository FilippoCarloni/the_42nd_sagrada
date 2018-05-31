package it.polimi.ingsw.model.commands.conditions;

public class ConditionID {

    private ConditionID() {}

    public static final String DIE_NOT_PICKED = "die_not_picked";
    public static final String DIE_PICKED = "die_picked";
    public static final String DIE_NOT_PLACED = "die_not_placed";
    public static final String TOOL_NOT_ACTIVE = "tool_not_active";
    public static final String TOOL_NOT_ACTIVATED = "tool_not_activated";
    public static final String INDEX_GREATER_THAN = "index_greater_than";
    public static final String INDEX_SMALLER_THAN = "index_smaller_than";
    public static final String VALID_COORDINATES = "valid_coordinates";
    public static final String FREE_SLOT = "free_slot";
    public static final String PLACING_RULE_CHECK = "placing_rule";
    public static final String FIRST_TURN = "first_turn";
    public static final String SECOND_TURN = "second_turn";
    public static final String SHADE_NOT_MAXIMUM = "shade_not_maximum";
    public static final String SHADE_NOT_MINIMUM = "shade_not_minimum";
    public static final String ENOUGH_FAVOR_POINTS = "favor_points_check";

    // auxiliary indexes
    public static final String INDEX = "index";
    public static final String BOUND = "bound";
    public static final String DICE_POOL_SIZE = "dice_pool_size";
    public static final String ROUND_TRACK_SIZE = "round_track";
    public static final String TOOL_CARDS_SIZE = "tool_cards_size";
    public static final String EXCLUDE = "exclude";
    public static final String NOTHING = "nothing";
}
