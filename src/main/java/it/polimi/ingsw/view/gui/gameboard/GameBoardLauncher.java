package it.polimi.ingsw.view.gui.gameboard;

import it.polimi.ingsw.view.gui.gameboard.windowframes.WindowFrameChoice;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameBoardLauncher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML_files/MainBoard.fxml"));
        primaryStage.setTitle(GUIParameters.MAIN_SCENE_TITLE);

        Scene scene = new Scene(parent, GUIParameters.SCREEN_WIDTH, GUIParameters.SCREEN_HEIGHT);
        //scene.getStylesheets().add(GuiLauncher.class.getResource("loginCSS.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
        new WindowFrameChoice().start(new Stage());
    }
    public static void main(String[] args){
        launch(args);
    }
}
