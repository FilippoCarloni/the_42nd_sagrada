package it.polimi.ingsw.view.viewdemo.gameboard.windowframes.windowframegenerator;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class WindowFrameFiller {

    public void frameFiller(GridPane gridPane, Group group){
        ArrayList<Canvas> canvasOnGrid = new ArrayList<>();

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                Canvas canvas = new Canvas();
                drawCanvas(canvas.getGraphicsContext2D());
                canvasOnGrid.add(canvas);
                gridPane.add(canvas, j, i);
            }
        }

        group.getChildren().addAll(canvasOnGrid);
    }

    private void drawCanvas(GraphicsContext gc){
        gc.setStroke(Color.BLACK);

    }

}
