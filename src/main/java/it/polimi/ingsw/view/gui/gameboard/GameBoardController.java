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

    private ArrayList<StackPane> paneDicePool;
    private ArrayList<Canvas> canvasDicePool;
    private ArrayList<ImageView> toolCards = new ArrayList<>();
    private ArrayList<ImageView> pubObjCards = new ArrayList<>();

    @FXML
    private AnchorPane apPlayer1 = new AnchorPane();
    @FXML
    private Canvas canvasPlayer1 = new Canvas(GUIParameters.PLAYER_1_CANVAS_WIDTH, GUIParameters.PLAYER_1_CANVAS_HEIGHT);
    @FXML
    private GridPane windowFramePlayer1 = new GridPane();
    @FXML
    private GridPane diceGrid = new GridPane();
    @FXML
    private StackPane paneDicePool1 = new StackPane();
    @FXML
    private StackPane paneDicePool2 = new StackPane();
    @FXML
    private StackPane paneDicePool3 = new StackPane();
    @FXML
    private StackPane paneDicePool4 = new StackPane();
    @FXML
    private StackPane paneDicePool5 = new StackPane();
    @FXML
    private StackPane paneDicePool6 = new StackPane();
    @FXML
    private StackPane paneDicePool7 = new StackPane();
    @FXML
    private StackPane paneDicePool8 = new StackPane();
    @FXML
    private StackPane paneDicePool9 = new StackPane();
    @FXML
    private Canvas canvasDicePool1 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
    @FXML
    private Canvas canvasDicePool2 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
    @FXML
    private Canvas canvasDicePool3 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
    @FXML
    private Canvas canvasDicePool4 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
    @FXML
    private Canvas canvasDicePool5 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
    @FXML
    private Canvas canvasDicePool6 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
    @FXML
    private Canvas canvasDicePool7 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
    @FXML
    private Canvas canvasDicePool8 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
    @FXML
    private Canvas canvasDicePool9 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
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

    public void listDicePoolSetter(){
        paneDicePool = new ArrayList<>();
        canvasDicePool = new ArrayList<>();

        paneDicePool.add(paneDicePool1);
        paneDicePool.add(paneDicePool2);
        paneDicePool.add(paneDicePool3);
        paneDicePool.add(paneDicePool4);
        paneDicePool.add(paneDicePool5);
        paneDicePool.add(paneDicePool6);
        paneDicePool.add(paneDicePool7);
        paneDicePool.add(paneDicePool8);
        paneDicePool.add(paneDicePool9);

        canvasDicePool.add(canvasDicePool1);
        canvasDicePool.add(canvasDicePool2);
        canvasDicePool.add(canvasDicePool3);
        canvasDicePool.add(canvasDicePool4);
        canvasDicePool.add(canvasDicePool5);
        canvasDicePool.add(canvasDicePool6);
        canvasDicePool.add(canvasDicePool7);
        canvasDicePool.add(canvasDicePool8);
        canvasDicePool.add(canvasDicePool9);
    }
    public ArrayList<StackPane> getPaneDicePool(){
        return paneDicePool;
    }
    public ArrayList<Canvas> getCanvasDicePool(){
        return canvasDicePool;
    }

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

    @FXML
    protected void initialize(){
        Group group = new Group();
        StackPane pane = new StackPane();
        ArrayList<Canvas> canvasOnDicePool;
        WindowFrameDrawer wDrawer = new WindowFrameDrawer();
        DiceDrawer diceDrawer = new DiceDrawer();

        setGrid();
        setCards();
        drawCanvasWindowFrameStructure(canvasPlayer1.getGraphicsContext2D());

        wDrawer.frameFiller(windowFramePlayer1, "{\"difficulty\":5,\"name\":\"Batllo\",\"coordinates\":[{\"die\":{\"color\":\"green\",\"shade\":5,\"id\":36},\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":0},{\"die\":{\"color\":\"red\",\"shade\":6,\"id\":0},\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":1},{\"die\":null,\"shade_constraint\":6,\"color_constraint\":null,\"row_index\":0,\"column_index\":2},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":3},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":4},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":1,\"column_index\":0},{\"die\":{\"color\":\"green\",\"shade\":6,\"id\":36},\"shade_constraint\":5,\"color_constraint\":null,\"row_index\":1,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"blue\",\"row_index\":1,\"column_index\":2},{\"die\":null,\"shade_constraint\":4,\"color_constraint\":null,\"row_index\":1,\"column_index\":3},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":1,\"column_index\":4},{\"die\":null,\"shade_constraint\":3,\"color_constraint\":null,\"row_index\":2,\"column_index\":0},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"green\",\"row_index\":2,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"yellow\",\"row_index\":2,\"column_index\":2},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"purple\",\"row_index\":2,\"column_index\":3},{\"die\":null,\"shade_constraint\":2,\"color_constraint\":null,\"row_index\":2,\"column_index\":4},{\"die\":null,\"shade_constraint\":1,\"color_constraint\":null,\"row_index\":3,\"column_index\":0},{\"die\":null,\"shade_constraint\":4,\"color_constraint\":null,\"row_index\":3,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"red\",\"row_index\":3,\"column_index\":2},{\"die\":null,\"shade_constraint\":5,\"color_constraint\":null,\"row_index\":3,\"column_index\":3},{\"die\":null,\"shade_constraint\":3,\"color_constraint\":null,\"row_index\":3,\"column_index\":4}]}");

        try {
            JSONObject jSon = (JSONObject) new JSONParser().parse(new FileReader("src/main/java/res/json_test/gen_2p_01.json"));
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

        canvasOnDicePool = diceDrawer.getCanvasDicePool();
        for(Canvas c : canvasOnDicePool){
            System.out.println("entered");
            c.setOnMouseClicked(e -> System.out.println("b"));
        }

        pane.getChildren().add(windowFramePlayer1);
        group.getChildren().addAll(canvasPlayer1, pane);
        apPlayer1.getChildren().add(group);
    }
}
