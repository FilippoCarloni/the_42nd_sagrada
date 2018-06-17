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

import java.util.List;

//TODO: method that will be used by canvas when clicked
//TODO: understand why dice pool is not responsive

public class DiceDrawer {

    //Used only the first time, to fill Dice Pool with Canvas and StackPane
    public static void dicePoolFiller(GridPane dicePoolGrid, List<StackPane> panesOnDicePool, List<Canvas> canvasOnDicePool, int numDiceOnDicePool){
        for(int i = 0; i < numDiceOnDicePool; i++){
            StackPane pane = new StackPane();
            dicePoolGrid.add(pane, i, 0);
            Canvas canvas = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
            pane.getChildren().add(canvas);
            panesOnDicePool.add(pane);
            canvasOnDicePool.add(canvas);
            int finalI = i;
            canvas.setOnMouseClicked(e -> System.out.println("pick " + finalI));
        }
    }

    //Used every move made, to reset colors and shades on dice pool and re-draw it
    public static void dicePoolReset(JSONObject json, List<StackPane> panesOnDicePool, List<Canvas> canvasOnDicePool){
        for(int i = 0; i < panesOnDicePool.size(); i++){
            panesOnDicePool.get(i).setStyle(GUIParameters.BACKGROUND_COLOR_STRING + GUIParameters.DEFAULT_DICE_COLOR);
            canvasOnDicePool.get(i).getGraphicsContext2D().clearRect(0,0, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
        }
        dicePoolDrawer(json, panesOnDicePool, canvasOnDicePool);
    }
    private static void dicePoolDrawer(JSONObject jSon, List<StackPane> panesOnDicePool, List<Canvas> canvasOnDicePool) {
        int i = 0;
        JSONArray dicePoolList = (JSONArray) jSon.get(JSONTag.DICE_POOL);

        for (Object o : dicePoolList) {
            StackPane pane = panesOnDicePool.get(i);
            Canvas canvas = canvasOnDicePool.get(i);
            String color = (String) ((JSONObject) o).get(JSONTag.COLOR);
            int shade = ((Long) ((JSONObject) o).get(JSONTag.SHADE)).intValue();
            dicePointsDrawer(shade, color, canvas.getGraphicsContext2D(), pane, 1);
            i++;
        }
    }

    //Utility methods to color and draw dice, used both by dice pool and window frame methods
    public static void dicePointsDrawer(int value, String color, GraphicsContext gc, Node node, double scale) {
        if (value < 1)
            throw new IllegalArgumentException("Shade of die must be at least 1");
        int numOfCoordinates = (value * 2) - 1;
        double[] coordinates = GUIShade.findByValue(value).getCoordinates();
        gc.setFill(GUIParameters.NUMBERS_DICE_COLOR);
        gc.setStroke(GUIParameters.NUMBERS_DICE_COLOR);
        gc.setLineWidth(4 * scale);
        gc.strokePolyline(new double[]{0, 60 * scale, 60 * scale, 0, 0},
                new double[]{0, 0, 60 * scale, 60 * scale, 0},
                5);
        while (numOfCoordinates >= 0) {
            gc.fillOval((coordinates[numOfCoordinates - 1] - GUIParameters.DICE_RADIUS / 2) * scale, (coordinates[numOfCoordinates] - GUIParameters.DICE_RADIUS / 2) * scale,
                    GUIParameters.DICE_RADIUS * scale, GUIParameters.DICE_RADIUS * scale);
            numOfCoordinates -= 2;
        }
        colorSetter(color, node);
    }
    public static void colorSetter(String color, Node node) {
        if (color.equals(GUIParameters.EMPTY_FROM_CONSTRAINTS_COLOR)) {
            node.setStyle(GUIParameters.BACKGROUND_COLOR_STRING + GUIParameters.DEFAULT_DICE_COLOR);
        } else {
            node.setStyle(GUIParameters.BACKGROUND_COLOR_STRING + color);
        }
    }

    private DiceDrawer(){

    }
}
