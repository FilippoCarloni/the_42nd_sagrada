package it.polimi.ingsw.view.gui.settings;

import javafx.scene.paint.Color;

//TODO: refactor where needed

public final class GUIParameters {

    private GUIParameters(){}

    //Scenes titles
    public static final String CONNECTION_LOGIN_SCENE_TITLE = "Login";
    public static final String LOBBY_TITLE = "Lobby";
    public static final String MAP_CHOICE_SCENE_TITLE = "Map Choice";
    public static final String MAIN_SCENE_TITLE = "Sagrada Board Game";
    public static final String ROUND_TRACK_TITLE = "Dice on Round Track";
    public static final String PRIVATE_OBJECTIVE_TITLE = "Private Objective";
    public static final String GROZING_PLIERS_TITLE = "Grozing Pliers Activated";
    public static final String FLUX_REMOVER_TITLE = "Flux Remover Activated";

    //Scenes dimensions
    public static final double PRIVATE_OBJECTIVE_SCENE_WIDTH = 240;
    public static final double PRIVATE_OBJECTIVE_SCENE_HEIGHT = 400;
    public static final double TOOL_CARDS_WIDTH = 400;
    public static final double TOOL_CARDS_HEIGHT = 300;

    //Dice dimension
    public static final double SQUARE_PLAYER_1_GRID_DIMENSION = 60;
    public static final double REDUCTION_SCALE = 0.67;
    public static final double REDUCTION_FOR_ROUND_TRACK = 0.9;

    //Maps constraints
    public static final int NUM_MAPS_TO_CHOOSE = 4;
    public static final int MAX_WINDOW_FRAMES_ROWS = 4;
    public static final int MAX_WINDOW_FRAMES_COLUMNS = 5;

    //Constraints
    public static final int MAX_NUM_TURNS = 10;
    public static final int NUM_SHADES = 6;

    //Colors
    public static final String BACKGROUND_COLOR_STRING = "-fx-background-color: ";
    public static final String DEFAULT_GRID_COLOR = "white";
    public static final String EMPTY_FROM_CONSTRAINTS_COLOR = "default";
    public static final Color NUMBERS_DICE_COLOR = Color.BLACK;
    public static final String DEFAULT_DICE_COLOR = "white";
    public static final String BACKGROUND_COLOR = "#303642";

    //Dimensions to draw dice
    public static final double DICE_RADIUS = 12.5;
    public static final double[] OFFSET_DIE_1 = new double[]{30.0, 30.0};
    public static final double[] OFFSET_DIE_2 = new double[]{15.0, 15.0, 45.0, 45.0};
    public static final double[] OFFSET_DIE_3 = new double[]{45.0, 15.0, 30.0, 30.0, 15.0, 45.0};
    public static final double[] OFFSET_DIE_4 = new double[]{15.0, 15.0, 45.0, 15.0, 45.0, 45.0, 15.0, 45.0};
    public static final double[] OFFSET_DIE_5 = new double[]{15.0, 15.0, 45.0, 15.0, 45.0, 45.0, 15.0, 45.0, 30.0, 30.0};
    public static final double[] OFFSET_DIE_6 = new double[]{15.0, 15.0, 45.0, 15.0, 15.0, 30.0, 45.0, 30.0, 15.0, 45.0, 45.0, 45.0};

    //Directories for cards loading
    public static final String DEFAULT_DIRECTORY = "src/main/java/res/images/cards/";
    public static final String TOOL_DIRECTORY = "toolcards";
    public static final String PUBOBJ_DIRECTORY = "pubobjcards";
    public static final String PRIV_OBJ_DIRECTORY = "privobjcards";

    //Directories for fxml files loading
    public static final String DEFAULT_FXML_DIRECTORY = "/FXML_files/";
    public static final String CONNECTION_LOGIN_FXML_PATH = "ConnectionLogin.fxml";
    public static final String LOBBY_FXML_PATH = "Lobby.fxml";
    public static final String MAP_CHOICE_FXML_PATH = "WindowFrameChoice.fxml";
    public static final String GAME_BOARD_FXML_PATH = "MainBoard.fxml";
    public static final String ROUND_TRACK_DICE_FXML_PATH = "RoundTrackVisualizer.fxml";
    public static final String PRIVATE_OBJECTIVE_FXML_PATH = "PrivateObjectiveScreen.fxml";
    public static final String GROZING_PLIERS_FXML_PATH = "GrozingPliersScreen.fxml";
    public static final String FLUX_REMOVER_FXML_PATH = "FluxRemoverScreen.fxml";

    //Error messages
    public static final String SERVER_ERROR = "Server not reachable\nTry again later";
    public static final String LOGIN_ERROR = "Username not valid, insert a new one";
    public static final String MAIN_PLAYER_NOT_FOUND = "Main player not found";
    public static final String DIE_VALUE_SMALL = "Shade of die must be at least 1";

    //Connection Controller messages
    public static final String PLAY = "play";
    public static final String EXIT = "exit";
    public static final String WINDOW = "window ";
    public static final String PASS = "pass";
    public static final String UNDO = "undo";
    public static final String REDO = "redo";
    public static final String PICK = "pick ";
    public static final String MOVE = "move ";
    public static final String PLACE = "place ";
    public static final String SELECT = "select ";
    public static final String INCREASE = "increase";
    public static final String DECREASE = "decrease";

    //Card dimension
    public static final double CARDS_ON_GAME_BOARD_WIDTH = 180;
    public static final double CARDS_ON_GAME_BOARD_HEIGHT = 215;
    public static final double CARD_ON_MAP_CHOICE_WIDTH = 240;
    public static final double CARD_ON_MAP_CHOICE_HEIGHT = 300;
}