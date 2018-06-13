package it.polimi.ingsw.view.gui.preliminarystages.lobby;

import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

import static jdk.nashorn.internal.objects.Global.print;

public class LobbyController {

    @FXML
    private TextArea textLobby;

    //Lobby Management
    public void quitClicked(){
        GUIParameters.globalHelper.getConnectionController().send("exit");
        System.exit(0);
    }
    public void printConnectionOrDisconnection(String message){
        textLobby.appendText(message + "\n");
    }

    public void lobbyToPreGame(){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.MAP_CHOICE_FXML_PATH));
            Scene scene = textLobby.getScene();
            Stage stage = (Stage) textLobby.getScene().getWindow();
            /*stage.setTitle(GUIParameters.MAP_CHOICE_SCENE_TITLE);
            stage.setWidth(GUIParameters.SCREEN_WIDTH);
            stage.setHeight(GUIParameters.SCREEN_HEIGHT);*/
            scene.setRoot(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            print(GUIParameters.LOAD_FXML_ERROR);
        }
    }

    @FXML
    protected void initialize(){
        GUIParameters.globalHelper.setLobbyController(this);
    }

}
