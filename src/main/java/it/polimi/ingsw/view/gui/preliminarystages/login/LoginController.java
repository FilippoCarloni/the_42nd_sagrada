package it.polimi.ingsw.view.gui.preliminarystages.login;

import it.polimi.ingsw.connection.client.ConnectionType;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static jdk.nashorn.internal.objects.Global.print;

public class LoginController {

    private ConnectionType connectionType = ConnectionType.RMI;

    @FXML
    private TextField username;
    @FXML
    private Label usernameNotValid;

    //Connection selection
    public void slowConnection(){
        connectionType = ConnectionType.SOCKET;
    }

    //Username and Login Management
    public void getUsername(ActionEvent event) {
        checkAndSaveUsername(username.getText(), event);
    }
    private void checkAndSaveUsername(String username, ActionEvent event) {
        GUIParameters.setGlobalHelper(connectionType);
        GUIParameters.globalHelper.startRefresh();
        boolean isValid = GUIParameters.globalHelper.getConnectionController().restore(username);
        if (isValid) {
            loginToPlayOrQuitChoice(event);
        }
        else
            usernameNotValid.setText(GUIParameters.LOGIN_ERROR);
    }

    //Change scene Management
    private void loginToPlayOrQuitChoice(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.PLAY_OR_QUIT_FXML_PATH));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(GUIParameters.PLAY_OR_QUIT_TITLE);
            stage.setScene(scene);
        } catch (IOException e) {
            print(GUIParameters.LOAD_FXML_ERROR);
        }
    }

    @FXML
    protected void initialize(){
        //I need it to initialize @FXML objects
    }
}
