package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.gui.utility.GUIParameters;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Launcher for Gui Client
 */

public class Gui extends Application {

    /**
     * Start method, it launch the Application.
     * @param primaryStage: Stage in which the Application will be drawn.
     * @throws Exception: throws Exception if the Application drawing can't be made successfully.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.DEFAULT_FXML_DIRECTORY + GUIParameters.CONNECTION_LOGIN_FXML_PATH));
        primaryStage.setTitle(GUIParameters.CONNECTION_LOGIN_SCENE_TITLE);
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}
