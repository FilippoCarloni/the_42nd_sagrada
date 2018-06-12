package it.polimi.ingsw.view.gui.gameboard.windowframes;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.gameboard.dice.DiceDrawer;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class WindowFrameDrawer {

    //TODO: add flag to understand if he is drawing player 1 map or not
    //TODO: create properly Event class

    public void frameFiller(GridPane gridPane, JSONObject windowFrame) {
        int positionIntoJson = 0;
        JSONArray windowFrameConstraints = (JSONArray) windowFrame.get(JSONTag.COORDINATES);
        for (int row = 0; row < GUIParameters.MAX_WINDOW_FRAMES_ROWS; row++){
            for (int column = 0; column < GUIParameters.MAX_WINDOW_FRAMES_COLUMNS; column++){
                StackPane pane = new StackPane();
                gridPane.add(pane, column, row);
                Canvas canvas = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
                canvasOnWindowFrame(canvas.getGraphicsContext2D());
                paintWindowFrame(canvas.getGraphicsContext2D(), pane, (JSONObject) windowFrameConstraints.get(positionIntoJson));
                pane.getChildren().add(canvas);
                positionIntoJson++;
            }
        }
    }


    private void paintWindowFrame(GraphicsContext gc, StackPane pane, JSONObject constraint) {
        if(constraint.get(JSONTag.COLOR_CONSTRAINT) != null){
            String color = (String) constraint.get(JSONTag.COLOR_CONSTRAINT);
            new DiceDrawer().colorSetter(color, pane);
            pane.setOnMouseClicked(e -> System.out.println("place"));
        }
        else if (constraint.get(JSONTag.SHADE_CONSTRAINT) != null) {
            shadeOnWindowFrame(gc, pane, constraint);
        }
        else {
            pane.setOnMouseClicked(e -> System.out.println("place"));
            new DiceDrawer().colorSetter(GUIParameters.DEFAULT_GRID_COLOR, pane);
        }
    }
    private void canvasOnWindowFrame(GraphicsContext gc){
        gc.setStroke(GUIParameters.NUMBERS_DICE_COLOR);
        gc.setLineWidth(4);
        gc.strokePolyline(new double[]{0, 60, 60, 0, 0},
                new double[]{0, 0, 60, 60, 0},
                5);
    }
    private void shadeOnWindowFrame(GraphicsContext gc, StackPane pane, JSONObject constraint){
        Die die = (Die) constraint.get(JSONTag.DIE);
        String color = GUIParameters.EMPTY_FROM_CONSTRAINTS_COLOR;
        pane.setOnMouseClicked(e -> System.out.println("place"));
        int shade = ((Long)constraint.get(JSONTag.SHADE_CONSTRAINT)).intValue();
        if(die != null){
            shade = die.getShade().getValue();
            color = die.getColor().getLabel();
            pane.setOnMouseClicked(e -> System.out.println("move"));
        }
        new DiceDrawer().dicePointsDrawer(shade, color, gc, pane);
    }
}
