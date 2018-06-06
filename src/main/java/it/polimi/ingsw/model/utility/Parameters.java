package it.polimi.ingsw.model.utility;

/**
 * This class holds default Sagrada parameters.
 * Any change to this class may affect the correct behavior of the game, so be careful!
 */
public final class Parameters {

    private Parameters() {}

    public static final int TOTAL_NUMBER_OF_ROUNDS = 10;
    public static final int MAX_ROWS = 4;
    public static final int MAX_COLUMNS = 5;
    public static final int MAX_PLAYERS = 4;
    public static final int PUBLIC_OBJECTIVES = 3;
    public static final int TOOL_CARDS = 3;
    public static final int NUM_OF_WINDOWS_PER_PLAYER_BEFORE_CHOICE = 4;

    public static final boolean USE_COMPLETE_RULES = true;

    public static final String WINDOW_PATTERNS_PATH = "src/main/java/res/window_patterns/";
    public static final String BASIC_COMMANDS_PATH = "src/main/java/res/commands/basic_commands.json";
    public static final String TOOL_ACTIVATOR_PATH = "src/main/java/res/commands/tool_card_activator.json";
    public static final String SIMPLIFIED_RULES_TOOLS_PATH = "src/main/java/res/cards/simplified_rules_tool_cards.json";
    public static final String COMPLETE_RULES_TOOLS_PATH = "src/main/java/res/cards/complete_rules_tool_cards.json";
    public static final String PRIVATE_OBJECTIVES_PATH = "src/main/java/res/cards/private_objectives.json";
    public static final String PUBLIC_OBJECTIVES_PATH = "src/main/java/res/cards/public_objectives.json";
}
