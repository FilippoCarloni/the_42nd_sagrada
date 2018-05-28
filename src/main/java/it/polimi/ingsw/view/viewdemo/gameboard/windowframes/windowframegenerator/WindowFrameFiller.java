package it.polimi.ingsw.view.viewdemo.gameboard.windowframes.windowframegenerator;

import it.polimi.ingsw.model.gameboard.windowframes.PaperWindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.view.viewdemo.settings.GUIParameters;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class WindowFrameFiller {

    //In the future this method will take as input a real JSON file, but for now I use a String
    public void frameFiller(GridPane gridPane, String JSon) {
        for (int row = 0; row < GUIParameters.MAX_WINDOW_FRAMES_ROWS; row++){
            for (int column = 0; column < GUIParameters.MAX_WINDOW_FRAMES_COLUMNS; column++){
                StackPane pane = new StackPane();
                gridPane.add(pane, column, row);
                Rectangle rectangle = new Rectangle(60, 60);
                paintWindowFrame(rectangle, row, column, JSon);
                pane.getChildren().add(rectangle);
            }
        }
    }

    private void paintWindowFrame(Rectangle rectangle, int row, int column, String JSon) {
        try {
            WindowFrame wf = new PaperWindowFrame((JSONObject) new JSONParser().parse(JSon));
            if(wf.getColorConstraint(row, column) != null){
                String color = wf.getColorConstraint(row, column).getLabel();
                switch (color){
                    case "red":
                        rectangle.setFill(Color.RED);
                        break;
                    case "green":
                        rectangle.setFill(Color.GREEN);
                        break;
                    case "yellow":
                        rectangle.setFill(Color.YELLOW);
                        break;
                    case "blue":
                        rectangle.setFill(Color.BLUE);
                        break;
                    case "purple":
                        rectangle.setFill(Color.PURPLE);
                        break;
                    default:
                        rectangle.setFill(Color.GRAY);
                        break;
                }
            }
            else if (wf.getShadeConstraint(row, column) != null) {
                int shade = wf.getShadeConstraint(row, column).getValue();
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
                        rectangle.setFill(Color.GRAY);
                        break;
                }
            }
            else {
                rectangle.setFill(Color.GRAY);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
