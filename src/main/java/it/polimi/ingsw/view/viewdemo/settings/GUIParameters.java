package it.polimi.ingsw.view.viewdemo.settings;

import javafx.stage.Screen;

public final class GUIParameters {

    private GUIParameters(){}

    public static final int NUM_COLUMNS = 3;
    public static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    public static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
    public static final String BG_IMAGE_PATH = "res/images/sagrada_menu_bg.png";
    public static final String COLOR_SHADE_IMAGE_PATH = "res/images/";
    public static final String DEFAULT_IMAGE_PATH = "res/images/default.png";

    public static final double DICE_BAG_WIDTH = 350;
    public static final double DICE_BAG_HEIGHT = 120;

    public static final double DIE_WIDTH = 250;
    public static final double DIE_HEIGHT = 80;

    public static final double FAVOR_POINT_WIDTH = 120;
    public static final double FAVOR_POINT_HEIGHT = 50;

    public static final double CARD_WIDTH = 120;
    public static final double CARD_HEIGHT = 50;

    public static final double ROUND_TRACK_WIDTH = 450;
    public static final double ROUND_TRACK_HEIGHT = 150;

    public static final double WINDOW_FRAME_WIDTH = 400;
    public static final double WINDOW_FRAME_HEIGHT = 200;

}