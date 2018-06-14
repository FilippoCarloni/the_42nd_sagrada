package it.polimi.ingsw.view.gui.preliminarystages.windowframeschoice;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.gameboard.GameBoardController;
import it.polimi.ingsw.view.gui.gameboard.cards.CardsSetter;
import it.polimi.ingsw.view.gui.gameboard.windowframes.WindowFrameDrawer;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import java.util.ArrayList;

import static jdk.nashorn.internal.objects.Global.print;

//TODO: add button to launch Game Board after map selection

public class WindowFramesChoice {

    private ArrayList<GridPane> maps = new ArrayList<>();
    private ArrayList<StackPane> names = new ArrayList<>();
    private  ArrayList<StackPane> difficulties = new ArrayList<>();

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
    private StackPane difficultyMap1;
    @FXML
    private StackPane difficultyMap2;
    @FXML
    private StackPane difficultyMap3;
    @FXML
    private StackPane difficultyMap4;
    @FXML
    private ImageView privObjCard;
    @FXML
    private GridPane privObjGrid;
    @FXML
    private Button gameBoardButton;

    //Map Choice Management
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
        GuiManager.getInstance().getConnectionController().send("window " + idMapChosen);
    }

    //Methods for preliminary stage
    private void drawPreGame(JSONObject json){
        JSONArray jsonMaps = (JSONArray) json.get(JSONTag.WINDOW_FRAMES);
        JSONObject card = (JSONObject) json.get(JSONTag.PRIVATE_OBJECTIVE);

        privObjCard = new CardsSetter().setPrivateCard(privObjCard, card);
        privObjGrid.add(privObjCard, 1, 0);
        setMaps();
        setNames();
        setDifficulties();
        for(int i = 0; i < GUIParameters.NUM_MAPS_TO_CHOOSE; i++){
            new WindowFrameDrawer().frameFiller(maps.get(i), (JSONObject) jsonMaps.get(i));
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

    public void quitClicked(){
        GuiManager.getInstance().getConnectionController().send("exit");
        System.exit(0);
    }
    public void launchGameBoard(ActionEvent event){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.GAME_BOARD_FXML_PATH));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(GUIParameters.MAIN_SCENE_TITLE);
            stage.setScene(scene);
        } catch (IOException e) {
            print(GUIParameters.LOAD_FXML_ERROR);
        }
    }

    public Button getGameBoardButton(){
        return gameBoardButton;
    }

    @FXML
    protected void initialize() {
        //I need this to initialize @FXML objects
        GuiManager.getInstance().setWindowFramesChoice(this);
        drawPreGame(GuiManager.getInstance().getPreGameMessage());
        gameBoardButton.setDisable(true);
    }
}
