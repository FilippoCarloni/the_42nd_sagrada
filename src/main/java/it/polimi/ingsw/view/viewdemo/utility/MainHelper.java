package it.polimi.ingsw.view.viewdemo.utility;

import it.polimi.ingsw.view.viewdemo.databaseview.*;
import it.polimi.ingsw.view.viewdemo.settings.GUIParameters;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;

public class MainHelper {

    public ArrayList<ColumnConstraints> setColumnConstraints(){
        ArrayList<ColumnConstraints> columnConstraints = new ArrayList<>();

        for (int i = 0; i < GUIParameters.NUM_COLUMNS; i++){
            columnConstraints.add(new ColumnConstraints());
            columnConstraints.get(i).setPrefWidth(GUIParameters.SCREEN_WIDTH / GUIParameters.NUM_COLUMNS);
        }

        return columnConstraints;
    }
    public ArrayList<RowConstraints> setRowConstraints(int numOfPlayers){
        int numRows = setNumRows(numOfPlayers);
        ArrayList<RowConstraints> rowConstraints = new ArrayList<>();

        for (int i = 0; i < numRows; i++){
            rowConstraints.add(new RowConstraints());
            rowConstraints.get(i).setPrefHeight(GUIParameters.SCREEN_HEIGHT / numRows);
        }

        return rowConstraints;
    }
    private int setNumRows(int numOfPlayers){
        if(numOfPlayers == 2)
            return 4;
        else
            return 5;
    }
    public void setRectangleOnGrid(GridPane root, int numOfPlayers){
        for(int i = 0; i < setNumRows(numOfPlayers); i++){
            for(int j = 0; j < GUIParameters.NUM_COLUMNS; j++){
                HBox hBox = new HBox();
                root.add(hBox, j, i);
            }
        }
    }

    public ArrayList<WindowFrame> setWindowFrames(int numOfPlayers, GridPane gridPane){
        ArrayList<WindowFrame> windowFrame = new ArrayList<>();

        if(numOfPlayers == 2) {
            windowFrame.add(new WindowFrame(1, 0, gridPane));
            windowFrame.add(new WindowFrame(1, 2, gridPane));
        }
        else if (numOfPlayers == 3){
            windowFrame.add(new WindowFrame(2, 0, gridPane));
            windowFrame.add(new WindowFrame(4, 1, gridPane));
            windowFrame.add(new WindowFrame(1, 2, gridPane));
        }
        else {
            windowFrame.add(new WindowFrame(1, 0, gridPane));
            windowFrame.add(new WindowFrame(3, 0, gridPane));
            windowFrame.add(new WindowFrame(0, 2, gridPane));
            windowFrame.add(new WindowFrame(4, 2, gridPane));
        }

        return windowFrame;
    }
    public ArrayList<CardItem> setPrivObjCards(int numOfPlayers, GridPane gridPane){
        ArrayList<CardItem> privObjCards = new ArrayList<>();

        if(numOfPlayers == 2) {
            privObjCards.add(new PrivObjCard(2, 0, gridPane));
            privObjCards.add(new PrivObjCard(0, 2, gridPane));
        }
        else if (numOfPlayers == 3) {
            privObjCards.add(new PrivObjCard(1, 0, gridPane));
            privObjCards.add(new PrivObjCard(4, 0, gridPane));
            privObjCards.add(new PrivObjCard(2, 2, gridPane));
        }
        else {
            privObjCards.add(new PrivObjCard(0, 0, gridPane));
            privObjCards.add(new PrivObjCard(4, 0, gridPane));
            privObjCards.add(new PrivObjCard(1, 2, gridPane));
            privObjCards.add(new PrivObjCard(3, 2, gridPane));
        }

        return privObjCards;
    }
    public ArrayList<CardItem> setPubObjCards(int numOfPlayers, GridPane gridPane){
        ArrayList<CardItem> pubObjCards = new ArrayList<>();

        pubObjCards.add(new PubObjCard(3, 1, gridPane));

        return pubObjCards;
    }
    public ArrayList<CardItem> setToolCards(int numOfPlayers, GridPane gridPane){
        ArrayList<CardItem> toolCards = new ArrayList<>();
        toolCards.add(new ToolCard(2, 1, gridPane));
        return toolCards;
    }
    public ArrayList<Die> setDice(int numOfPlayers, GridPane gridPane){
        ArrayList<Die> dice = new ArrayList<>();
        dice.add(new Die(1, 1, gridPane));
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
