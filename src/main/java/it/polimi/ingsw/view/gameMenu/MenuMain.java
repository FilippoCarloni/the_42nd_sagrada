package it.polimi.ingsw.view.gameMenu;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MenuMain extends Application {

    private GameMenu gameMenu;
    private Pane root;
    private ImageView imgView;
    private Scene scene;
    private OverlayWrite startingWrite;

    public static void main(String args[]){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new Pane();
        scene = new Scene(root);
        gameMenu = new GameMenu();
        gameMenu.setVisible(false);

        startingWrite = new OverlayWrite("  Press any key to start", 510, 100, Pos.CENTER_LEFT);
        startingWrite.manageText(50, Color.WHITE);
        startingWrite.manageRectangle(0.6, Color.BLACK);
        startingWrite.setVisible(true);

        try (InputStream is = Files.newInputStream(Paths.get("res/images/sagrada_bg.jpg"))) {
         imgView = new ImageView(new Image(is));
         imgView.setFitWidth(1209);
         imgView.setFitHeight(1614);
        }
        catch (IOException e) {
            System.err.println ("Couldn't load background image");
        }

        startingWrite.setTranslateX(360);
        startingWrite.setTranslateY(900);

        hideAndShowMenu(scene);
        root.setPrefSize(1209, 1080);
        root.getChildren().addAll(imgView, gameMenu, startingWrite);
        primaryStage.setTitle("Sagrada Board Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Pressing ESC you can hide/show the main menu
    private void hideAndShowMenu(Scene s){
        if (s == null) throw new NullPointerException();
        s.setOnKeyPressed(e -> {
            if(!gameMenu.isVisible()) {
                FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
                ft.setFromValue(0);
                ft.setToValue(1);

                gameMenu.setVisible(true);
                ft.play();
                hideStartingWrite();
            }
            else {
                FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.setOnFinished(evt -> gameMenu.setVisible(false));
                ft.play();
            }
        });
    }
    private void hideStartingWrite(){
        if (startingWrite.isVisible()) {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.6), startingWrite);
            ft.setFromValue(1);
            ft.setToValue(0);

            startingWrite.setVisible(false);
            ft.play();
        }
    }
}