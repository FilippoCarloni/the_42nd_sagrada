package it.polimi.ingsw.view.viewDemo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import it.polimi.ingsw.view.viewDemo.databaseView.*;
import it.polimi.ingsw.view.viewDemo.utility.*;

import java.util.ArrayList;

public class Main extends Application {

    private AnchorPane root;
    private Scene scene;
    private MainHelper helper;
    private RoundTrack roundTrack;
    private Die dice;
    private DiceBag diceBag;
    private ArrayList<WindowFrame> windowFrame;
    private ArrayList<FavorPoint> favorPoints;
    private ArrayList<CardItem> privObjCards, pubObjCards, toolCards;
    private int numOfPlayers;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        root = new AnchorPane();
        scene = new Scene(root);
        helper = new MainHelper();
        windowFrame = new ArrayList<>();
        favorPoints = new ArrayList<>();
        privObjCards = new ArrayList<>();
        pubObjCards = new ArrayList<>();
        toolCards = new ArrayList<>();
        primaryStage.setFullScreen(true);

        if(numOfPlayers == 3) {
            roundTrack = new RoundTrack(520, 260, "res/9-eng.jpg");
            dice = new Die(1100, 530, "res/9-eng.jpg");
            diceBag = new DiceBag(1060, 280, "res/9-eng.jpg");
            pubObjCards = helper.setPubObjCards(560, 500);
            toolCards = helper.setToolCards(560, 560);
        }
        else{
            roundTrack = new RoundTrack(520, 340, "res/9-eng.jpg");
            dice = new Die(1100, 610, "res/9-eng.jpg");
            diceBag = new DiceBag(1060, 360, "res/9-eng.jpg");
            pubObjCards = helper.setPubObjCards(560, 580);
            toolCards = helper.setToolCards(560, 640);
        }
        windowFrame = helper.setWindowFrames(numOfPlayers);
        favorPoints = helper.setFavorPoints(numOfPlayers);
        privObjCards = helper.setPrivObjCards(numOfPlayers);

        root.setPrefSize(1920, 1080);
        root.setStyle("-fx-background-color: #8fbc8f");
        root.getChildren().addAll(roundTrack, dice, diceBag);
        root.getChildren().addAll(windowFrame);
        root.getChildren().addAll(favorPoints);
        root.getChildren().addAll(privObjCards);
        root.getChildren().addAll(pubObjCards);
        root.getChildren().addAll(toolCards);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Will be used by controller class to set num of players playing the game
    public void setNumOfPlayers(int numOfPlayers) throws IllegalArgumentException {
        if (numOfPlayers > 4) throw new IllegalArgumentException("Too many players");
        this.numOfPlayers = numOfPlayers;
    }
}
