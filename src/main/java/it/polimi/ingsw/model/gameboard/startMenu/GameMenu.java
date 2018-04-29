package it.polimi.ingsw.model.gameboard.startMenu;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameMenu extends Parent {

    public GameMenu(){
        //Menu0 main menu, menu1 is options menu
        VBox menu0 = new VBox(20);
        VBox menu1 = new VBox(20);
        menu0.setTranslateX(400);
        menu0.setTranslateY(500);
        menu1.setTranslateX(400);
        menu1.setTranslateY(500);

        //Main menu buttons
        MenuButton btnNewGame = new MenuButton(" NEW GAME");
        MenuButton btnResume = new MenuButton(" RESUME");
        MenuButton btnOptions = new MenuButton(" OPTIONS");
        MenuButton btnExit = new MenuButton(" EXIT");

        //Options menu buttons
        MenuButton btnSound = new MenuButton(" SOUND");
        //MenuButton btnVideo = new MenuButton(" VIDEO");
        MenuButton btnDifficulty = new MenuButton(" DIFFICULTY");
        MenuButton btnBack = new MenuButton(" BACK");

        //Main menu buttons mouse effects
        btnNewGame.setOnMouseClicked(e -> {
            //It hides main menu, and nothing more
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(evt -> this.setVisible(false));
            ft.play();
        });
        btnResume.setOnMouseClicked(e -> {
            //It hides main menu, just like New Game
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(evt -> this.setVisible(false));
            ft.play();
        });
        btnOptions.setOnMouseClicked(e -> {
            getChildren().add(menu1);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.01), menu0);
            tt.setToX(menu0.getTranslateX());
            TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
            tt1.setToX(menu0.getTranslateX());

            tt.play();
            tt1.play();

            tt.setOnFinished(evt -> {
                getChildren().remove(menu0);
            });
        });
        btnExit.setOnMouseClicked(e -> System.exit(0));

        //Options menu buttons mouse effects
        btnBack.setOnMouseClicked(e -> {
            getChildren().add(menu0);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.01), menu1);
            tt.setToX(menu1.getTranslateX());
            TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
            tt1.setToX(menu0.getTranslateX());

            tt.play();
            tt1.play();

            tt.setOnFinished(evt -> {
                getChildren().remove(menu1);
            });
        });

        menu0.getChildren().addAll(btnNewGame, btnResume, btnOptions, btnExit);
        menu1.getChildren().addAll(btnSound, btnDifficulty, btnBack);

        Rectangle bg = new Rectangle(1209, 1614);
        bg.setFill(Color.GRAY);
        bg.setOpacity(0.4);

        getChildren().addAll(bg, menu0);
    }

}