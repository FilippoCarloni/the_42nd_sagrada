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

    public static final String WINDOW_PATTERNS_PATH = "src/main/java/res/window_patterns/";
    public static final String BASIC_COMMANDS_PATH = "src/main/java/res/commands/basic_commands.json";
    public static final String TOOL_ACTIVATOR_PATH = "src/main/java/res/commands/tool_card_activator.json";
    public static final String TOOLS_PATH = "src/main/java/res/commands/tool_cards.json";
}
