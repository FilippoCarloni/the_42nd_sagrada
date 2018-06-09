package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connection.client.Client;
import it.polimi.ingsw.connection.client.ConnectionController;
import it.polimi.ingsw.view.gui.login.LoginController;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiLauncher extends Application {

    private Stage primaryStage;

    public void chooseRMIConnection() throws Exception {
        connectionHandler("RMI");
    }
    public void chooseSocketConnection() throws Exception {
        connectionHandler("Socket");
    }
    private void connectionHandler(String typeOfConnection) throws Exception {
        //Management of type of connection, sending information to server
        setStage(new Stage());
        new LoginController().start(primaryStage);
    }

    public void setStage(Stage stage){
        this.primaryStage = stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setStage(primaryStage);
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML_files/ConnectionChoice.fxml"));
        primaryStage.setTitle(GUIParameters.CONNECTION_SCENE_TITLE);

        Scene scene = new Scene(parent, GUIParameters.CONNECTION_LOGIN_SCENE_WIDTH, GUIParameters.CONNECTION_LOGIN_SCENE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}
