package it.polimi.ingsw.view.gui.preliminarystages;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.gameboard.cards.CardsSetter;
import it.polimi.ingsw.view.gui.gameboard.windowframes.WindowFrameDrawer;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import static jdk.nashorn.internal.objects.Global.print;

/**
 * Controller class for the window frame choice screen.
 */

public class WindowFramesChoice {

    //Container of the maps
    private ArrayList<GridPane> maps = new ArrayList<>();

    //Container of the maps' names
    private ArrayList<Label> names = new ArrayList<>();

    //Containers of the maps' difficulties
    private  ArrayList<Label> difficulties = new ArrayList<>();

    //ToggleGroup that will contain all RadioButton for the map choice
    private ToggleGroup group = new ToggleGroup();

    @FXML
    private GridPane map1;
    @FXML
    private GridPane map2;
    @FXML
    private GridPane map3;
    @FXML
    private GridPane map4;
    @FXML
    private Label nameMap1;
    @FXML
    private Label nameMap2;
    @FXML
    private Label nameMap3;
    @FXML
    private Label nameMap4;
    @FXML
    private RadioButton buttonMap1;
    @FXML
    private RadioButton buttonMap2;
    @FXML
    private RadioButton buttonMap3;
    @FXML
    private RadioButton buttonMap4;
    @FXML
    private Label difficultyMap1;
    @FXML
    private Label difficultyMap2;
    @FXML
    private Label difficultyMap3;
    @FXML
    private Label difficultyMap4;
    @FXML
    private Label privateObjectiveTitle;
    @FXML
    private Rectangle privateObjectiveRectangle;
    @FXML
    private TextArea privateObjectiveDescription;
    @FXML
    private Button gameBoardButton;

    /**
     * This four methods are called by the mouse clicking on every RadioButton under the maps.
     * They call the private method clicked(), sending the number of the chosen map, then the
     * method sends a message to the Connection Controller, with the number of the chosen map.
     */
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

    //Support methods for map choice
    private void clicked(int idMapChosen) {
        try {
            GuiManager.getInstance().getConnectionController().send(GUIParameters.WINDOW + idMapChosen);
        } catch (ConnectException e) {
            print(e.getMessage());
        }
    }
    private void addRadioButtonsInToggleGroup(){
        buttonMap1.setToggleGroup(group);
        buttonMap2.setToggleGroup(group);
        buttonMap3.setToggleGroup(group);
        buttonMap4.setToggleGroup(group);
    }

    //Methods for preliminary stage
    private void drawPreGame(JSONObject json){
        JSONArray jsonMaps = (JSONArray) json.get(JSONTag.WINDOW_FRAMES);
        JSONObject card = (JSONObject) json.get(JSONTag.PRIVATE_OBJECTIVE);

        CardsSetter.setPrivateCard(card, privateObjectiveTitle, privateObjectiveRectangle, privateObjectiveDescription);
        setMaps();
        setNames();
        setDifficulties();
        for(int i = 0; i < GUIParameters.NUM_MAPS_TO_CHOOSE; i++){
            ArrayList<Canvas> canvas = new ArrayList<>();
            ArrayList<StackPane> stackPanes = new ArrayList<>();
            WindowFrameDrawer.frameFiller(canvas, stackPanes, maps.get(i), 1, false);
            WindowFrameDrawer.framePainterManager((JSONObject) jsonMaps.get(i), canvas, stackPanes, 1, false);
            names.get(i).setText(((JSONObject)jsonMaps.get(i)).get(JSONTag.NAME).toString());
            difficulties.get(i).setText(GUIParameters.DIFFICULTY + parseInt(((((JSONObject)jsonMaps.get(i)).get(JSONTag.DIFFICULTY))).toString()));
        }
    }
    private void setMaps(){
        maps.add(map1);
        maps.add(map2);
        maps.add(map3);
        maps.add(map4);
    }
    private void setNames(){
        names.add(nameMap1);
        names.add(nameMap2);
        names.add(nameMap3);
        names.add(nameMap4);
    }
    private void setDifficulties(){
        difficulties.add(difficultyMap1);
        difficulties.add(difficultyMap2);
        difficulties.add(difficultyMap3);
        difficulties.add(difficultyMap4);
    }

    /**
     * Getter for the gameBoardButton, used by the update() method in GuiManager to make it not disabled.
     * @return this.gameBoardButton.
     * @see GuiManager
     */
    public Button getGameBoardButton(){
        return gameBoardButton;
    }

    /**
     * Method that switch from the map choice screen to the game board. Used by clicking on the gameBoardButton in this screen, or
     * by clicking on startButton in the lobby screen if a game is already started.
     * @param event: the ActionEvent generated when a player clicks on the button.
     * @see LobbyController
     */
    public void launchGameBoard(ActionEvent event){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.DEFAULT_FXML_DIRECTORY + GUIParameters.GAME_BOARD_FXML_PATH));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            GuiManager.setOnCloseRequest(stage);
            stage.setTitle(GUIParameters.MAIN_SCENE_TITLE);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void initialize() throws ConnectException {
        GuiManager.getInstance().setWindowFramesChoice(this);
        addRadioButtonsInToggleGroup();
        drawPreGame(GuiManager.getInstance().getPreGameMessage());
        gameBoardButton.setDisable(true);
    }
}
