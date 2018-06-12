package it.polimi.ingsw.view.gui.gameboard.dice;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import it.polimi.ingsw.view.gui.settings.GUIShade;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//TODO: method that will be used by canvases when clicked
//TODO: refactor

public class DiceDrawer {

    public void dicePoolFiller(JSONObject jSon, GridPane dicePoolGrid) {
        int column = 0;
        JSONArray dicePoolList = (JSONArray) jSon.get(JSONTag.DICE_POOL);

        for (Object o : dicePoolList) {
            StackPane pane = new StackPane();
            Canvas canvas = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
            dicePoolGrid.add(pane, column, 0);
            String color = (String) ((JSONObject) o).get(JSONTag.COLOR);
            int shade = ((Long) ((JSONObject) o).get(JSONTag.SHADE)).intValue();
            dicePointsDrawer(shade, color, canvas.getGraphicsContext2D(), pane);
            column++;
            pane.getChildren().add(canvas);
        }
    }

    public void dicePointsDrawer(int value, String color, GraphicsContext gc, Node node) {
        if (value < 1)
            throw new IllegalArgumentException("Shade of die must be at least 1");
        int numOfCoordinates = (value * 2) - 1;
        double[] coordinates = GUIShade.findByValue(value).getCoordinates();
        gc.setFill(GUIParameters.NUMBERS_DICE_COLOR);
        gc.setStroke(GUIParameters.NUMBERS_DICE_COLOR);
        gc.setLineWidth(4);
        gc.strokePolyline(new double[]{0, 60, 60, 0, 0},
                new double[]{0, 0, 60, 60, 0},
                5);
        while (numOfCoordinates >= 0) {
            gc.fillOval(coordinates[numOfCoordinates - 1] - GUIParameters.DICE_RADIUS / 2, coordinates[numOfCoordinates] - GUIParameters.DICE_RADIUS / 2,
                    GUIParameters.DICE_RADIUS, GUIParameters.DICE_RADIUS);
            numOfCoordinates -= 2;
        }
        colorSetter(color, node);
    }
    public void colorSetter(String color, Node node) {
        if (color.equals(GUIParameters.EMPTY_FROM_CONSTRAINTS_COLOR)) {
            node.setStyle("-fx-background-color: " + GUIParameters.DEFAULT_DICE_COLOR);
        } else {
            node.setStyle("-fx-background-color: " + color);
        }
    }
}
