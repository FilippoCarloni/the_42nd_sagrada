package it.polimi.ingsw.view.viewdemo.gameboard.dice;

import it.polimi.ingsw.view.viewdemo.settings.GUIParameters;
import it.polimi.ingsw.view.viewdemo.settings.GUIShade;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;

public class DiceDrawer {

    //TODO: divide methods
    public void diceDrawer(int value, String color, GraphicsContext gc, StackPane pane){
        if(value < 1)
            throw new IllegalArgumentException("Value of die must be at least 1");

        int numOfCoordinates = (value * 2) - 1;
        double[] coordinates = GUIShade.findByValue(value).getCoordinates();
        gc.setFill(GUIParameters.NUMBERS_DICE_COLOR);
        gc.setStroke(GUIParameters.NUMBERS_DICE_COLOR);
        gc.setLineWidth(4);
        gc.strokePolyline(new double[]{0, 60, 60, 0, 0},
                new double[]{0, 0, 60, 60, 0},
                5);
        while(numOfCoordinates >= 0){
            gc.fillOval(coordinates[numOfCoordinates - 1] - GUIParameters.DICE_RADIUS / 2, coordinates[numOfCoordinates] - GUIParameters.DICE_RADIUS / 2, GUIParameters.DICE_RADIUS, GUIParameters.DICE_RADIUS);
            numOfCoordinates -= 2;
        }
        if(color.equals("default")) {
            pane.setStyle("-fx-background-color: white");
        }
        else{
            pane.setStyle("-fx-background-color: " + color);
        }
    }
}
