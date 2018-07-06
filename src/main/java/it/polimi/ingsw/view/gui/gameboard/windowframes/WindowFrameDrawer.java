package it.polimi.ingsw.view.gui.gameboard.windowframes;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.gameboard.dice.DiceDrawer;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * Class that manage the window frame drawing,
 */

public class WindowFrameDrawer {

    /**
     * Called only the first time we go to game board, to set every StackPane and Canvas on window frames
     * @param canvasOnWindowFrame: List<Canvas> in which all Canvas on the specified window frame will be stored
     * @param stackPanesOnWindowFrame: List<StackPane> in which all StackPane on the specified window frame will be stored
     * @param windowFrame: the GridPane which will contain the drawn window frame
     * @param scale: double, parameter which makes "scalable" the window frame drawing algorithm, in order to draw all players' window frames
     *             with different dimensions
     * @param clickable: boolean which is used to discriminate if we are filling the main player window frame. If it is true, all StackPane
     *                 into this window frame will handle Mouse Events (in particular Mouse Click Events)
     */
    public static void frameFiller(List<Canvas> canvasOnWindowFrame, List<StackPane> stackPanesOnWindowFrame, GridPane windowFrame, double scale, boolean clickable){
        for (int row = 0; row < GUIParameters.MAX_WINDOW_FRAMES_ROWS; row++) {
            for (int column = 0; column < GUIParameters.MAX_WINDOW_FRAMES_COLUMNS; column++) {
                StackPane pane = new StackPane();
                windowFrame.add(pane, column, row);
                Canvas canvas = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * scale, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * scale);
                pane.getChildren().add(canvas);
                stackPanesOnWindowFrame.add(pane);
                canvasOnWindowFrame.add(canvas);
                if(clickable) {
                    canvas.getStyleClass().clear();
                    canvas.getStyleClass().add(GUIParameters.CLICKABLE);
                    new WindowEvents().clickEventsOnWindowFrame(pane, row + 1, column + 1, false);
                }
            }
        }
    }

    /**
     * Called every move made, to reset all Canvas and StackPane to default values; after the reset step it calls the framePainterManager
     * which re-draw the entire window frame
     * @param windowFrame: JSONObject containing all information about the window frame to draw
     * @param canvasOnWindowFrame: List<Canvas> containing all Canvas on the specified window frame
     * @param panesOnWindowFrame: List<StackPane> containing all StackPane on the specified window frame
     * @param scale: double, parameter which makes "scalable" the window frame drawing algorithm, in order to draw all players' window frames
     *             with different dimensions
     * @param clickable: boolean which is used to discriminate if we are filling the main player window frame. If it is true, all StackPane
     *                 into this window frame will handle Mouse Events (in particular Mouse Click Events)
     */
    public static void frameReset(JSONObject windowFrame, List<Canvas> canvasOnWindowFrame, List<StackPane> panesOnWindowFrame, double scale, boolean clickable){
        for(int i = 0; i < GUIParameters.MAX_WINDOW_FRAMES_ROWS * GUIParameters.MAX_WINDOW_FRAMES_COLUMNS; i++){
            panesOnWindowFrame.get(i).setStyle(GUIParameters.BACKGROUND_COLOR_STRING + GUIParameters.DEFAULT_DICE_COLOR);
            canvasOnWindowFrame.get(i).getGraphicsContext2D().clearRect(0, 0, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * scale, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * scale);
        }
        framePainterManager(windowFrame, canvasOnWindowFrame, panesOnWindowFrame, scale, clickable);
    }

    /**
     * Called after a reset; manager method for re-drawing window frames
     * @param windowFrame: JSONObject containing all information about the window frame to draw
     * @param canvasOnWindowFrame: List<Canvas> containing all Canvas on the specified window frame
     * @param panesOnWindowFrame: List<StackPane> containing all StackPane on the specified window frame
     * @param scale: double, parameter which makes "scalable" the window frame drawing algorithm, in order to draw all players' window frames
     *             with different dimensions
     * @param clickable: boolean which is used to discriminate if we are filling the main player window frame. If it is true, all StackPane
     *                 into this window frame will handle Mouse Events (in particular Mouse Click Events)
     */
    public static void framePainterManager(JSONObject windowFrame, List<Canvas> canvasOnWindowFrame, List<StackPane> panesOnWindowFrame, double scale, boolean clickable) {
        JSONArray windowFrameConstraints = (JSONArray) windowFrame.get(JSONTag.COORDINATES);
        for(int i = 0; i < GUIParameters.MAX_WINDOW_FRAMES_COLUMNS * GUIParameters.MAX_WINDOW_FRAMES_ROWS; i++) {
            canvasOnWindowFrame(canvasOnWindowFrame.get(i).getGraphicsContext2D(), scale);
            framePainter(canvasOnWindowFrame.get(i).getGraphicsContext2D(), panesOnWindowFrame.get(i), (JSONObject) windowFrameConstraints.get(i), i, scale, clickable);
        }
    }


    private static void framePainter(GraphicsContext gc, StackPane pane, JSONObject constraint, int positionIntoLists, double scale, boolean clickable) {
        JSONObject die = (JSONObject) constraint.get(JSONTag.DIE);

        if(die != null){
            if(clickable)
                new WindowEvents().clickEventsOnWindowFrame(pane, rowAndColumnFromListIndex(positionIntoLists)[0], rowAndColumnFromListIndex(positionIntoLists)[1], true);
            int shade = parseInt(die.get(JSONTag.SHADE).toString());
            String color = die.get(JSONTag.COLOR).toString();
            DiceDrawer.dicePointsDrawer(shade, color, gc, pane, scale);
        } else if(constraint.get(JSONTag.COLOR_CONSTRAINT) != null){
            DiceDrawer.colorSetter(constraint.get(JSONTag.COLOR_CONSTRAINT).toString(), pane);
        } else if (constraint.get(JSONTag.SHADE_CONSTRAINT) != null) {
            DiceDrawer.dicePointsDrawer(parseInt(constraint.get(JSONTag.SHADE_CONSTRAINT).toString()), GUIParameters.EMPTY_FROM_CONSTRAINTS_COLOR, gc, pane, scale);
        } else {
            DiceDrawer.colorSetter(GUIParameters.DEFAULT_GRID_COLOR, pane);
        }
    }
    private static void canvasOnWindowFrame(GraphicsContext gc, double scale){
        gc.setStroke(GUIParameters.NUMBERS_DICE_COLOR);
        gc.setLineWidth(4);
        gc.strokePolyline(new double[]{0, 60 * scale, 60 * scale, 0, 0},
                new double[]{0, 0, 60 * scale, 60 * scale, 0},
                5);
    }

    /**
     * Function that calculate the right position into window frame (in terms of row and column) from the position into the List
     * @param listIndex: position of element into the list
     * @return array of int containing row position and column position into the window frame
     */
    static int[] rowAndColumnFromListIndex(int listIndex){
        int column = (listIndex % GUIParameters.MAX_WINDOW_FRAMES_COLUMNS) + 1;
        int row = (listIndex / GUIParameters.MAX_WINDOW_FRAMES_COLUMNS) + 1;

        return new int[]{row, column};
    }

    private WindowFrameDrawer(){

    }



}
