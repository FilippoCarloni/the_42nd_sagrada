package it.polimi.ingsw.view.viewdemo.utility;

import it.polimi.ingsw.model.gameboard.utility.Parameters;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ConstructorHelper {

    public void setElement(int row, int column, GridPane gridPane, String name, double font) {
        StackPane stackPane = new StackPane();
        Text nameText = new Text(name);
        nameText.setFont(nameText.getFont().font(font));
        nameText.setFill(Color.WHITE);
        nameText.setTextAlignment(TextAlignment.CENTER);
        stackPane.getChildren().add(nameText);

        gridPane.add(stackPane, column, row);   //add asks columnIndex, rowIndex
    }

    public GridPane setWindowFrameElement(int row, int column, GridPane gridPane){
        GridPane container = new GridPane();
        container.getColumnConstraints().addAll(new MainHelper().setColumnConstraints(Parameters.MAX_COLUMNS));
        container.getRowConstraints().addAll(new MainHelper().setRowConstraints(Parameters.MAX_ROWS));
        container.setGridLinesVisible(true);

        gridPane.add(container, column, row);
        return container;
    }

}
