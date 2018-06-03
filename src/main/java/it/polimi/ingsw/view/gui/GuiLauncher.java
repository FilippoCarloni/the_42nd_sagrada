package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.gui.login.LoginController;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiLauncher extends Application {

    private Stage stage;
    private boolean loginLaunched = false;

    public void chooseRMIConnection() throws Exception {
        connectionHandler("RMI");
    }
    public void chooseSocketConnection() throws Exception {
        connectionHandler("Socket");
    }
    private void connectionHandler(String typeOfConnection) throws Exception {
        System.out.println(typeOfConnection);
        Stage newStage = new Stage();
        new LoginController().start(newStage);
        loginLaunched = true;
    }
    private void setStage(Stage stage){
        this.stage = stage;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML_files/ConnectionChoice.fxml"));
        primaryStage.setTitle(GUIParameters.CONNECTION_SCENE_TITLE);

        setStage(primaryStage);

        Scene scene = new Scene(parent, GUIParameters.CONNECTION_LOGIN_SCENE_WIDTH, GUIParameters.CONNECTION_LOGIN_SCENE_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}
