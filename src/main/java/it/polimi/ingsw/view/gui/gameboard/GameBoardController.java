package it.polimi.ingsw.view.gui.gameboard;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.gameboard.cards.CardsSetter;
import it.polimi.ingsw.view.gui.gameboard.dice.DiceDrawer;
import it.polimi.ingsw.view.gui.gameboard.windowframes.WindowFrameDrawer;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

//TODO: draw all maps, scaling canvases dimensions
//TODO: set all players' username under the corresponding window frame
//TODO: add undo, redo and quit buttons
//TODO: add round track
//TODO: MAKE DICE POOL'S DICE RESPONSIVE

public class GameBoardController {

    private ArrayList<ImageView> toolCards = new ArrayList<>();
    private ArrayList<ImageView> pubObjCards = new ArrayList<>();

    @FXML
    private AnchorPane apPlayer1;
    @FXML
    private Canvas canvasPlayer1;
    @FXML
    private GridPane windowFramePlayer1;
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

    private void setCards() {
        pubObjCards.add(pubObjCard1);
        pubObjCards.add(pubObjCard2);
        pubObjCards.add(pubObjCard3);

        toolCards.add(toolCard1);
        toolCards.add(toolCard2);
        toolCards.add(toolCard3);
    }
    private void setGrid(){
        windowFramePlayer1.setTranslateX(GUIParameters.PLAYER_1_GRID_X);
        windowFramePlayer1.setTranslateY(GUIParameters.PLAYER_1_GRID_Y);
        diceGrid.setGridLinesVisible(false);
    }
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

    private int getPlayer1ByUsername(JSONArray players){
        for(int i = 0; i < players.size(); i++){
            if((((JSONObject)players.get(i)).get(JSONTag.USERNAME)).toString().equals(GuiManager.getInstance().getUsernamePlayer1())){
                return i;
            }
        }
        return players.size();
    }

    @FXML
    public void initialize(){
        GuiManager.getInstance().setGameBoard(this);

        Group group = new Group();
        StackPane pane = new StackPane();

        setGrid();
        setCards();
        drawCanvasWindowFrameStructure(canvasPlayer1.getGraphicsContext2D());

        JSONObject json = GuiManager.getInstance().getGameBoardMessage();

        JSONArray players = (JSONArray)((JSONObject)json.get(JSONTag.TURN_MANAGER)).get(JSONTag.PLAYERS);
        JSONObject player1 = (JSONObject) players.get(getPlayer1ByUsername(players));

        new WindowFrameDrawer().frameFiller(windowFramePlayer1, (JSONObject) player1.get(JSONTag.WINDOW_FRAME));
        new DiceDrawer().dicePoolFiller(json, diceGrid);
        toolCards = new CardsSetter().setPublicCards(toolCards.get(0), toolCards.get(1), toolCards.get(2), (JSONArray) json.get(JSONTag.TOOLS), GUIParameters.TOOL_DIRECTORY);
        pubObjCards = new CardsSetter().setPublicCards(pubObjCards.get(0), pubObjCards.get(1), pubObjCards.get(2), (JSONArray) json.get(JSONTag.PUBLIC_OBJECTIVES), GUIParameters.PUBOBJ_DIRECTORY);

        for (int i = 0, j = 0; i < 5 && j < toolCards.size(); i += 2, j++) {
            gridCards.add(toolCards.get(j), i, 0);
            gridCards.add(pubObjCards.get(j), i, 2);
        }

        pane.getChildren().add(windowFramePlayer1);
        group.getChildren().addAll(canvasPlayer1, pane);
        apPlayer1.getChildren().add(group);
    }
}
