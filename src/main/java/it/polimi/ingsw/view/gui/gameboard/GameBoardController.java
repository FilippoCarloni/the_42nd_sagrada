package it.polimi.ingsw.view.gui.gameboard;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.gameboard.cards.CardsSetter;
import it.polimi.ingsw.view.gui.gameboard.dice.DiceDrawer;
import it.polimi.ingsw.view.gui.gameboard.roundtrack.RoundTrackDrawer;
import it.polimi.ingsw.view.gui.gameboard.windowframes.WindowFrameDrawer;
import it.polimi.ingsw.view.gui.settings.GUIColor;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static jdk.nashorn.internal.objects.Global.print;

//TODO: add image on Anchor Pane Main Player Background
//TODO: add stylesheets files (.css)
//TODO: add favor points
//TODO: add round track
//TODO: add a button that will display all dice on round track
//TODO: append messages to messageTextArea
//TODO: take a look on bugs
//TODO: add points counter at the end of the match

public class GameBoardController {

    /**
     * Round Track StackPane container
     */
    private ArrayList<StackPane> panesOnRoundTrack = new ArrayList<>();

    /**
     * Player's names and window frames containers
     */
    private ArrayList<Label> playersNameLabels = new ArrayList<>();
    private ArrayList<GridPane> playersGrids = new ArrayList<>();

    /**
     * Element to draw window frames containers
     */
    private Map<Integer, ArrayList<Canvas>> canvasOnGrids;
    private Map<Integer, ArrayList<StackPane>> panesOnGrids;

    /**
     * Element to draw dice pool containers
     */
    private ArrayList<StackPane> panesOnDicePool = new ArrayList<>();
    private ArrayList<Canvas> canvasOnDicePool = new ArrayList<>();

    /**
     * JSON containers
     */
    private JSONArray players;
    private JSONObject mainPlayer;

    /**
     * Round Track reference
     */
    private RoundTrackDrawer rDrawer;

    @FXML
    private AnchorPane roundTrackAnchorPane;
    @FXML
    private GridPane roundTrackGrid;
    @FXML
    private AnchorPane backGroundMainPlayer;
    @FXML
    private GridPane windowFramePlayer1;
    @FXML
    private GridPane windowFramePlayer2;
    @FXML
    private GridPane windowFramePlayer3;
    @FXML
    private GridPane windowFramePlayer4;
    @FXML
    private Label labelPlayer1;
    @FXML
    private Label labelPlayer2;
    @FXML
    private Label labelPlayer3;
    @FXML
    private Label labelPlayer4;
    @FXML
    private GridPane diceGrid;
    @FXML
    private GridPane gridCards;
    @FXML
    private Rectangle privateObjectiveRectangle;
    @FXML
    private StackPane draftedDieStackPane;
    @FXML
    private Canvas draftedDieCanvas;
    @FXML
    private TextArea messageTextArea;


    //Setters and getters
    private void setGrid(){
        windowFramePlayer1.setTranslateX(GUIParameters.PLAYER_1_GRID_X);
        windowFramePlayer1.setTranslateY(GUIParameters.PLAYER_1_GRID_Y);
        diceGrid.setGridLinesVisible(false);

        playersGrids.add(windowFramePlayer2);
        playersGrids.add(windowFramePlayer3);
        playersGrids.add(windowFramePlayer4);
    }
    private void setPlayersNameLabels(){
        playersNameLabels.add(labelPlayer2);
        playersNameLabels.add(labelPlayer3);
        playersNameLabels.add(labelPlayer4);
    }
    private void setMaps(){
        canvasOnGrids = new HashMap<>();
        panesOnGrids = new HashMap<>();
        canvasOnGrids.put(getPlayer1ByUsername(players), new ArrayList<>());
        panesOnGrids.put(getPlayer1ByUsername(players), new ArrayList<>());
        for(int i = 0; i < players.size(); i++){
            if(i != getPlayer1ByUsername(players)){
                canvasOnGrids.put(i, new ArrayList<>());
                panesOnGrids.put(i, new ArrayList<>());
            }
        }
    }
    public List<StackPane> getPanesOnWindowFrame(){
        return panesOnGrids.get(getPlayer1ByUsername(players));
    }

    //Main Player getter
    public JSONObject getMainPlayer(){
        return mainPlayer;
    }

    //Maps and labels management
    private int getPlayer1ByUsername(JSONArray players){
        for(int i = 0; i < players.size(); i++){
            if((((JSONObject)players.get(i)).get(JSONTag.USERNAME)).toString().equals(GuiManager.getInstance().getUsernamePlayer1())){
                return i;
            }
        }
        throw new IllegalArgumentException("Player 1 not found");
    }
    private void drawMapAndSetUsername(ArrayList<Canvas> canvasArrayList, ArrayList<StackPane> stackPanes, JSONObject player, Label labelPlayer, double scale, boolean editable){
        labelPlayer.setText(player.get(JSONTag.USERNAME).toString());
        WindowFrameDrawer.frameReset((JSONObject) player.get(JSONTag.WINDOW_FRAME), canvasArrayList, stackPanes, scale, editable);
    }
    private void fillFirstTimeMap(){
        int j = 0;
        WindowFrameDrawer.frameFiller(canvasOnGrids.get(getPlayer1ByUsername(players)), panesOnGrids.get(getPlayer1ByUsername(players)), windowFramePlayer1, 1, true);
        for(int i = 0; i < players.size(); i++) {
            if(i != getPlayer1ByUsername(players)) {
                WindowFrameDrawer.frameFiller(canvasOnGrids.get(i), panesOnGrids.get(i), playersGrids.get(j), GUIParameters.REDUCTION_SCALE, false);
                j++;
            }
        }
    }

    //Cards management
    private void addCardsOnGameBoard(JSONObject json){
        List<ImageView> toolCards = CardsSetter.setPublicCards((JSONArray) json.get(JSONTag.TOOLS), GUIParameters.TOOL_DIRECTORY, true);
        List<ImageView> pubObjCards = CardsSetter.setPublicCards((JSONArray) json.get(JSONTag.PUBLIC_OBJECTIVES), GUIParameters.PUBOBJ_DIRECTORY, false);

        for (int i = 0, j = 0; i < 5 && j < toolCards.size(); i += 2, j++) {
            gridCards.add(toolCards.get(j), i, 0);
            gridCards.add(pubObjCards.get(j), i, 2);
        }
    }
    private void privateObjectiveRectangleFiller(JSONObject privateObjectiveCard){
        int id = parseInt(privateObjectiveCard.get(JSONTag.CARD_ID).toString());
        privateObjectiveRectangle.setFill(GUIColor.findById(id).getColor());
    }

    //Messages printing into TextArea
    public void setMessageText(String message){
        messageTextArea.appendText(message);
    }

    //Methods used by action buttons
    public void pass(){
        GuiManager.getInstance().getConnectionController().send("pass");
    }
    public void undo(){
        GuiManager.getInstance().getConnectionController().send("undo");
    }
    public void redo(){
        GuiManager.getInstance().getConnectionController().send("redo");
    }
    public void showPrivateObjective(){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.DEFAULT_FXML_DIRECTORY + GUIParameters.PRIVATE_OBJECTIVE_FXML_PATH));
            Stage stage = new Stage();
            stage.setTitle(GUIParameters.PRIVATE_OBJECTIVE_TITLE + " - " + GuiManager.getInstance().getUsernamePlayer1());
            stage.setScene(new Scene(parent, GUIParameters.PRIVATE_OBJECTIVE_SCENE_WIDTH, GUIParameters.PRIVATE_OBJECTIVE_SCENE_HEIGHT));
            stage.show();
        } catch (IOException e){
            print(e.getMessage());
        }
    }
    public void quit(){
        GuiManager.getInstance().getConnectionController().send("exit");
        System.exit(0);
    }

    //Main method, called by the update() method from Gui Manager to handle gui refresh
    public void gameBoardUpdate(JSONObject json){
        players = (JSONArray) ((JSONObject) json.get(JSONTag.TURN_MANAGER)).get(JSONTag.PLAYERS);
        mainPlayer = (JSONObject) players.get(getPlayer1ByUsername(players));
        updater(json);
    }

    //Updater support method
    private void updater(JSONObject json){
        int j = 0;
        drawMapAndSetUsername(canvasOnGrids.get(getPlayer1ByUsername(players)), panesOnGrids.get(getPlayer1ByUsername(players)), mainPlayer, labelPlayer1, 1, true);
        for(int i = 0; i < players.size(); i++) {
            if(i != getPlayer1ByUsername(players)) {
                drawMapAndSetUsername(canvasOnGrids.get(i), panesOnGrids.get(i), (JSONObject) players.get(i), playersNameLabels.get(j), GUIParameters.REDUCTION_SCALE, false);
                j++;
            }
        }
        manageDraftedDie(json);
        DiceDrawer.dicePoolReset(json, panesOnDicePool, canvasOnDicePool);
    }
    private void manageDraftedDie(JSONObject json){
        draftedDieCanvas.getGraphicsContext2D().clearRect(0, 0, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * 1.5, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * 1.5);
        draftedDieStackPane.setStyle(GUIParameters.BACKGROUND_COLOR_STRING + GUIParameters.DEFAULT_DICE_COLOR);
        if(json.get(JSONTag.PICKED_DIE) != null) {
            JSONObject draftedDie = (JSONObject) json.get(JSONTag.PICKED_DIE);
            //Problems when a tool card that modifies his value is activated
            int value = parseInt((draftedDie.get(JSONTag.SHADE)).toString());
            String color = draftedDie.get(JSONTag.COLOR).toString();

            DiceDrawer.dicePointsDrawer(value, color, draftedDieCanvas.getGraphicsContext2D(), draftedDieStackPane, 1.5);
        }
    }

    //First game board draw, called by initialize
    private void firstUpdate(JSONObject json){
        players = (JSONArray)((JSONObject)json.get(JSONTag.TURN_MANAGER)).get(JSONTag.PLAYERS);
        mainPlayer = (JSONObject) players.get(getPlayer1ByUsername(players));

        Group group = new Group();
        StackPane pane = new StackPane();

        rDrawer = new RoundTrackDrawer();

        setGrid();
        setPlayersNameLabels();
        setMaps();

        rDrawer.roundTrackStartingFiller(roundTrackGrid, panesOnRoundTrack);
        fillFirstTimeMap();
        privateObjectiveRectangleFiller((JSONObject) mainPlayer.get(JSONTag.PRIVATE_OBJECTIVE));
        DiceDrawer.diceFiller(diceGrid, panesOnDicePool, canvasOnDicePool, ((JSONArray) json.get(JSONTag.DICE_POOL)).size(), true);
        updater(json);
        addCardsOnGameBoard(json);

        pane.getChildren().add(windowFramePlayer1);
        group.getChildren().add(pane);
        backGroundMainPlayer.getChildren().add(group);
    }

    @FXML
    public void initialize(){
        GuiManager.getInstance().setGameBoard(this);
        JSONObject json = GuiManager.getInstance().getGameBoardMessage();
        messageTextArea.setEditable(false);
        firstUpdate(json);
    }
}
