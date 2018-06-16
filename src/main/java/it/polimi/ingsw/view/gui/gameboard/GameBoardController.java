package it.polimi.ingsw.view.gui.gameboard;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.gameboard.cards.CardsSetter;
import it.polimi.ingsw.view.gui.gameboard.dice.DiceDrawer;
import it.polimi.ingsw.view.gui.gameboard.roundtrack.RoundTrackDrawer;
import it.polimi.ingsw.view.gui.gameboard.windowframes.WindowFrameDrawer;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


//TODO: add favor points
//TODO: add undo, redo, quit and private objective buttons
//TODO: add round track
//TODO: MAKE DICE POOL'S DICE RESPONSIVE

public class GameBoardController {

    //Cards containers
    private ArrayList<ImageView> toolCards = new ArrayList<>();
    private ArrayList<ImageView> pubObjCards = new ArrayList<>();

    private ArrayList<StackPane> panesOnRoundTrack = new ArrayList<>();
    private ArrayList<Canvas> canvasOnRoundTrack = new ArrayList<>();

    //Player's names and window frames containers
    private ArrayList<Label> playersNameLabels = new ArrayList<>();
    private ArrayList<GridPane> playersGrids = new ArrayList<>();

    //Element to draw window frames containers
    private Map<Integer, ArrayList<Canvas>> canvasOnGrids;
    private Map<Integer, ArrayList<StackPane>> panesOnGrids;

    //Element to draw dice pool containers
    private ArrayList<StackPane> panesOnDicePool = new ArrayList<>();
    private ArrayList<Canvas> canvasOnDicePool = new ArrayList<>();

    //JSON containers
    private JSONArray players;
    private JSONObject mainPlayer;

    //Drawers containers
    private RoundTrackDrawer rDrawer;
    private WindowFrameDrawer wDrawer;
    private DiceDrawer dDrawer;
    private CardsSetter cardsSetter;

    @FXML
    private AnchorPane roundTrackAnchorPane;
    @FXML
    private GridPane roundTrackGrid;
    @FXML
    private AnchorPane apPlayer1;
    @FXML
    private Canvas canvasPlayer1;
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
    private ImageView toolCard1;
    @FXML
    private ImageView toolCard2;
    @FXML
    private ImageView toolCard3;
    @FXML
    private ImageView pubObjCard1;
    @FXML
    private ImageView pubObjCard2;
    @FXML
    private ImageView pubObjCard3;

    //Setters
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

    //Starting drawer
    private void drawCanvasWindowFrameStructure(GraphicsContext gc){
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(4);
        gc.strokeLine(GUIParameters.PLAYER_1_CANVAS_X_1, GUIParameters.PLAYER_1_CANVAS_Y_1, 335, GUIParameters.PLAYER_1_CANVAS_Y_1);
        gc.strokeLine(GUIParameters.PLAYER_1_CANVAS_X_1, GUIParameters.PLAYER_1_CANVAS_Y_1 + GUIParameters.PLAYER_1_CANVAS_OFFSET_Y,
                GUIParameters.PLAYER_1_CANVAS_X_1 + GUIParameters.PLAYER_1_CANVAS_OFFSET_X, GUIParameters.PLAYER_1_CANVAS_Y_1 + GUIParameters.PLAYER_1_CANVAS_OFFSET_Y);
        gc.strokePolyline(GUIParameters.BORDER_CANVAS_PLAYER_1_X_POLYLINE,
                GUIParameters.BORDER_CANVAS_PLAYER_1_Y_POLYLINE,
                GUIParameters.NUM_POLYLINE_PLAYER_1_POINTS);
        gc.strokeArc(GUIParameters.PLAYER_1_CANVAS_X_1, GUIParameters.ARC_Y, GUIParameters.ARC_WIDTH,
                GUIParameters.ARC_HEIGHT, GUIParameters.ARC_ANGLE_START, GUIParameters.ARC_ANGLE_EXTEND, ArcType.OPEN);
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
    private void drawMapAndSetUsername(ArrayList<Canvas> canvasArrayList, ArrayList<StackPane> stackPanes, JSONObject player, Label labelPlayer, double scale){
        labelPlayer.setText(player.get(JSONTag.USERNAME).toString());
        wDrawer.frameReset((JSONObject) player.get(JSONTag.WINDOW_FRAME), canvasArrayList, stackPanes, scale);
    }
    private void fillFirstTimeMap(){
        int j = 0;
        wDrawer.setPaneAndCanvasOnFrames(canvasOnGrids.get(getPlayer1ByUsername(players)), panesOnGrids.get(getPlayer1ByUsername(players)), windowFramePlayer1, 1);
        for(int i = 0; i < players.size(); i++) {
            if(i != getPlayer1ByUsername(players)) {
                wDrawer.setPaneAndCanvasOnFrames(canvasOnGrids.get(i), panesOnGrids.get(i), playersGrids.get(j), GUIParameters.REDUCTION_SCALE);
                j++;
            }
        }
    }

    //Cards management
    private void addCardsOnGameBoard(JSONObject json){
        setCards();
        toolCards = cardsSetter.setPublicCards(toolCards.get(0), toolCards.get(1), toolCards.get(2), (JSONArray) json.get(JSONTag.TOOLS), GUIParameters.TOOL_DIRECTORY);
        pubObjCards = cardsSetter.setPublicCards(pubObjCards.get(0), pubObjCards.get(1), pubObjCards.get(2), (JSONArray) json.get(JSONTag.PUBLIC_OBJECTIVES), GUIParameters.PUBOBJ_DIRECTORY);

        for (int i = 0, j = 0; i < 5 && j < toolCards.size(); i += 2, j++) {
            gridCards.add(toolCards.get(j), i, 0);
            gridCards.add(pubObjCards.get(j), i, 2);
        }
    }
    private void setCards() {
        pubObjCards.add(pubObjCard1);
        pubObjCards.add(pubObjCard2);
        pubObjCards.add(pubObjCard3);

        toolCards.add(toolCard1);
        toolCards.add(toolCard2);
        toolCards.add(toolCard3);
    }

    //Main method, called by the update() method from Gui Manager to handle gui refresh. NOT WORKING even if I enter into it
    public void gameBoardUpdate(JSONObject json){
        players = (JSONArray) ((JSONObject) json.get(JSONTag.TURN_MANAGER)).get(JSONTag.PLAYERS);
        mainPlayer = (JSONObject) players.get(getPlayer1ByUsername(players));
        updater(json);
    }

    //Updater support method
    private void updater(JSONObject json){
        int j = 0;
        drawMapAndSetUsername(canvasOnGrids.get(getPlayer1ByUsername(players)), panesOnGrids.get(getPlayer1ByUsername(players)), mainPlayer, labelPlayer1, 1);
        for(int i = 0; i < players.size(); i++) {
            if(i != getPlayer1ByUsername(players)) {
                drawMapAndSetUsername(canvasOnGrids.get(i), panesOnGrids.get(i), (JSONObject) players.get(i), playersNameLabels.get(j), GUIParameters.REDUCTION_SCALE);
                j++;
            }
        }
        //Think about it, because you can't re-draw all dice pool every turn
        dDrawer.dicePoolReset(json, panesOnDicePool, canvasOnDicePool);
    }

    //First game board draw, called by initialize
    private void firstUpdate(JSONObject json){
        players = (JSONArray)((JSONObject)json.get(JSONTag.TURN_MANAGER)).get(JSONTag.PLAYERS);
        mainPlayer = (JSONObject) players.get(getPlayer1ByUsername(players));

        Group group = new Group();
        StackPane pane = new StackPane();

        rDrawer = new RoundTrackDrawer();
        wDrawer = new WindowFrameDrawer();
        cardsSetter = new CardsSetter();
        dDrawer = new DiceDrawer();

        setGrid();
        setPlayersNameLabels();
        setMaps();

        drawCanvasWindowFrameStructure(canvasPlayer1.getGraphicsContext2D());
        rDrawer.roundTrackFiller(roundTrackGrid, panesOnRoundTrack, canvasOnRoundTrack);
        fillFirstTimeMap();
        dDrawer.dicePoolFiller(json, diceGrid, panesOnDicePool, canvasOnDicePool);
        updater(json);
        addCardsOnGameBoard(json);

        pane.getChildren().add(windowFramePlayer1);
        group.getChildren().addAll(canvasPlayer1, pane);
        apPlayer1.getChildren().add(group);
    }

    @FXML
    public void initialize(){
        GuiManager.getInstance().setGameBoard(this);
        JSONObject json = GuiManager.getInstance().getGameBoardMessage();
        firstUpdate(json);
    }
}
