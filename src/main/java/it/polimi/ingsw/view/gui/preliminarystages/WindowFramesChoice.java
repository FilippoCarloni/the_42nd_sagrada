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
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static jdk.nashorn.internal.objects.Global.print;

/**
 * Controller class for the window frame choice screen.
 */

public class WindowFramesChoice {

    //GridPane in which I will draw the map between the player has to choose
    private ArrayList<GridPane> maps = new ArrayList<>();

    //Containers of the maps' names
    private ArrayList<StackPane> names = new ArrayList<>();

    //Containers of the maps' difficulties
    private  ArrayList<StackPane> difficulties = new ArrayList<>();

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
    private StackPane nameMap1;
    @FXML
    private StackPane nameMap2;
    @FXML
    private StackPane nameMap3;
    @FXML
    private StackPane nameMap4;
    @FXML
    private RadioButton buttonMap1;
    @FXML
    private RadioButton buttonMap2;
    @FXML
    private RadioButton buttonMap3;
    @FXML
    private RadioButton buttonMap4;
    @FXML
    private StackPane difficultyMap1;
    @FXML
    private StackPane difficultyMap2;
    @FXML
    private StackPane difficultyMap3;
    @FXML
    private StackPane difficultyMap4;
    @FXML
    private ImageView privateObjCard;
    @FXML
    private GridPane privateObjGrid;
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

    private void clicked(int idMapChosen) {
        try {
            GuiManager.getInstance().getConnectionController().send("window " + idMapChosen);
        } catch (ConnectException | RemoteException e) {
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

        privateObjCard = CardsSetter.setPrivateCard(card);
        privateObjGrid.add(privateObjCard, 1, 0);
        setMaps();
        setNames();
        setDifficulties();
        for(int i = 0; i < GUIParameters.NUM_MAPS_TO_CHOOSE; i++){
            ArrayList<Canvas> canvas = new ArrayList<>();
            ArrayList<StackPane> stackPanes = new ArrayList<>();
            WindowFrameDrawer.frameFiller(canvas, stackPanes, maps.get(i), 1, false);
            WindowFrameDrawer.framePainterManager((JSONObject) jsonMaps.get(i), canvas, stackPanes, 1, false);
            Text name = new Text(((JSONObject)jsonMaps.get(i)).get(JSONTag.NAME).toString());
            setText(name, names.get(i));
            Text difficulty = new Text("Difficulty: " + ((Long)(((JSONObject)jsonMaps.get(i)).get(JSONTag.DIFFICULTY))).intValue());
            setText(difficulty, difficulties.get(i));
        }
    }
    private void setText(Text text, StackPane pane){
        Rectangle rectangle = new Rectangle(305, 30);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        pane.getChildren().addAll(rectangle, text);
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
            stage.setOnCloseRequest(e -> {
                try {
                    GuiManager.getInstance().getConnectionController().send("exit");
                } catch (ConnectException | RemoteException e1) {
                    print(e1.getMessage());
                }
                System.exit(0);
            });
            stage.setTitle(GUIParameters.MAIN_SCENE_TITLE);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void initialize() throws RemoteException, ConnectException {
        GuiManager.getInstance().setWindowFramesChoice(this);
        addRadioButtonsInToggleGroup();
        drawPreGame(GuiManager.getInstance().getPreGameMessage());
        gameBoardButton.setDisable(true);
    }
}
