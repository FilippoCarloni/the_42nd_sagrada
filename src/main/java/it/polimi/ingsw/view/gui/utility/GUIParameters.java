package it.polimi.ingsw.view.gui.utility;

import javafx.scene.paint.Color;

/**
 * This class holds all the default parameters for the graphical visualization of Sagrada game.
 * Any change to this class may affect the correct behavior of the game, so be careful!
 */

public final class GUIParameters {

    private GUIParameters(){}

    //Scenes titles
    public static final String CONNECTION_LOGIN_SCENE_TITLE = "Login";
    public static final String LOBBY_TITLE = "Lobby";
    public static final String MAP_CHOICE_SCENE_TITLE = "Map Choice";
    public static final String MAIN_SCENE_TITLE = "Sagrada Board Game";
    public static final String ROUND_TRACK_TITLE = "Dice on Round Track";
    public static final String PRIVATE_OBJECTIVE_TITLE = "Private Objective Card";
    public static final String GROZING_PLIERS_TITLE = "Grozing Pliers Activated";
    public static final String FLUX_REMOVER_TITLE = "Flux Remover Activated";
    public static final String END_GAME_TITLE = "Game Stats";

    //Maps constraints
    public static final int NUM_MAPS_TO_CHOOSE = 4;
    public static final int MAX_WINDOW_FRAMES_ROWS = 4;
    public static final int MAX_WINDOW_FRAMES_COLUMNS = 5;

    //Constraints
    public static final int MAX_NUM_TURNS = 10;
    public static final int NUM_SHADES = 6;

    //Style
    public static final String BACKGROUND_COLOR_STRING = "-fx-background-color: ";
    public static final String BORDER_COLOR_STRING = "-fx-border-color: ";
    public static final String DEFAULT_GRID_COLOR = "white";
    public static final String EMPTY_FROM_CONSTRAINTS_COLOR = "default";
    public static final Color NUMBERS_DICE_COLOR = Color.BLACK;
    public static final String DEFAULT_DICE_COLOR = "white";
    public static final String BACKGROUND_COLOR = "#303642";
    public static final String BORDER_COLOR = "black";
    public static final String CLICKABLE_STYLE = "clickable";
    public static final String USED_TOOL = "used-tool-title-label";

    //Dimensions to draw dice
    public static final double DICE_RADIUS = 12.5;
    public static final double SQUARE_PLAYER_1_GRID_DIMENSION = 60;
    static final double[] OFFSET_DIE_1 = new double[]{30.0, 30.0};
    static final double[] OFFSET_DIE_2 = new double[]{15.0, 15.0, 45.0, 45.0};
    static final double[] OFFSET_DIE_3 = new double[]{45.0, 15.0, 30.0, 30.0, 15.0, 45.0};
    static final double[] OFFSET_DIE_4 = new double[]{15.0, 15.0, 45.0, 15.0, 45.0, 45.0, 15.0, 45.0};
    static final double[] OFFSET_DIE_5 = new double[]{15.0, 15.0, 45.0, 15.0, 45.0, 45.0, 15.0, 45.0, 30.0, 30.0};
    static final double[] OFFSET_DIE_6 = new double[]{15.0, 15.0, 45.0, 15.0, 15.0, 30.0, 45.0, 30.0, 15.0, 45.0, 45.0, 45.0};

    //Reduction scales for dice drawing
    public static final double NO_SCALE = 1;
    public static final double REDUCTION_FOR_OTHER_PLAYERS = 0.67;
    public static final double REDUCTION_FOR_ROUND_TRACK = 0.9;
    public static final double INCREMENT_FOR_DRAFTED_DIE = 1.5;

    //Directories for fxml files loading
    public static final String DEFAULT_FXML_DIRECTORY = "/FXML_files/";
    public static final String CONNECTION_LOGIN_FXML_PATH = "ConnectionLogin.fxml";
    public static final String LOBBY_FXML_PATH = "Lobby.fxml";
    public static final String MAP_CHOICE_FXML_PATH = "WindowFrameChoice.fxml";
    public static final String GAME_BOARD_FXML_PATH = "MainBoard.fxml";
    public static final String ROUND_TRACK_DICE_FXML_PATH = "RoundTrackVisualizer.fxml";
    public static final String GROZING_PLIERS_FXML_PATH = "GrozingPliersScreen.fxml";
    public static final String FLUX_REMOVER_FXML_PATH = "FluxRemoverScreen.fxml";
    public static final String END_GAME_FXML_PATH = "EndGameScreen.fxml";
    public static final String PRIVATE_OBJECTIVE_FXML_PATH = "PrivateObjectiveFavorPoints.fxml";

    //Error messages
    public static final String SERVER_ERROR = "Server not reachable\nTry again later";
    public static final String LOGIN_ERROR = "Username not valid, insert a new one";
    public static final String MAIN_PLAYER_NOT_FOUND = "Main player not found";
    public static final String DIE_VALUE_SMALL = "Shade of die must be at least 1";
    public static final String DIE_VALUE_BIG = "Shade of die must be at most 6";
    static final String MESSAGE_ERROR = "Message not supported!";
    static final String FIND_ERROR = " not valid, please insert a new one";

    //Connection Controller messages
    public static final String PICK = "pick";
    public static final String MOVE = "move";
    public static final String PLACE = "place";
    public static final String TOOL = "tool";
    public static final String SELECT = "select";
    public static final String INCREASE = "increase";
    public static final String DECREASE = "decrease";
    static final String NOW_PLAYING = "Now is playing: ";
    static final String END_GAME = "\nGame Ended!\nPress Continue to see the score.";

    //Other
    public static final String DIFFICULTY = "Difficulty: ";
    public static final String FAVOR_POINTS = "Favor Points: ";
    public static final String SEPARATOR = " - ";
    public static final int FIRST_COLUMN_ROW = 0;
    public static final int SECOND_COLUMN_ROW = 1;
    public static final int STARTING_POINT = 0;
    public static final int STARTING_INDEX_VALUE = -1;
    public static final int NUM_POINTS_DICE_POLYLINE = 5;


}