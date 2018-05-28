package it.polimi.ingsw.view.viewdemo.gameboard.windowframes.windowframegenerator;

import it.polimi.ingsw.model.gameboard.windowframes.PaperWindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
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

    public void frameFiller(GridPane gridPane) {
        for (int row = 0; row < 4; row++){
            for (int column = 0; column < 5; column++){
                StackPane pane = new StackPane();
                gridPane.add(pane, column, row);
                Rectangle rectangle = new Rectangle(56, 68);
                paintWindowFrame(rectangle, row, column);
                pane.getChildren().add(rectangle);
            }
        }
    }

    private void paintWindowFrame(Rectangle rectangle, int row, int column) {
        try {
            WindowFrame wf = new PaperWindowFrame((JSONObject) new JSONParser().parse("{\"difficulty\":5,\"name\":\"Batllo\",\"coordinates\":[{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":0},{\"die\":{\"color\":\"red\",\"shade\":6,\"id\":0},\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":1},{\"die\":null,\"shade_constraint\":6,\"color_constraint\":null,\"row_index\":0,\"column_index\":2},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":3},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":4},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":1,\"column_index\":0},{\"die\":{\"color\":\"green\",\"shade\":6,\"id\":36},\"shade_constraint\":5,\"color_constraint\":null,\"row_index\":1,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"blue\",\"row_index\":1,\"column_index\":2},{\"die\":null,\"shade_constraint\":4,\"color_constraint\":null,\"row_index\":1,\"column_index\":3},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":1,\"column_index\":4},{\"die\":null,\"shade_constraint\":3,\"color_constraint\":null,\"row_index\":2,\"column_index\":0},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"green\",\"row_index\":2,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"yellow\",\"row_index\":2,\"column_index\":2},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"purple\",\"row_index\":2,\"column_index\":3},{\"die\":null,\"shade_constraint\":2,\"color_constraint\":null,\"row_index\":2,\"column_index\":4},{\"die\":null,\"shade_constraint\":1,\"color_constraint\":null,\"row_index\":3,\"column_index\":0},{\"die\":null,\"shade_constraint\":4,\"color_constraint\":null,\"row_index\":3,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"red\",\"row_index\":3,\"column_index\":2},{\"die\":null,\"shade_constraint\":5,\"color_constraint\":null,\"row_index\":3,\"column_index\":3},{\"die\":null,\"shade_constraint\":3,\"color_constraint\":null,\"row_index\":3,\"column_index\":4}]}"));
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
