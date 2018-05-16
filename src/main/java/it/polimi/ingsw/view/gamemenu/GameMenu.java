package it.polimi.ingsw.view.gamemenu;

import it.polimi.ingsw.view.viewdemo.*;

import it.polimi.ingsw.view.viewdemo.settings.GUIParameters;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
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

    private ViewStart view;

    public GameMenu(){
        view = new ViewStart();

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

        Rectangle bg = new Rectangle(GUIParameters.SCREEN_WIDTH, GUIParameters.SCREEN_HEIGHT);
        bg.setFill(Color.GRAY);
        bg.setOpacity(0.4);

        getChildren().addAll(bg, mainMenu);
    }

    private VBox setMenu(){
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setTranslateX((GUIParameters.SCREEN_WIDTH / 1.6) - GUIParameters.MENU_BUTTON_WIDTH);
        menu.setTranslateY((GUIParameters.SCREEN_HEIGHT / 2.3) - GUIParameters.MENU_BUTTON_HEIGHT);
        return menu;
    }
    private void setMainMenuButtons(){
        btnNewGame = new MenuButton(" NEW GAME", "Start a new game");
        btnResume = new MenuButton(" RESUME", "Load the last game saved");
        btnOptions = new MenuButton(" SETTINGS", "Sound and difficult settings");
        btnExit = new MenuButton(" EXIT", "Close view menu");
    }
    private void setSettingsMenuButtons(){
        btnSound = new MenuButton(" SOUND", "Set audio preferences");
        btnDifficulty = new MenuButton(" DIFFICULTY", "Enable/Disable rules description");
        btnFullScren = new MenuButton(" FULLSCREEN", "Enable/Disable full screen");
        btnBack = new MenuButton(" BACK", "Go back to view menu");
    }
    private void setDifficultyMenuButtons(){
        btnEasy = new MenuButton(" EASY", "Enable dynamic points visualisation");
        btnNormal = new MenuButton(" NORMAL", "Disable dynamic points visualisation");
        btnBackToOptions = new MenuButton(" BACK TO SETTINGS", "Go back to settings");
        btnBackToMainMenu = new MenuButton(" BACK TO MAIN MENU", "Go back to view menu");
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
        //It hides view menu, and nothing more
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

        tt.setOnFinished(e -> getChildren().remove(menuComing));
    }
    private void launchViewDemo(int numOfPlayers) {
        view.setNumOfPlayers(numOfPlayers);
        try {
            view.start(new Stage());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}