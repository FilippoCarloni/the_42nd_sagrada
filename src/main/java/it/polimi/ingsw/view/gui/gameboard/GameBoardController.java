package it.polimi.ingsw.view.gui.gameboard;

import it.polimi.ingsw.model.utility.JSONTag;
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
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameBoardController {

    private ArrayList<ImageView> toolCards = new ArrayList<>();
    private ArrayList<ImageView> pubObjCards = new ArrayList<>();

    //TODO: this will be used to choose the map to draw for player 1
    private int idMapChosen;

    @FXML
    private AnchorPane apPlayer1 = new AnchorPane();
    @FXML
    private Canvas canvasPlayer1 = new Canvas(GUIParameters.PLAYER_1_CANVAS_WIDTH, GUIParameters.PLAYER_1_CANVAS_HEIGHT);
    @FXML
    private GridPane windowFramePlayer1 = new GridPane();
    @FXML
    private GridPane diceGrid = new GridPane();
    @FXML
    private GridPane gridCards = new GridPane();
    @FXML
    private ImageView toolCard1 = new ImageView();
    @FXML
    private ImageView toolCard2 = new ImageView();
    @FXML
    private ImageView toolCard3 = new ImageView();
    @FXML
    private ImageView pubObjCard1 = new ImageView();
    @FXML
    private ImageView pubObjCard2 = new ImageView();
    @FXML
    private ImageView pubObjCard3 = new ImageView();

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
    public void setIdMapChosen(int idMapChosen){
        this.idMapChosen = idMapChosen;
    }

    @FXML
    public void initialize(){
        Group group = new Group();
        StackPane pane = new StackPane();
        WindowFrameDrawer wDrawer = new WindowFrameDrawer();
        DiceDrawer diceDrawer = new DiceDrawer();

        setGrid();
        setCards();
        drawCanvasWindowFrameStructure(canvasPlayer1.getGraphicsContext2D());


        try {
            JSONObject jSon = (JSONObject) new JSONParser().parse(new FileReader("src/main/java/res/json_test/gen_2p_01.json"));
            JSONArray players = (JSONArray) jSon.get(JSONTag.PLAYERS);
            JSONObject player0 = (JSONObject) players.get(0);
            wDrawer.frameFiller(windowFramePlayer1, (JSONObject) player0.get(JSONTag.WINDOW_FRAME));
            diceDrawer.dicePoolFiller(jSon, diceGrid);
            toolCards = new CardsSetter().setPublicCards(toolCards.get(0), toolCards.get(1), toolCards.get(2), (JSONArray) jSon.get(JSONTag.TOOLS), GUIParameters.TOOL_DIRECTORY);
            pubObjCards = new CardsSetter().setPublicCards(pubObjCards.get(0), pubObjCards.get(1), pubObjCards.get(2), (JSONArray) jSon.get(JSONTag.PUBLIC_OBJECTIVES), GUIParameters.PUBOBJ_DIRECTORY);
        } catch (ParseException f) {
            System.err.println("Error in parsing file JSON");
        } catch (FileNotFoundException e) {
            System.err.println("File JSON not found");
        } catch (IOException e) {
            System.err.println("IOException launched from JSON loader");
        }

        for (int i = 0, j = 0; i < 5 && j < toolCards.size(); i += 2, j++) {
            gridCards.add(toolCards.get(j), i, 0);
            gridCards.add(pubObjCards.get(j), i, 2);
        }

        pane.getChildren().add(windowFramePlayer1);
        group.getChildren().addAll(canvasPlayer1, pane);
        apPlayer1.getChildren().add(group);
    }
}
