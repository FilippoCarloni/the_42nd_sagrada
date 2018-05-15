package it.polimi.ingsw.view.viewdemo;

import it.polimi.ingsw.view.viewdemo.databaseview.*;
import it.polimi.ingsw.view.viewdemo.settings.GUIParameters;
import it.polimi.ingsw.view.viewdemo.utility.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.List;

public class ViewStart extends Application {

    private GridPane root;
    private Scene scene;
    private MainHelper helper;
    private RoundTrack roundTrack;
    private DiceBag diceBag;
    private List<ColumnConstraints> columnConstraints;
    private List<RowConstraints> rowConstraints;
    private List<DrawableWindowFrame> drawableWindowFrame;
    private List<FavorPoint> favorPoints;
    private List<CardItem> privObjCards, pubObjCards, toolCards;
    private List<Die> dice;
    private int numOfPlayers;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        try {
            root = new GridPane();
            root.setPrefWidth(GUIParameters.SCREEN_WIDTH);
            root.setPrefHeight(GUIParameters.SCREEN_HEIGHT);
            root.setMinWidth(GUIParameters.SCREEN_WIDTH);
            root.setMinHeight(GUIParameters.SCREEN_HEIGHT);

            scene = new Scene(root, GUIParameters.SCREEN_WIDTH, GUIParameters.SCREEN_HEIGHT);
            helper = new MainHelper();
            primaryStage.setFullScreen(true);
            root.setGridLinesVisible(true);

            rowConstraints = helper.setRowConstraints(helper.setNumRows(numOfPlayers));
            columnConstraints = helper.setColumnConstraints(GUIParameters.NUM_COLUMNS);
            helper.setHBoxOnGrid(root, helper.setNumRows(numOfPlayers), GUIParameters.NUM_COLUMNS);

            drawableWindowFrame = helper.setWindowFrames(numOfPlayers, root);
            privObjCards = helper.setPrivObjCards(numOfPlayers, root);
            pubObjCards = helper.setPubObjCards(numOfPlayers, root);
            toolCards = helper.setToolCards(numOfPlayers, root);
            dice = helper.setDice(numOfPlayers, root);
            roundTrack = new RoundTrack(0, 1, root);

            root.setPrefSize(GUIParameters.SCREEN_WIDTH, GUIParameters.SCREEN_HEIGHT);
            root.setStyle("-fx-background-color: #8fbc8f");
            root.getRowConstraints().addAll(rowConstraints);
            root.getColumnConstraints().addAll(columnConstraints);
            root.getChildren().add(roundTrack);
            root.getChildren().addAll(dice);
            root.getChildren().addAll(drawableWindowFrame);
            root.getChildren().addAll(privObjCards);
            root.getChildren().addAll(pubObjCards);
            root.getChildren().addAll(toolCards);


            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setNumOfPlayers(int numOfPlayers) {
        if (numOfPlayers > 4) throw new IllegalArgumentException("Too many players");
        this.numOfPlayers = numOfPlayers;
    }
}
