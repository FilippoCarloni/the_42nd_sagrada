package it.polimi.ingsw.view.viewDemo.utility;

import it.polimi.ingsw.view.viewDemo.databaseView.*;
import java.util.ArrayList;

public class MainHelper {

    //The final version of this methods will have an ArrayList<String> paths as parameter,
    //to allow loading multiple images of Window Frames/privObjCard, one for each player
    public ArrayList<WindowFrame> setWindowFrames(int numOfPlayers){
        double X;
        double Y;
        ArrayList<WindowFrame> windowFrame = new ArrayList<>();

        if(numOfPlayers == 2) {
            Y = 460;
            X = 10;
            windowFrame.add(new WindowFrame(X, Y, "res/9-eng.jpg"));
            X = 1510;
            windowFrame.add(new WindowFrame(X, Y, "res/9-eng.jpg"));
        }
        else if (numOfPlayers == 3){
            Y = 200;
            X = 10;
            windowFrame.add(new WindowFrame(X, Y, "res/9-eng.jpg"));
            Y = 850;
            X = 705;
            windowFrame.add(new WindowFrame(X, Y, "res/9-eng.jpg"));
            Y = 200;
            X = 1510;
            windowFrame.add(new WindowFrame(X, Y, "res/9-eng.jpg"));
        }
        else {
            Y = 260;
            X = 10;
            windowFrame.add(new WindowFrame(X, Y, "res/9-eng.jpg"));
            Y = 660;
            X = 10;
            windowFrame.add(new WindowFrame(X, Y, "res/9-eng.jpg"));
            Y = 260;
            X = 1510;
            windowFrame.add(new WindowFrame(X, Y, "res/9-eng.jpg"));
            Y = 660;
            X = 1510;
            windowFrame.add(new WindowFrame(X, Y, "res/9-eng.jpg"));
        }

        return windowFrame;
    }
    public ArrayList<CardItem> setPrivObjCards(int numOfPlayers){
        double X;
        double Y;
        ArrayList<CardItem> privObjCards = new ArrayList<>();

        if(numOfPlayers == 2) {
            Y = 360;
            X = 10;
            privObjCards.add(new PrivObjCard(X, Y, "res/9-eng.jpg"));
            X = 1790;
            privObjCards.add(new PrivObjCard(X, Y, "res/9-eng.jpg"));
        }
        else if (numOfPlayers == 3){
            Y = 100;
            X = 10;
            privObjCards.add(new PrivObjCard(X, Y, "res/9-eng.jpg"));
            Y = 1000;
            X = 560;
            privObjCards.add(new PrivObjCard(X, Y, "res/9-eng.jpg"));
            Y = 100;
            X = 1790;
            privObjCards.add(new PrivObjCard(X, Y, "res/9-eng.jpg"));
        }
        else {
            Y = 160;
            X = 10;
            privObjCards.add(new PrivObjCard(X, Y, "res/9-eng.jpg"));
            Y = 900;
            X = 10;
            privObjCards.add(new PrivObjCard(X, Y, "res/9-eng.jpg"));
            Y = 160;
            X = 1790;
            privObjCards.add(new PrivObjCard(X, Y, "res/9-eng.jpg"));
            Y = 900;
            X = 1790;
            privObjCards.add(new PrivObjCard(X, Y, "res/9-eng.jpg"));
        }

        return privObjCards;
    }
    //The final version of this methods will have an ArrayList<String> paths as parameter,
    //to allow loading multiple images of PubObj/Tool Cards
    public ArrayList<CardItem> setPubObjCards(double X, double Y){
        ArrayList<CardItem> pubObjCards = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            pubObjCards.add(new PubObjCard(X, Y, "res/9-eng.jpg"));
            X += 120;
        }
        return pubObjCards;
    }
    public ArrayList<CardItem> setToolCards(double X, double Y){
        ArrayList<CardItem> toolCards = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            toolCards.add(new PubObjCard(X, Y, "res/9-eng.jpg"));
            X += 120;
        }
        return toolCards;
    }
    //The total num of Favor Points depends on Window Frame difficulty; not used yet, but
    //in the future I will implement each favor point
    public ArrayList<FavorPoint> setFavorPoints(int numOfPlayers){
        double X;
        double Y;
        ArrayList<FavorPoint> favorPoints = new ArrayList<>();

        if(numOfPlayers == 2) {
            Y = 360;
            X = 150;
            favorPoints.add(new FavorPoint(X, Y, "res/9-eng.jpg"));
            X = 1650;
            favorPoints.add(new FavorPoint(X, Y, "res/9-eng.jpg"));
        }
        else if (numOfPlayers == 3){
            Y = 100;
            X = 150;
            favorPoints.add(new FavorPoint(X, Y, "res/9-eng.jpg"));
            Y = 1140;
            X = 560;
            favorPoints.add(new FavorPoint(X, Y, "res/9-eng.jpg"));
            Y = 100;
            X = 1650;
            favorPoints.add(new FavorPoint(X, Y, "res/9-eng.jpg"));
        }
        else {
            Y = 160;
            X = 150;
            favorPoints.add(new FavorPoint(X, Y, "res/9-eng.jpg"));
            Y = 900;
            X = 150;
            favorPoints.add(new FavorPoint(X, Y, "res/9-eng.jpg"));
            Y = 160;
            X = 1650;
            favorPoints.add(new FavorPoint(X, Y, "res/9-eng.jpg"));
            Y = 900;
            X = 1650;
            favorPoints.add(new FavorPoint(X, Y, "res/9-eng.jpg"));
        }

        return favorPoints;
    }
}
