package it.polimi.ingsw.view.gui.preliminarystages;

import it.polimi.ingsw.connection.client.ConnectionType;
import it.polimi.ingsw.view.gui.GuiManager;
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
import java.net.ConnectException;

import static jdk.nashorn.internal.objects.Global.print;

/**
 * Controller class for the login screen, the starting screen
 */

public class LoginController {

    //Default connection type: RMI; able to switch into socket pressing on "slow connection".
    private ConnectionType connectionType = ConnectionType.RMI;

    @FXML
    private TextField username;
    @FXML
    private Label usernameNotValid;

    /**
     * Method that allows player to switch from the default connection type, RMI, tho a socket one.
     */
    public void slowConnection(){
        if(connectionType == ConnectionType.RMI) {
            connectionType = ConnectionType.SOCKET;
        } else {
            connectionType = ConnectionType.RMI;
        }
    }

    /**
     * Method that handle the username sending to the Connection Controller, making a validity control. If the
     * username chosen is not valid, it prints an alert text, and doesn't allow a login with a not-valid username.
     * @param event: the ActionEvent generated when a player clicks on the button.
     */
    public void getUsername(ActionEvent event) {
        GuiManager.setConnectionType(connectionType);
        try {
            GuiManager.getInstance().startRefresh();
            boolean isValid = GuiManager.getInstance().getConnectionController().restore(username.getText());
            if (isValid) {
                GuiManager.getInstance().setUsernameMainPlayer(username.getText());
                loginToLobby(event);
            } else
                usernameNotValid.setText(GUIParameters.LOGIN_ERROR);
        } catch (ConnectException e) {
            usernameNotValid.setText(GUIParameters.SERVER_ERROR);
        }
    }

    //Change scene Management
    private void loginToLobby(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.DEFAULT_FXML_DIRECTORY + GUIParameters.LOBBY_FXML_PATH));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setOnCloseRequest(e -> System.exit(0));
            stage.setTitle(GUIParameters.LOBBY_TITLE + " - " + GuiManager.getInstance().getUsernameMainPlayer());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
