package it.polimi.ingsw.model.gameboard.startMenu;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MenuMain extends Application {

    private GameMenu gameMenu;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        InputStream is = Files.newInputStream(Paths.get("res/images/sagrada_bg.jpg"));
        Image img = new Image(is);
        ImageView imgView = new ImageView(img);
        Scene scene = new Scene(root);
        this.gameMenu = new GameMenu();
        this.gameMenu.setVisible(false);

        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ESCAPE){
                if(!this.gameMenu.isVisible()){
                    FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
                    ft.setFromValue(0);
                    ft.setToValue(1);

                    gameMenu.setVisible(true);
                    ft.play();
                }
                else{
                    FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
                    ft.setFromValue(1);
                    ft.setToValue(0);
                    ft.setOnFinished(evt -> gameMenu.setVisible(false));
                    ft.play();
                }
            }
        });

        is.close();
        root.setPrefSize(1209, 1614);
        imgView.setFitWidth(1209);
        imgView.setFitHeight(1614);
        root.getChildren().addAll(imgView, gameMenu);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args[]){
        launch(args);
    }
}