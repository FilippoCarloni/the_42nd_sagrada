package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.connection.client.ConnectionController;
import it.polimi.ingsw.connection.client.ConnectionType;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.gameboard.GameBoardController;
import it.polimi.ingsw.view.gui.gameboard.cards.CardsSetter;
import it.polimi.ingsw.view.gui.gameboard.windowframes.WindowFrameDrawer;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class GuiLauncher extends Application {

    private ConnectionController connectionController;
    private ConnectionType connectionType = ConnectionType.RMI;
    private ArrayList<GridPane> maps = new ArrayList<>();
    private Parent parent;

    @FXML
    private TextField username;
    @FXML
    private Label usernameNotValid;
    @FXML
    private GridPane map1 = new GridPane();
    @FXML
    private GridPane map2;
    @FXML
    private GridPane map3;
    @FXML
    private GridPane map4;
    @FXML
    private ImageView privObjCard;

    public void slowConnection(){
        this.connectionType = ConnectionType.SOCKET;
    }

    public void getUsername() throws Exception {
        checkAndSaveUsername(username.getText());
    }
    private void checkAndSaveUsername(String username) throws Exception {
        connectionController = new ConnectionController(connectionType);
        boolean isValid = connectionController.restore(username);
        if (isValid) {
            if(usernameNotValid.getText() != null){
                usernameNotValid.setText("");
                changeScene1();
            }
        } else
            usernameNotValid.setText(GUIParameters.LOGIN_ERROR);
    }
    private void changeScene1() {
        try {
            parent = FXMLLoader.load(getClass().getResource("/FXML_files/WindowFrameChoice.fxml"));
            //WARNING: you have to initialize map1 to use getScene() on it
            Scene scene = username.getScene();
            scene.setRoot(parent);
            Stage stage = (Stage) scene.getWindow();
            stage.setTitle(GUIParameters.MAIN_SCENE_TITLE);
            stage.setWidth(GUIParameters.SCREEN_WIDTH);
            stage.setHeight(GUIParameters.SCREEN_HEIGHT);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("File fxml not found");
        }
    }

    public void drawMapsAndSetCard(JSONObject json){
        JSONArray jsonMaps = (JSONArray) json.get(JSONTag.WINDOW_FRAMES);
        JSONObject card = (JSONObject) json.get(JSONTag.PRIVATE_OBJECTIVE);

        privObjCard = new CardsSetter().setPrivateCard(privObjCard, card);
        setMaps();
        for(int i = 0; i < GUIParameters.NUM_MAPS_TO_CHOOSE; i++){
            new WindowFrameDrawer().frameFiller(maps.get(i), (JSONObject) jsonMaps.get(i));
        }
    }
    private void setMaps(){
        maps.add(map1);
        maps.add(map2);
        maps.add(map3);
        maps.add(map4);
    }

    public void choose1(){
        clicked(1);
    }
    public void choose2(){
        clicked(2);
    }
    public void choose3(){
        clicked(3);
    }
    public void choose4(){
        clicked(4);
    }

    private void clicked(int idMapChosen) {
        GameBoardController controller = new GameBoardController();
        controller.setIdMapChosen(idMapChosen);
        connectionController.send("window " + idMapChosen);
        changeScene(controller);
    }
    private void changeScene(GameBoardController controller) {
        try {
            controller.initialize();
            parent = FXMLLoader.load(getClass().getResource("/FXML_files/MainBoard.fxml"));
            //WARNING: you have to initialize map1 to use getScene() on it
            Scene scene = map1.getScene();
            scene.setRoot(parent);
            Stage stage = (Stage) scene.getWindow();
            stage.setTitle(GUIParameters.MAIN_SCENE_TITLE);
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("File fxml not found");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        parent = FXMLLoader.load(getClass().getResource(GUIParameters.CONNECTION_LOGIN_FXML_PATH));
        primaryStage.setTitle(GUIParameters.CONNECTION_LOGIN_SCENE_TITLE);

        Scene scene = new Scene(parent, GUIParameters.CONNECTION_LOGIN_SCENE_WIDTH, GUIParameters.CONNECTION_LOGIN_SCENE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
