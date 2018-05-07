package it.polimi.ingsw.view.gameMenu;

import it.polimi.ingsw.view.viewDemo.*;
import it.polimi.ingsw.view.viewDemo.utility.MainHelper;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GameMenu extends Parent {

    private VBox mainMenu, settingsMenu, difficultyMenu, startGameMenu;
    private MenuButton btnNewGame, btnResume, btnOptions, btnExit;
    private MenuButton btnSound, btnDifficulty, btnFullScren, btnBack;
    private MenuButton btnEasy, btnNormal, btnBackToOptions, btnBackToMainMenu;
    private MenuButton btnTwoPlayers, btnThreePlayers, btnFourPlayers, btnBackMain;

    private Main main;

    public GameMenu(){
        main = new Main();

        mainMenu = setMenu();
        settingsMenu = setMenu();
        difficultyMenu = setMenu();
        startGameMenu = setMenu();

        setMainMenuButtons();
        setSettingsMenuButtons();
        setDifficultyMenuButtons();
        setStartGameMenu();

        btnNewGame.setOnMouseClicked(e -> changeMenu(mainMenu, startGameMenu));
        btnOptions.setOnMouseClicked(e -> changeMenu(mainMenu, settingsMenu));
        btnExit.setOnMouseClicked(e -> System.exit(0));

        btnDifficulty.setOnMouseClicked(e -> changeMenu(settingsMenu, difficultyMenu));
        btnBack.setOnMouseClicked(e -> changeMenu(settingsMenu, mainMenu));

        btnBackToOptions.setOnMouseClicked(e -> changeMenu(difficultyMenu, settingsMenu));
        btnBackToMainMenu.setOnMouseClicked(e -> changeMenu(difficultyMenu, mainMenu));

        btnTwoPlayers.setOnMouseClicked(e -> launchViewDemo(2));
        btnThreePlayers.setOnMouseClicked(e -> launchViewDemo(3));
        btnFourPlayers.setOnMouseClicked(e -> launchViewDemo(4));
        btnBackMain.setOnMouseClicked(e -> changeMenu(startGameMenu, mainMenu));

        mainMenu.getChildren().addAll(btnNewGame, btnResume, btnOptions, btnExit);
        settingsMenu.getChildren().addAll(btnSound, btnDifficulty, btnFullScren, btnBack);
        difficultyMenu.getChildren().addAll(btnEasy, btnNormal, btnBackToOptions, btnBackToMainMenu);
        startGameMenu.getChildren().addAll(btnTwoPlayers, btnThreePlayers, btnFourPlayers, btnBackMain);

        Rectangle bg = new Rectangle(1920, 1080);
        bg.setFill(Color.GRAY);
        bg.setOpacity(0.4);

        getChildren().addAll(bg, mainMenu);
    }

    private VBox setMenu(){
        VBox menu = new VBox(20);
        menu.setTranslateX(720);
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
        btnFullScren = new MenuButton(" FULLSCREEN", "Enable/Disable full screen");
        btnBack = new MenuButton(" BACK", "Go back to main menu");
    }
    private void setDifficultyMenuButtons(){
        btnEasy = new MenuButton(" EASY", "Enable dynamic points visualisation");
        btnNormal = new MenuButton(" NORMAL", "Disable dynamic points visualisation");
        btnBackToOptions = new MenuButton(" BACK TO SETTINGS", "Go back to settings");
        btnBackToMainMenu = new MenuButton(" BACK TO MAIN MENU", "Go back to main menu");
    }
    private void setStartGameMenu(){
        btnTwoPlayers = new MenuButton(" 2 PLAYERS", "");
        btnThreePlayers = new MenuButton(" 3 PLAYERS", "");
        btnFourPlayers = new MenuButton(" 4 PLAYERS", "");
        btnBackMain = new MenuButton(" BACK", "");
    }

    public MenuButton getFullScreen(){
        return btnFullScren;
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
    private void launchViewDemo(int numOfPlayers) {
        main.setNumOfPlayers(numOfPlayers);
        try {
            main.start(new Stage());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}