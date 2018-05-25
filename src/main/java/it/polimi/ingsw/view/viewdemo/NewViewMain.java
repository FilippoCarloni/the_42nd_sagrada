package it.polimi.ingsw.view.viewdemo;

import it.polimi.ingsw.view.viewdemo.gameboard.MainBoardController;
import it.polimi.ingsw.view.viewdemo.settings.GUIParameters;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class NewViewMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/FXML_files/MainBoard.fxml"));
        primaryStage.setTitle(GUIParameters.SCENE_TITLE);

        Scene scene = new Scene(root, GUIParameters.SCREEN_WIDTH, GUIParameters.SCREEN_HEIGHT);
        //scene.getStylesheets().add(NewViewMain.class.getResource("loginCSS.css").toExternalForm());
        new MainBoardController().launchDrawableWindowFrameGenerator();

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
