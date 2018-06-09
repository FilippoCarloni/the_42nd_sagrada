package it.polimi.ingsw.view.gui.login;

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

public class LoginController extends Application {

    @FXML
    private TextField username = new TextField();
    @FXML
    private Label usernameNotValid;

    public void getUsername() throws Exception {
        checkAndSaveUsername(username.getText());
    }

    private void checkAndSaveUsername(String username) throws Exception {
        boolean isValid = false;                                             //answer from controller
        if(isValid) {
            System.out.println(username);
            Stage stage = new Stage();
            new GameBoardLauncher().start(stage);
        }
        else{
            usernameNotValid.setText("Username not valid!");
        }

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML_files/LoginScreen.fxml"));
        primaryStage.setTitle(GUIParameters.LOGIN_SCENE_TITLE);

        Scene scene = new Scene(parent, GUIParameters.CONNECTION_LOGIN_SCENE_WIDTH, GUIParameters.CONNECTION_LOGIN_SCENE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
