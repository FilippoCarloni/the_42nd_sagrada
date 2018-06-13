package it.polimi.ingsw.view.gui.settings;

import it.polimi.ingsw.connection.client.ConnectionType;
import it.polimi.ingsw.view.gui.Helper;
import javafx.scene.paint.Color;

public final class GUIParameters {

    private GUIParameters(){}

    //Scenes titles
    public static final String CONNECTION_LOGIN_SCENE_TITLE = "Login";
    public static final String PLAY_OR_QUIT_TITLE = "Play or Quit";
    public static final String LOBBY_TITLE = "Lobby";
    public static final String MAP_CHOICE_SCENE_TITLE = "Map Choice";
    public static final String MAIN_SCENE_TITLE = "Sagrada Board Game";

    //Scenes dimensions
    public static final double CONNECTION_LOGIN_SCENE_WIDTH = 600;
    public static final double CONNECTION_LOGIN_SCENE_HEIGHT = 400;
    public static final double MAP_CHOICE_SCREEN_HEIGHT = 370;
    public static final double SCREEN_WIDTH = 1280;
    public static final double SCREEN_HEIGHT = 720;

    //Elements on Game Board static dimensions
    public static final double PLAYER_1_GRID_X = 77;
    public static final double PLAYER_1_GRID_Y = 402;
    public static final double PLAYER_1_CANVAS_WIDTH = 350;
    public static final double PLAYER_1_CANVAS_HEIGHT = 500;
    public static final double[] BORDER_CANVAS_PLAYER_1_X_POLYLINE = new double[]{15, 15, 335, 335};
    public static final double[] BORDER_CANVAS_PLAYER_1_Y_POLYLINE = new double[]{230, 548, 548, 230};
    public static final double PLAYER_1_CANVAS_X_1 = 15;
    public static final double PLAYER_1_CANVAS_Y_1 = 230;
    public static final double PLAYER_1_CANVAS_OFFSET_X = 320;
    public static final double PLAYER_1_CANVAS_OFFSET_Y = 258;
    public static final int NUM_POLYLINE_PLAYER_1_POINTS = 4;
    public static final double SQUARE_PLAYER_1_GRID_DIMENSION = 60;

    //Canvas Player 1 arc dimensions
    public static final double ARC_Y = 90;
    public static final double ARC_WIDTH = 320;
    public static final double ARC_HEIGHT = 280;
    public static final double ARC_ANGLE_START = 0;
    public static final double ARC_ANGLE_EXTEND = 180;

    //Maps constraints
    public static final int NUM_MAPS_TO_CHOOSE = 4;
    public static final int MAX_WINDOW_FRAMES_ROWS = 4;
    public static final int MAX_WINDOW_FRAMES_COLUMNS = 5;

    //Colors
    public static final String DEFAULT_GRID_COLOR = "gray";
    public static final String EMPTY_FROM_CONSTRAINTS_COLOR = "default";
    public static final Color NUMBERS_DICE_COLOR = Color.BLACK;
    public static final String DEFAULT_DICE_COLOR = "white";

    //Dimensions to draw dice
    public static final double DICE_RADIUS = 12.5;
    public static final double[] OFFSET_DIE_1 = new double[]{30.0, 30.0};
    public static final double[] OFFSET_DIE_2 = new double[]{15.0, 15.0, 45.0, 45.0};
    public static final double[] OFFSET_DIE_3 = new double[]{45.0, 15.0, 30.0, 30.0, 15.0, 45.0};
    public static final double[] OFFSET_DIE_4 = new double[]{15.0, 15.0, 45.0, 15.0, 45.0, 45.0, 15.0, 45.0};
    public static final double[] OFFSET_DIE_5 = new double[]{15.0, 15.0, 45.0, 15.0, 45.0, 45.0, 15.0, 45.0, 30.0, 30.0};
    public static final double[] OFFSET_DIE_6 = new double[]{15.0, 15.0, 45.0, 15.0, 15.0, 30.0, 45.0, 30.0, 15.0, 45.0, 45.0, 45.0};

    public static final double REDUCTION_SCALE = 0.67;

    //Directories for cards loading
    public static final String DEFAULT_DIRECTORY = "src/main/java/res/images/cards/";
    public static final String TOOL_DIRECTORY = "toolcards";
    public static final String PUBOBJ_DIRECTORY = "pubobjcards";
    public static final String PRIV_OBJ_DIRECTORY = "privobjcards";

    //Directories for fxml files loading
    public static final String CONNECTION_LOGIN_FXML_PATH = "/FXML_files/ConnectionLogin.fxml";
    public static final String PLAY_OR_QUIT_FXML_PATH = "/FXML_files/PlayOrQuit.fxml";
    public static final String LOBBY_FXML_PATH = "/FXML_files/Lobby.fxml";
    public static final String MAP_CHOICE_FXML_PATH = "/FXML_files/WindowFrameChoice.fxml";
    public static final String MAIN_BOARD_FXML_PATH = "/FXML_files/MainBoard.fxml";

    //Error messages
    public static final String LOGIN_ERROR = "Username not valid, insert a new one";
    public static final String LOAD_FXML_ERROR = "File fxml not found";

    //Global Helper, to have one Connection Controller for all GUI fxml files
    public static Helper globalHelper;
    public static void setGlobalHelper(ConnectionType connectionType){
        globalHelper = Helper.getInstance(connectionType);
    }

    public static boolean alreadyInLobby = false;

    public static void setAlreadyInLobby(){
        alreadyInLobby = true;
    }
}