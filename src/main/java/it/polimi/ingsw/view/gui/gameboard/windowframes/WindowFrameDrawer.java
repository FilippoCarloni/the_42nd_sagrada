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


public class WindowFrameDrawer {

    //In the future this method will take as input a real JSON file, but for now I use a String
    public void frameFiller(GridPane gridPane, String json) {
        for (int row = 0; row < GUIParameters.MAX_WINDOW_FRAMES_ROWS; row++){
            for (int column = 0; column < GUIParameters.MAX_WINDOW_FRAMES_COLUMNS; column++){
                StackPane pane = new StackPane();
                gridPane.add(pane, column, row);
                paintWindowFrame(pane, row, column, json);
            }
        }
    }

    private void paintWindowFrame(StackPane pane, int row, int column, String json) {
        try {
            WindowFrame wf = JSONFactory.getWindowFrame((JSONObject) new JSONParser().parse(json));
            if(wf.getColorConstraint(row, column) != null){
                Canvas canvas = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
                String color = wf.getColorConstraint(row, column).getLabel();
                canvasOnWindowFrame(canvas.getGraphicsContext2D());
                new DiceDrawer().colorSetter(color, pane);
                pane.getChildren().add(canvas);
            }
            else if (wf.getShadeConstraint(row, column) != null) {
                Die die = wf.getDie(row, column);
                String color = GUIParameters.EMPTY_FROM_CONSTRAINTS_COLOR;
                int shade = wf.getShadeConstraint(row, column).getValue();
                Canvas canvas = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
                if(die != null){
                    shade = die.getShade().getValue();
                    color = die.getColor().getLabel();
                }
                new DiceDrawer().diceDrawer(shade, color, canvas.getGraphicsContext2D(), pane);
                pane.getChildren().add(canvas);
            }
            else {
                Canvas canvas = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
                canvasOnWindowFrame(canvas.getGraphicsContext2D());
                new DiceDrawer().colorSetter(GUIParameters.DEFAULT_GRID_COLOR, pane);
                pane.getChildren().add(canvas);
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
