package it.polimi.ingsw.view.viewdemo.utility;

import it.polimi.ingsw.view.viewdemo.databaseview.*;
import it.polimi.ingsw.view.viewdemo.settings.GUIParameters;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;

public class MainHelper {

    public int setNumRows(int numOfPlayers){
        if(numOfPlayers == 2)
            return 4;
        else
            return 5;
    }
    public double setDimension(int i, boolean b){
        if(b)       //Rows dimension
            return (GUIParameters.SCREEN_HEIGHT / i);
        else        //Column dimension
            return (GUIParameters.SCREEN_WIDTH / i);
    }
    public ArrayList<ColumnConstraints> setColumnConstraints(int numColumn){
        ArrayList<ColumnConstraints> columnConstraints = new ArrayList<>();

        for (int i = 0; i < numColumn; i++){
            columnConstraints.add(new ColumnConstraints());
            columnConstraints.get(i).setPrefWidth(setDimension(GUIParameters.NUM_COLUMNS, false));
        }

        return columnConstraints;
    }
    public ArrayList<RowConstraints> setRowConstraints(int numRows){
        ArrayList<RowConstraints> rowConstraints = new ArrayList<>();

        for (int i = 0; i < numRows; i++){
            rowConstraints.add(new RowConstraints());
            rowConstraints.get(i).setPrefHeight(setDimension(numRows, true));
        }

        return rowConstraints;
    }
    public void setHBoxOnGrid(GridPane gridPane, int numRows, int numColumn){
        for(int i = 0; i < setNumRows(numRows); i++){
            for(int j = 0; j < numColumn; j++){
                gridPane.add(new HBox(), j, i);
            }
        }
    }

    public ArrayList<DrawableWindowFrame> setWindowFrames(int numOfPlayers, GridPane gridPane){
        ArrayList<DrawableWindowFrame> drawableWindowFrame = new ArrayList<>();

        if(numOfPlayers == 2) {
            drawableWindowFrame.add(new DrawableWindowFrame(1, 0, gridPane, "src/main/java/res/window_patterns/batllo.txt"));
            drawableWindowFrame.add(new DrawableWindowFrame(1, 2, gridPane, "src/main/java/res/window_patterns/batllo.txt"));
        }
        else if (numOfPlayers == 3){
            drawableWindowFrame.add(new DrawableWindowFrame(2, 0, gridPane, "src/main/java/res/window_patterns/batllo.txt"));
            drawableWindowFrame.add(new DrawableWindowFrame(4, 1, gridPane, "src/main/java/res/window_patterns/batllo.txt"));
            drawableWindowFrame.add(new DrawableWindowFrame(1, 2, gridPane, "src/main/java/res/window_patterns/batllo.txt"));
        }
        else {
            drawableWindowFrame.add(new DrawableWindowFrame(1, 0, gridPane, "src/main/java/res/window_patterns/batllo.txt"));
            drawableWindowFrame.add(new DrawableWindowFrame(3, 0, gridPane, "src/main/java/res/window_patterns/batllo.txt"));
            drawableWindowFrame.add(new DrawableWindowFrame(0, 2, gridPane, "src/main/java/res/window_patterns/batllo.txt"));
            drawableWindowFrame.add(new DrawableWindowFrame(4, 2, gridPane, "src/main/java/res/window_patterns/batllo.txt"));
        }

        return drawableWindowFrame;
    }
    public ArrayList<DrawableCardItem> setPrivObjCards(int numOfPlayers, GridPane gridPane){
        ArrayList<DrawableCardItem> privObjCards = new ArrayList<>();

        if(numOfPlayers == 2) {
            privObjCards.add(new PrivObjDrawableCard(2, 0, gridPane));
            privObjCards.add(new PrivObjDrawableCard(0, 2, gridPane));
        }
        else if (numOfPlayers == 3) {
            privObjCards.add(new PrivObjDrawableCard(1, 0, gridPane));
            privObjCards.add(new PrivObjDrawableCard(4, 0, gridPane));
            privObjCards.add(new PrivObjDrawableCard(2, 2, gridPane));
        }
        else {
            privObjCards.add(new PrivObjDrawableCard(0, 0, gridPane));
            privObjCards.add(new PrivObjDrawableCard(4, 0, gridPane));
            privObjCards.add(new PrivObjDrawableCard(1, 2, gridPane));
            privObjCards.add(new PrivObjDrawableCard(3, 2, gridPane));
        }

        return privObjCards;
    }
    public ArrayList<DrawableCardItem> setPubObjCards(int numOfPlayers, GridPane gridPane){
        ArrayList<DrawableCardItem> pubObjCards = new ArrayList<>();

        pubObjCards.add(new PubObjDrawableCard(3, 1, gridPane));

        return pubObjCards;
    }
    public ArrayList<DrawableCardItem> setToolCards(int numOfPlayers, GridPane gridPane){
        ArrayList<DrawableCardItem> toolCards = new ArrayList<>();
        toolCards.add(new ToolDrawableCard(2, 1, gridPane));
        return toolCards;
    }
    public ArrayList<DrawableDie> setDice(int numOfPlayers, GridPane gridPane){
        ArrayList<DrawableDie> dice = new ArrayList<>();
        dice.add(new DrawableDie(1, 1, gridPane));
        return dice;
    }

    /*public ArrayList<FavorPoint> setFavorPoints(int numOfPlayers){
        double x;
        double y;
        ArrayList<FavorPoint> favorPoints = new ArrayList<>();

        if(numOfPlayers == 2) {
            y = 360;
            x = 150;
            favorPoints.add(new FavorPoint(x, y, "res/9-eng.jpg"));
            x = 1650;
            favorPoints.add(new FavorPoint(x, y, "res/9-eng.jpg"));
        }
        else if (numOfPlayers == 3){
            y = 100;
            x = 150;
            favorPoints.add(new FavorPoint(x, y, "res/9-eng.jpg"));
            y = 930;
            x = 560;
            favorPoints.add(new FavorPoint(x, y, "res/9-eng.jpg"));
            y = 100;
            x = 1650;
            favorPoints.add(new FavorPoint(x, y, "res/9-eng.jpg"));
        }
        else {
            y = 160;
            x = 150;
            favorPoints.add(new FavorPoint(x, y, "res/9-eng.jpg"));
            y = 900;
            x = 150;
            favorPoints.add(new FavorPoint(x, y, "res/9-eng.jpg"));
            y = 160;
            x = 1650;
            favorPoints.add(new FavorPoint(x, y, "res/9-eng.jpg"));
            y = 900;
            x = 1650;
            favorPoints.add(new FavorPoint(x, y, "res/9-eng.jpg"));
        }

        return favorPoints;
    }*/
}
