package it.polimi.ingsw.view.viewdemo.gameboard.windowframes;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.utility.JSONFactory;
import it.polimi.ingsw.view.viewdemo.gameboard.dice.DiceDrawer;
import it.polimi.ingsw.view.viewdemo.settings.GUIColor;
import it.polimi.ingsw.view.viewdemo.settings.GUIParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
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
                Rectangle rectangle = new Rectangle(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
                String color = wf.getColorConstraint(row, column).getLabel();
                rectangle.setFill(GUIColor.findByName(color).getColor());
                pane.getChildren().add(rectangle);
            }
            else if (wf.getShadeConstraint(row, column) != null) {
                Die die = wf.getDie(row, column);
                String color = "default";
                int shade = wf.getShadeConstraint(row, column).getValue();
                Canvas canvas = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
                if(die != null){
                    shade = die.getShade().getValue();
                    color = die.getColor().getLabel();
                    new DiceDrawer().diceDrawer(shade, color, canvas.getGraphicsContext2D(), pane);
                }
                new DiceDrawer().diceDrawer(shade, color, canvas.getGraphicsContext2D(), pane);
                pane.getChildren().add(canvas);
            }
            else {
                Rectangle rectangle = new Rectangle(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
                rectangle.setFill(GUIParameters.DEFAULT_GRID_COLOR);
                pane.getChildren().add(rectangle);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
