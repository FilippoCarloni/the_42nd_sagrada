package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.CONNECTION_LOGIN_FXML_PATH));
        primaryStage.setTitle(GUIParameters.CONNECTION_LOGIN_SCENE_TITLE);

        Scene scene = new Scene(parent, GUIParameters.CONNECTION_LOGIN_SCENE_WIDTH, GUIParameters.CONNECTION_LOGIN_SCENE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}
