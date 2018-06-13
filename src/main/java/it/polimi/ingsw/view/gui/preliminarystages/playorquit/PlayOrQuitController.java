package it.polimi.ingsw.view.gui.preliminarystages.playorquit;

import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static jdk.nashorn.internal.objects.Global.print;

public class PlayOrQuitController {

    //Play or Quit Management
    public void playClicked(ActionEvent event){
        GUIParameters.globalHelper.getConnectionController().send("play");
        playOrQuitToLobby(event);
    }
    public void quitClicked(){
        GUIParameters.globalHelper.getConnectionController().send("exit");
        System.exit(0);
    }

    //Change scene Management
    private void playOrQuitToLobby(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.LOBBY_FXML_PATH));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(GUIParameters.LOBBY_TITLE);
            stage.setScene(scene);
        } catch (IOException e) {
            print(GUIParameters.LOAD_FXML_ERROR);
        }
    }

}
