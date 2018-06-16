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

//TODO: create properly Event class when Canvas clicked
//TODO: maybe static class?

public class WindowFrameDrawer {

    //Called only the first time we go to game board, to set every StackPane and Canvas on window frames
    public void setPaneAndCanvasOnFrames(List<Canvas> canvasArrayList, List<StackPane> stackPanes, GridPane gridPane, double scale){
        for (int row = 0; row < GUIParameters.MAX_WINDOW_FRAMES_ROWS; row++) {
            for (int column = 0; column < GUIParameters.MAX_WINDOW_FRAMES_COLUMNS; column++) {
                StackPane pane = new StackPane();
                gridPane.add(pane, column, row);
                Canvas canvas = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * scale, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * scale);
                pane.getChildren().add(canvas);
                stackPanes.add(pane);
                canvasArrayList.add(canvas);
            }
        }
    }

    //Called every move made, to reset all Canvas and StackPane to default values
    public void frameReset(JSONObject json, List<Canvas> canvasOnWindowFrame, List<StackPane> panesOnWindowFrame, double scale){
        for(int i = 0; i < GUIParameters.MAX_WINDOW_FRAMES_ROWS * GUIParameters.MAX_WINDOW_FRAMES_COLUMNS; i++){
            panesOnWindowFrame.get(i).setStyle(GUIParameters.BACKGROUND_COLOR_STRING + GUIParameters.DEFAULT_DICE_COLOR);
            canvasOnWindowFrame.get(i).getGraphicsContext2D().clearRect(0, 0, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * scale, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * scale);
        }
        frameFiller(json, canvasOnWindowFrame, panesOnWindowFrame, scale);
    }

    //Called after a reset, to re-draw window frame after a move
    public void frameFiller(JSONObject windowFrame, List<Canvas> canvasOnWindowFrame, List<StackPane> panesOnWindowFrame, double scale) {
        JSONArray windowFrameConstraints = (JSONArray) windowFrame.get(JSONTag.COORDINATES);
        for(int i = 0; i < GUIParameters.MAX_WINDOW_FRAMES_COLUMNS * GUIParameters.MAX_WINDOW_FRAMES_ROWS; i++) {
            canvasOnWindowFrame(canvasOnWindowFrame.get(i).getGraphicsContext2D(), scale);
            paintWindowFrame(canvasOnWindowFrame.get(i).getGraphicsContext2D(), panesOnWindowFrame.get(i), (JSONObject) windowFrameConstraints.get(i), scale);
        }
    }

    //Called by frame filler, to draw and paint window frames basing on constraints
    private void paintWindowFrame(GraphicsContext gc, StackPane pane, JSONObject constraint, double scale) {
        JSONObject die = (JSONObject) constraint.get(JSONTag.DIE);

        if(die != null){
            int shade = parseInt(die.get(JSONTag.SHADE).toString());
            String color = die.get(JSONTag.COLOR).toString();
            new DiceDrawer().dicePointsDrawer(shade, color, gc, pane, scale);
        } else if(constraint.get(JSONTag.COLOR_CONSTRAINT) != null){
            new DiceDrawer().colorSetter(constraint.get(JSONTag.COLOR_CONSTRAINT).toString(), pane);
        } else if (constraint.get(JSONTag.SHADE_CONSTRAINT) != null) {
            new DiceDrawer().dicePointsDrawer(parseInt(constraint.get(JSONTag.SHADE_CONSTRAINT).toString()), GUIParameters.EMPTY_FROM_CONSTRAINTS_COLOR, gc, pane, scale);
        } else {
            new DiceDrawer().colorSetter(GUIParameters.DEFAULT_GRID_COLOR, pane);
        }
    }
    private void canvasOnWindowFrame(GraphicsContext gc, double scale){
        gc.setStroke(GUIParameters.NUMBERS_DICE_COLOR);
        gc.setLineWidth(4);
        gc.strokePolyline(new double[]{0, 60 * scale, 60 * scale, 0, 0},
                new double[]{0, 0, 60 * scale, 60 * scale, 0},
                5);
    }
}
