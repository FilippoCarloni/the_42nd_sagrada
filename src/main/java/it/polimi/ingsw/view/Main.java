package it.polimi.ingsw.view;

import it.polimi.ingsw.view.gamemenu.GameMenu;
import it.polimi.ingsw.view.gamemenu.MenuButton;
import it.polimi.ingsw.view.viewdemo.settings.GUIParameters;

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

public class Main extends Application {

    private GameMenu gameMenu;
    private Pane root;
    private ImageView imgView;
    private Scene scene;
    private MenuButton fullScreen;

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

        loadFromFile(GUIParameters.BG_IMAGE_PATH);

        fullScreen = gameMenu.getFullScreen();
        fullScreen.setOnMouseClicked(e -> setFullScreen(primaryStage));

        root.setPrefSize(GUIParameters.SCREEN_WIDTH, GUIParameters.SCREEN_HEIGHT);
        root.getChildren().addAll(imgView, gameMenu);
        primaryStage.setTitle(GUIParameters.SCENE_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadFromFile (String path) {
        try (InputStream is = Files.newInputStream(Paths.get(path))) {
            imgView = new ImageView(new Image(is));
            imgView.setFitWidth(GUIParameters.SCREEN_WIDTH);
            imgView.setFitHeight(GUIParameters.SCREEN_HEIGHT);
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
}