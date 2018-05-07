package it.polimi.ingsw.view.gameMenu;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
    private MenuButton fullScreen;
    //private OverlayWrite startingWrite;

    public static void main(String args[]){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new Pane();
        scene = new Scene(root);
        gameMenu = new GameMenu();
        gameMenu.setVisible(true);
        primaryStage.setFullScreen(true);

        /*startingWrite = new OverlayWrite("  Press any key to start", 510, 100, Pos.CENTER_LEFT);
        startingWrite.manageText(50, Color.WHITE);
        startingWrite.manageRectangle(0.6, Color.BLACK);
        startingWrite.setVisible(true);*/

        loadFromFile("res/images/sagrada_menu_bg.png");

        /*startingWrite.setTranslateX(720);
        startingWrite.setTranslateY(900);*/

        fullScreen = gameMenu.getFullScreen();
        fullScreen.setOnMouseClicked(e -> setFullScreen(primaryStage));

        //hideAndShowMenu(scene);
        root.setPrefSize(1920, 1080);
        root.getChildren().addAll(imgView, gameMenu);
        primaryStage.setTitle("Sagrada Board Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadFromFile (String path) {
        try (InputStream is = Files.newInputStream(Paths.get(path))) {
            imgView = new ImageView(new Image(is));
            imgView.setFitWidth(1920);
            imgView.setFitHeight(1080);
        }
        catch (IOException e) {
            System.err.println ("Couldn't load background image");
        }
    }
    private void setFullScreen(Stage stage){
        if(stage.isFullScreen())
            stage.setFullScreen(false);
        else
            stage.setFullScreen(true);
    }
    private void hideAndShowMenu(Scene s){
        if (s == null) throw new NullPointerException();
        s.setOnKeyPressed(e -> {
            if(!gameMenu.isVisible()) {
                FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
                ft.setFromValue(0);
                ft.setToValue(1);

                gameMenu.setVisible(true);
                ft.play();
                //hideStartingWrite();
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
    /*private void hideStartingWrite(){
        if (startingWrite.isVisible()) {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.6), startingWrite);
            ft.setFromValue(1);
            ft.setToValue(0);

            startingWrite.setVisible(false);
            ft.play();
        }
    }*/
}