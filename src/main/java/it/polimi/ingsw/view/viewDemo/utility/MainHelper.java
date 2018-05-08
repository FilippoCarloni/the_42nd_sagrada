package it.polimi.ingsw.view.viewdemo.utility;

import it.polimi.ingsw.view.viewdemo.databaseview.*;
import java.util.ArrayList;

public class MainHelper {

    //The final version of this methods will have an ArrayList<String> paths as parameter,
    //to allow loading multiple images of Window Frames/privObjCard, one for each player
    public ArrayList<WindowFrame> setWindowFrames(int numOfPlayers){
        double x;
        double y;
        ArrayList<WindowFrame> windowFrame = new ArrayList<>();

        if(numOfPlayers == 2) {
            y = 460;
            x = 10;
            windowFrame.add(new WindowFrame(x, y, "res/9-eng.jpg"));
            x = 1510;
            windowFrame.add(new WindowFrame(x, y, "res/9-eng.jpg"));
        }
        else if (numOfPlayers == 3){
            y = 200;
            x = 10;
            windowFrame.add(new WindowFrame(x, y, "res/9-eng.jpg"));
            y = 850;
            x = 705;
            windowFrame.add(new WindowFrame(x, y, "res/9-eng.jpg"));
            y = 200;
            x = 1510;
            windowFrame.add(new WindowFrame(x, y, "res/9-eng.jpg"));
        }
        else {
            y = 260;
            x = 10;
            windowFrame.add(new WindowFrame(x, y, "res/9-eng.jpg"));
            y = 660;
            x = 10;
            windowFrame.add(new WindowFrame(x, y, "res/9-eng.jpg"));
            y = 260;
            x = 1510;
            windowFrame.add(new WindowFrame(x, y, "res/9-eng.jpg"));
            y = 660;
            x = 1510;
            windowFrame.add(new WindowFrame(x, y, "res/9-eng.jpg"));
        }

        return windowFrame;
    }
    public ArrayList<CardItem> setPrivObjCards(int numOfPlayers){
        double x;
        double y;
        ArrayList<CardItem> privObjCards = new ArrayList<>();

        if(numOfPlayers == 2) {
            y = 360;
            x = 10;
            privObjCards.add(new PrivObjCard(x, y, "res/9-eng.jpg"));
            x = 1790;
            privObjCards.add(new PrivObjCard(x, y, "res/9-eng.jpg"));
        }
        else if (numOfPlayers == 3){
            y = 100;
            x = 10;
            privObjCards.add(new PrivObjCard(x, y, "res/9-eng.jpg"));
            y = 1000;
            x = 560;
            privObjCards.add(new PrivObjCard(x, y, "res/9-eng.jpg"));
            y = 100;
            x = 1790;
            privObjCards.add(new PrivObjCard(x, y, "res/9-eng.jpg"));
        }
        else {
            y = 160;
            x = 10;
            privObjCards.add(new PrivObjCard(x, y, "res/9-eng.jpg"));
            y = 900;
            x = 10;
            privObjCards.add(new PrivObjCard(x, y, "res/9-eng.jpg"));
            y = 160;
            x = 1790;
            privObjCards.add(new PrivObjCard(x, y, "res/9-eng.jpg"));
            y = 900;
            x = 1790;
            privObjCards.add(new PrivObjCard(x, y, "res/9-eng.jpg"));
        }

        return privObjCards;
    }
    //The final version of this methods will have an ArrayList<String> paths as parameter,
    //to allow loading multiple images of PubObj/Tool Cards
    public ArrayList<CardItem> setPubObjCards(double x, double y){
        ArrayList<CardItem> pubObjCards = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            pubObjCards.add(new PubObjCard(x, y, "res/9-eng.jpg"));
            x += 120;
        }
        return pubObjCards;
    }
    public ArrayList<CardItem> setToolCards(double x, double y){
        ArrayList<CardItem> toolCards = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            toolCards.add(new ToolCard(x, y, "res/9-eng.jpg"));
            x += 120;
        }
        return toolCards;
    }
    //The total num of Favor Points depends on Window Frame difficulty; not used yet, but
    //in the future I will implement each favor point
    public ArrayList<FavorPoint> setFavorPoints(int numOfPlayers){
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
    }
}
