package it.polimi.ingsw.view.gameMenu;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class GameMenu extends Parent {

    private VBox mainMenu, settingsMenu, difficultyMenu;
    private MenuButton btnNewGame, btnResume, btnOptions, btnExit;
    private MenuButton btnSound, btnDifficulty, btnBack;
    private MenuButton btnEasy, btnNormal, btnBackToOptions, btnBackToMainMenu;

    public GameMenu(){
        mainMenu = setMenu();
        settingsMenu = setMenu();
        difficultyMenu = setMenu();

        setMainMenuButtons();
        setSettingsMenuButtons();
        setDifficultyMenuButtons();

        btnOptions.setOnMouseClicked(e -> changeMenu(mainMenu, settingsMenu));
        btnExit.setOnMouseClicked(e -> System.exit(0));

        btnDifficulty.setOnMouseClicked(e -> changeMenu(settingsMenu, difficultyMenu));
        btnBack.setOnMouseClicked(e -> changeMenu(settingsMenu, mainMenu));

        btnBackToOptions.setOnMouseClicked(e -> changeMenu(difficultyMenu, settingsMenu));
        btnBackToMainMenu.setOnMouseClicked(e -> changeMenu(difficultyMenu, mainMenu));

        mainMenu.getChildren().addAll(btnNewGame, btnResume, btnOptions, btnExit);
        settingsMenu.getChildren().addAll(btnSound, btnDifficulty, btnBack);
        difficultyMenu.getChildren().addAll(btnEasy, btnNormal, btnBackToOptions, btnBackToMainMenu);

        Rectangle bg = new Rectangle(1209, 1614);
        bg.setFill(Color.GRAY);
        bg.setOpacity(0.4);

        getChildren().addAll(bg, mainMenu);
    }

    private VBox setMenu(){
        VBox menu = new VBox(20);
        menu.setTranslateX(400);
        menu.setTranslateY(500);
        return menu;
    }
    private void setMainMenuButtons(){
        btnNewGame = new MenuButton(" NEW GAME", "Start a new game");
        btnResume = new MenuButton(" RESUME", "Load the last game saved");
        btnOptions = new MenuButton(" SETTINGS", "Sound and difficult settings");
        btnExit = new MenuButton(" EXIT", "Close main menu");
    }
    private void setSettingsMenuButtons(){
        btnSound = new MenuButton(" SOUND", "Set audio preferences");
        btnDifficulty = new MenuButton(" DIFFICULTY", "Enable/Disable rules description");
        btnBack = new MenuButton(" BACK", "Go back to main menu");
    }
    private void setDifficultyMenuButtons(){
        btnEasy = new MenuButton(" EASY", "Enable rules description");
        btnNormal = new MenuButton(" NORMAL", "Disable rules description");
        btnBackToOptions = new MenuButton(" BACK TO SETTINGS", "Go back to settings");
        btnBackToMainMenu = new MenuButton(" BACK TO MAIN MENU", "Go back to main menu");
    }

    private void hideMenu(){
        //It hides main menu, and nothing more
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(evt -> this.setVisible(false));
        ft.play();
    }
    private void changeMenu(VBox menuComing, VBox menuGoing) {
        if (menuComing == null) throw new NullPointerException("Menu coming null");
        if (menuGoing == null) throw new NullPointerException("Menu going null");

        getChildren().add(menuGoing);

        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.01), menuComing);
        tt.setToX(menuComing.getTranslateX());
        TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menuGoing);
        tt1.setToX(menuComing.getTranslateX());

        tt.play();
        tt1.play();

        tt.setOnFinished(e -> {
            getChildren().remove(menuComing);
        });
    }

}