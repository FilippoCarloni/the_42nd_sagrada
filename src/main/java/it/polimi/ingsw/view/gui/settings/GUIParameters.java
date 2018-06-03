package it.polimi.ingsw.view.gui.settings;

import javafx.scene.paint.Color;

public final class GUIParameters {

    private GUIParameters(){}

    public static final String CONNECTION_SCENE_TITLE = "Connection Choice";
    public static final String LOGIN_SCENE_TITLE = "Login Screen";
    public static final String MAIN_SCENE_TITLE = "Sagrada Board Game";

    public static final double CONNECTION_LOGIN_SCENE_WIDTH = 600;
    public static final double CONNECTION_LOGIN_SCENE_HEIGHT = 400;
    public static final double SCREEN_WIDTH = 1280;
    public static final double SCREEN_HEIGHT = 720;

    public static final double PLAYER_1_GRID_X = 110;
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

    public static final double ARC_Y = 90;
    public static final double ARC_WIDTH = 320;
    public static final double ARC_HEIGHT = 280;
    public static final double ARC_ANGLE_START = 0;
    public static final double ARC_ANGLE_EXTEND = 180;

    public static final int MAX_WINDOW_FRAMES_ROWS = 4;
    public static final int MAX_WINDOW_FRAMES_COLUMNS = 5;

    public static final String DEFAULT_GRID_COLOR = "gray";
    public static final String EMPTY_FROM_CONSTRAINTS_COLOR = "default";
    public static final Color NUMBERS_DICE_COLOR = Color.BLACK;
    public static final String DEFAULT_DICE_COLOR = "white";

    public static final double DICE_RADIUS = 12.5;
    public static final double[] OFFSET_DIE_1 = new double[]{30.0, 30.0};
    public static final double[] OFFSET_DIE_2 = new double[]{15.0, 15.0, 45.0, 45.0};
    public static final double[] OFFSET_DIE_3 = new double[]{45.0, 15.0, 30.0, 30.0, 15.0, 45.0};
    public static final double[] OFFSET_DIE_4 = new double[]{15.0, 15.0, 45.0, 15.0, 45.0, 45.0, 15.0, 45.0};
    public static final double[] OFFSET_DIE_5 = new double[]{15.0, 15.0, 45.0, 15.0, 45.0, 45.0, 15.0, 45.0, 30.0, 30.0};
    public static final double[] OFFSET_DIE_6 = new double[]{15.0, 15.0, 45.0, 15.0, 15.0, 30.0, 45.0, 30.0, 15.0, 45.0, 45.0, 45.0};

    public static final double REDUCTION_SCALE = 0.67;
}