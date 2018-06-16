package it.polimi.ingsw.view.gui.preliminarystages.lobby;

import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

import static jdk.nashorn.internal.objects.Global.print;

//TODO: add something that can launch directly the main board, if a player requests a reconnection

public class LobbyController {

    @FXML
    private Button playButton;
    @FXML
    private TextArea textLobby;

    //Lobby Management
    public void enteredLobby(){
        GuiManager.getInstance().getConnectionController().send("play");
    }
    public void playClicked(ActionEvent event){
        lobbyToPreGame(event);
    }
    public void quitClicked(){
        GuiManager.getInstance().getConnectionController().send("exit");
        System.exit(0);
    }
    public void printConnectionOrDisconnection(String message){
        textLobby.appendText(message + "\n");
    }

    public Button getPlayButton(){
        return playButton;
    }

    private void lobbyToPreGame(ActionEvent event){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.MAP_CHOICE_FXML_PATH));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(GUIParameters.MAP_CHOICE_SCENE_TITLE);
            stage.setScene(scene);
        } catch (IOException e) {
            print(e.getMessage());
        }
    }

    @FXML
    protected void initialize(){
        GuiManager.getInstance().setLobbyController(this);
        playButton.setDisable(true);
    }
}
