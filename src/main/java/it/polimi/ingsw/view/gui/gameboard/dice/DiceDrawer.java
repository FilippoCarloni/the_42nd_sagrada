package it.polimi.ingsw.view.gui.gameboard.dice;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import it.polimi.ingsw.view.gui.settings.GUIShade;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.print;

/**
 * Class that manage the dice drawing,
 */

public class DiceDrawer {

    /**
     * Used to fill an horizontal GridPane with Canvas and StackPane, to set it ready to be drawn
     * @param gridToFill: horizontal GridPane to fill with Canvas and StackPane
     * @param panesOnDicePool: List<StackPane> in which will be put all StackPanes initialized
     * @param canvasOnDicePool: List<Canvas> in which will be put all Canvas initialized
     * @param numDice: integer, it represents the number of StackPane and Canvas that will be initialized and put on gridToFill
     * @param isDicePool: boolean, it helps managing click events on Dice Pool
     */
    public static void diceFiller(GridPane gridToFill, List<StackPane> panesOnDicePool, List<Canvas> canvasOnDicePool, int numDice, boolean isDicePool){
        for(int i = 0; i < numDice; i++){
            StackPane pane = new StackPane();
            gridToFill.add(pane, i, 0);
            Canvas canvas = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
            canvas.getStyleClass().clear();
            canvas.getStyleClass().add("clickable-canvas");
            pane.getChildren().add(canvas);
            panesOnDicePool.add(pane);
            canvasOnDicePool.add(canvas);
            if(isDicePool) {
                //Need this finalI because in lambda expressions I can't use not final variables
                int finalI = i + 1;
                canvas.setOnMouseClicked(e -> {
                    try {
                        GuiManager.getInstance().getConnectionController().send(GUIParameters.PICK + finalI);
                    } catch (ConnectException | RemoteException e1) {
                        print(e1.getMessage());
                    }
                });
            }
        }
    }

    /**
     * Used every move made, to reset colors and shades on dice pool and re-draw it
     * @param json: JSONObject containing all information about dice on dice pool.
     * @param panesOnDicePool: List<StackPane> containing all StackPane on dice pool.
     * @param canvasOnDicePool: List<Canvas> containing all Canvas on dice pool.
     */
    public static void dicePoolReset(JSONObject json, List<StackPane> panesOnDicePool, List<Canvas> canvasOnDicePool){
        for(int i = 0; i < panesOnDicePool.size(); i++){
            panesOnDicePool.get(i).setStyle(GUIParameters.BACKGROUND_COLOR_STRING + GUIParameters.BACKGROUND_COLOR);
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

    /**
     * Utility methods to color and draw dice, used both by dice pool and window frame methods.
     * @param value: value of die to draw.
     * @param color: color of die to draw.
     * @param gc: GraphicsContext on which die's value will be drawn.
     * @param node: Node that will take die's color
     * @param scale: double, parameter which makes "scalable" the dice drawing algorithm.
     */
    public static void dicePointsDrawer(int value, String color, GraphicsContext gc, Node node, double scale) {
        if (value < 1)
            throw new IllegalArgumentException(GUIParameters.DIE_VALUE_SMALL);
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
