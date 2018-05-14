package it.polimi.ingsw.view.viewdemo.utility;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ConstructorHelper {

    public StackPane setElement(int row, int column, GridPane gridPane, String name, double font) {
        StackPane stackPane = new StackPane();
        Text nameText = new Text(name);
        nameText.setFont(nameText.getFont().font(font));
        nameText.setFill(Color.WHITE);
        nameText.setTextAlignment(TextAlignment.CENTER);
        stackPane.getChildren().add(nameText);

        gridPane.add(stackPane, column, row);   //add asks columnIndex, rowIndex
        return stackPane;
    }

}
