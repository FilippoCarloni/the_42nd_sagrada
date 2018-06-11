package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connection.client.ConnectionController;
import it.polimi.ingsw.connection.client.ConnectionType;
import it.polimi.ingsw.view.gui.gameboard.GameBoardLauncher;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class GuiLauncher extends Application {

    private Stage primaryStage;
    private ConnectionController connectionController;
    private ConnectionType connectionType = ConnectionType.RMI;
    @FXML
    private TextField username;
    @FXML
    private Label usernameNotValid;

    public void slowConnection(){
        this.connectionType = ConnectionType.SOCKET;
    }

    public void getUsername() throws Exception {
        checkAndSaveUsername(username.getText());
    }
    private void checkAndSaveUsername(String username) throws Exception {
        connectionController = new ConnectionController(connectionType);
        boolean isValid = connectionController.restore(username);
        if (isValid)
            new GameBoardLauncher().start(new Stage());
        else
            usernameNotValid.setText("Username not valid, insert a new one");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML_files/ConnectionLogin.fxml"));
        this.primaryStage.setTitle(GUIParameters.CONNECTION_SCENE_TITLE);

        Scene scene = new Scene(parent, GUIParameters.CONNECTION_LOGIN_SCENE_WIDTH, GUIParameters.CONNECTION_LOGIN_SCENE_HEIGHT);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }
}
