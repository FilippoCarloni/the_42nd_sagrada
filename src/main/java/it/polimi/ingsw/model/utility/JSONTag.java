package it.polimi.ingsw.model.utility;

/**
 * This class contains all the tag string constants that are used to encode and decode game
 * information from JSON objects.
 */
public final class JSONTag {

    JSONTag() {}

    // ArrayTurnManager tags
    public static final String ROUND_STARTING = "round_starting";
    public static final String ROUND_ENDING = "round_ending";
    public static final String TURN_INDEX = "turn_index";
    public static final String FIRST_PLAYER_INDEX = "first_player_index";
    public static final String PLAYERS = "players";
    public static final String PLAYER_TURNS = "player_turns";

    // ConcretePlayer tags
    public static final String USERNAME = "username";
    public static final String FAVOR_POINTS = "favor_points";
    public static final String WINDOW_FRAME = "window_frame";
    public static final String PRIVATE_OBJECTIVE = "private objective";

    // ConcreteGameData tags
    public static final String DICE_POOL = "dice_pool";
    public static final String DICE_BAG = "dice_bag";
    public static final String ROUND_TRACK = "round_track";
    public static final String PUBLIC_OBJECTIVES = "public_objectives";
    public static final String TOOLS = "tools";
    public static final String TURN_MANAGER = "turn_manager";
    public static final String PICKED_DIE = "picked_die";
    public static final String DIE_PLACED = "die_placed";
    public static final String ACTIVE_TOOL_ID = "active_tool_ID";
    public static final String PASSIVE_TOOL_ID = "passive_tool_ID";
    public static final String TOOL_ACTIVATED = "tool_activated";
    public static final String DICE_MOVED = "dice_moved";
    public static final String UNDO_AVAILABLE = "undo_available";

    // PaperWindowFrame tags
    public static final String NAME = "name";
    public static final String DIFFICULTY = "difficulty";
    public static final String COORDINATES = "coordinates";
    public static final String ROW_INDEX = "row_index";
    public static final String COLUMN_INDEX = "column_index";
    public static final String DIE = "die";
    public static final String COLOR_CONSTRAINT = "color_constraint";
    public static final String SHADE_CONSTRAINT = "shade_constraint";

    // PaperRoundTrack tags
    public static final String CURRENT_ROUND_NUMBER = "current_round_number";
    public static final String ALL_DICE = "all_dice";
    public static final String NUMBER_OF_DICE_ON_SLOT = "number_of_dice_on_slot";

    // PlasticDie tags
    public static final String COLOR = "color";
    public static final String SHADE = "shade";
    public static final String DIE_ID = "id";

    // ArrayDiceBag tags
    public static final String DICE = "dice";

    // AbstractCard tags
    public static final String CARD_ID = "id";

    // AbstractToolCard tags
    public static final String TOOL_FAVOR_POINTS = "favor_points";

    // Commands
    public static final String REGEXP = "reg_exp";
    public static final String UNDOABLE = "undoable";
    public static final String CONDITIONS = "conditions";
    public static final String INSTRUCTIONS = "instructions";
    public static final String CONDITION = "condition";
    public static final String INSTRUCTION = "instruction";
    public static final String COMMANDS = "commands";

    // Tool cards
    public static final String TOOL_CARDS = "tool_cards";
    public static final String DESCRIPTION = "description";
    public static final String ACTIVE = "active";
    public static final String ACTIVATOR = "activator";

    // private objectives
    public static final String INDEX = "index";
    public static final String BASE_ID = "base_id";

    // public objectives
    public static final String OBJECT = "object";
    public static final String VALUES = "values";
    public static final String TYPE = "type";
    public static final String PLACE = "place";
    public static final String PATTERN = "pattern";
    public static final String POINTS_PER_PATTERN = "points_per_pattern";
}
