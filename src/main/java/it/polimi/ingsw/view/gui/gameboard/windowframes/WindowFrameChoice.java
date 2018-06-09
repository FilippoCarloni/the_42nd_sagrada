package it.polimi.ingsw.view.gui.gameboard.windowframes;

import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class WindowFrameChoice extends Application {

    @FXML
    private GridPane map1;
    @FXML
    private GridPane map2;
    @FXML
    private GridPane map3;
    @FXML
    private GridPane map4;

    //I will need a JSON file containing all the windowframes between which the player will choose his own

    public void choose1(){
        clicked(map1);
    }
    public void choose2(){
        clicked(map2);
    }
    public void choose3(){
        clicked(map3);
    }
    public void choose4(){
        clicked(map4);
    }

    private void clicked(GridPane chosenMap){
        //There will be the drawn map's sending to GameBoardController and the info's sending to controller, and then this stage will be closed
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML_files/WindowFrameChoice.fxml"));
        primaryStage.setTitle(GUIParameters.CHOICE_SCENE_TITLE);

        //There will be 4 calls to drawWindowFrame method, for every map sent from the server

        Scene scene = new Scene(parent, GUIParameters.SCREEN_WIDTH, GUIParameters.MAP_CHOICE_SCREEN_HEIGHT);
        //scene.getStylesheets().add(GuiLauncher.class.getResource("loginCSS.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
