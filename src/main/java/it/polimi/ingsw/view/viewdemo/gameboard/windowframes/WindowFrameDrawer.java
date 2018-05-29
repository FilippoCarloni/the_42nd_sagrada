package it.polimi.ingsw.view.viewdemo.gameboard.windowframes;

import it.polimi.ingsw.model.gameboard.windowframes.PaperWindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.view.viewdemo.settings.GUIColor;
import it.polimi.ingsw.view.viewdemo.settings.GUIParameters;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
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

    //TODO: create the function that draws the dice
    private void paintWindowFrame(StackPane pane, int row, int column, String json) {
        try {
            WindowFrame wf = new PaperWindowFrame((JSONObject) new JSONParser().parse(json));
            if(wf.getColorConstraint(row, column) != null){
                Rectangle rectangle = new Rectangle(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
                String color = wf.getColorConstraint(row, column).getLabel();
                rectangle.setFill(GUIColor.findByName(color).getColor());
                pane.getChildren().add(rectangle);
            }
            else if (wf.getShadeConstraint(row, column) != null) {
                //TODO: instead of rectangle I will put canvas, to draw dice
                Rectangle rectangle = new Rectangle(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
                pane.getChildren().add(rectangle);
                int shade = wf.getShadeConstraint(row, column).getValue();
                //TODO: modify this switch-case after having the function that draws dice
                switch (shade){
                    case 1:
                        rectangle.setFill(new ImagePattern(new Image("/images/die_1.png")));
                        break;
                    case 2:
                        rectangle.setFill(new ImagePattern(new Image("/images/die_2.png")));
                        break;
                    case 3:
                        rectangle.setFill(new ImagePattern(new Image("/images/die_3.png")));
                        break;
                    case 4:
                        rectangle.setFill(new ImagePattern(new Image("/images/die_4.png")));
                        break;
                    case 5:
                        rectangle.setFill(new ImagePattern(new Image("/images/die_5.png")));
                        break;
                    case 6:
                        rectangle.setFill(new ImagePattern(new Image("/images/die_6.png")));
                        break;
                    default:
                        rectangle.setFill(GUIParameters.DEFAULT_GRID_COLOR);
                        break;
                }
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
