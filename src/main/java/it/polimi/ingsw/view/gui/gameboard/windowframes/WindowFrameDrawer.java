package it.polimi.ingsw.view.gui.gameboard.windowframes;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.utility.JSONFactory;
import it.polimi.ingsw.view.gui.gameboard.dice.DiceDrawer;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;


public class WindowFrameDrawer {

    private  ArrayList<Canvas> canvasOnWindowFrame;

    //In the future this method will take as input a real JSON file, but for now I use a String
    public void frameFiller(GridPane gridPane, String json) {
        canvasOnWindowFrame = new ArrayList<>();
        for (int row = 0; row < GUIParameters.MAX_WINDOW_FRAMES_ROWS; row++){
            for (int column = 0; column < GUIParameters.MAX_WINDOW_FRAMES_COLUMNS; column++){
                StackPane pane = new StackPane();
                gridPane.add(pane, column, row);
                Canvas canvas = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
                canvasOnWindowFrame(canvas.getGraphicsContext2D());
                paintWindowFrame(canvas.getGraphicsContext2D(), pane, row, column, json);
                pane.getChildren().add(canvas);
                canvasOnWindowFrame.add(canvas);
            }
        }
    }

    public ArrayList<Canvas> getCanvasOnWindowFrame(){
        return canvasOnWindowFrame;
    }

    private void paintWindowFrame(GraphicsContext gc, StackPane pane, int row, int column, String json) {
        try {
            WindowFrame wf = JSONFactory.getWindowFrame((JSONObject) new JSONParser().parse(json));
            if(wf.getColorConstraint(row, column) != null){
                String color = wf.getColorConstraint(row, column).getLabel();
                new DiceDrawer().colorSetter(color, pane);
            }
            else if (wf.getShadeConstraint(row, column) != null) {
                Die die = wf.getDie(row, column);
                String color = GUIParameters.EMPTY_FROM_CONSTRAINTS_COLOR;
                int shade = wf.getShadeConstraint(row, column).getValue();
                if(die != null){
                    shade = die.getShade().getValue();
                    color = die.getColor().getLabel();
                }
                new DiceDrawer().dicePointsDrawer(shade, color, gc, pane);
            }
            else {
                new DiceDrawer().colorSetter(GUIParameters.DEFAULT_GRID_COLOR, pane);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void canvasOnWindowFrame(GraphicsContext gc){
        gc.setStroke(GUIParameters.NUMBERS_DICE_COLOR);
        gc.setLineWidth(4);
        gc.strokePolyline(new double[]{0, 60, 60, 0, 0},
                new double[]{0, 0, 60, 60, 0},
                5);
    }
}
