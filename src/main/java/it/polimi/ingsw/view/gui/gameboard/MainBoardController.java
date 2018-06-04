package it.polimi.ingsw.view.gui.gameboard;

import it.polimi.ingsw.view.gui.gameboard.cards.CardsSetter;
import it.polimi.ingsw.view.gui.gameboard.dice.DiceDrawer;
import it.polimi.ingsw.view.gui.gameboard.windowframes.WindowFrameDrawer;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainBoardController extends Application implements Initializable {

    private ArrayList<Canvas> canvasOnDicePool = new ArrayList<>();
    private ArrayList<StackPane> diceStackPane = new ArrayList<>();
    private ArrayList<Button> diceButton = new ArrayList<>();

    @FXML
    private AnchorPane apPlayer1 = new AnchorPane();
    @FXML
    private Canvas canvasPlayer1 = new Canvas(GUIParameters.PLAYER_1_CANVAS_WIDTH, GUIParameters.PLAYER_1_CANVAS_HEIGHT);
    @FXML
    private GridPane windowFramePlayer1 = new GridPane();
    @FXML
    private GridPane diceGrid = new GridPane();
    @FXML
    private StackPane dicePane0 = new StackPane();
    @FXML
    private StackPane dicePane1 = new StackPane();
    @FXML
    private StackPane dicePane2 = new StackPane();
    @FXML
    private StackPane dicePane3 = new StackPane();
    @FXML
    private StackPane dicePane4 = new StackPane();
    @FXML
    private StackPane dicePane5 = new StackPane();
    @FXML
    private StackPane dicePane6 = new StackPane();
    @FXML
    private StackPane dicePane7 = new StackPane();
    @FXML
    private StackPane dicePane8 = new StackPane();
    @FXML
    private Canvas diceCanvas0 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
    @FXML
    private Canvas diceCanvas1 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
    @FXML
    private Canvas diceCanvas2 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
    @FXML
    private Canvas diceCanvas3 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
    @FXML
    private Canvas diceCanvas4 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
    @FXML
    private Canvas diceCanvas5 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
    @FXML
    private Canvas diceCanvas6 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
    @FXML
    private Canvas diceCanvas7 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
    @FXML
    private Canvas diceCanvas8 = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION);
    @FXML
    private Button diceButton0 = new Button();
    @FXML
    private Button diceButton1 = new Button();
    @FXML
    private Button diceButton2 = new Button();
    @FXML
    private Button diceButton3 = new Button();
    @FXML
    private Button diceButton4 = new Button();
    @FXML
    private Button diceButton5 = new Button();
    @FXML
    private Button diceButton6 = new Button();
    @FXML
    private Button diceButton7 = new Button();
    @FXML
    private Button diceButton8 = new Button();
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


    private void setGrid(){
        windowFramePlayer1.setTranslateX(GUIParameters.PLAYER_1_GRID_X);
        windowFramePlayer1.setTranslateY(GUIParameters.PLAYER_1_GRID_Y);
        diceGrid.setGridLinesVisible(false);
    }
    private void fillerDicePool(){
        diceStackPane.add(dicePane0);
        diceStackPane.add(dicePane1);
        diceStackPane.add(dicePane2);
        diceStackPane.add(dicePane3);
        diceStackPane.add(dicePane4);
        diceStackPane.add(dicePane5);
        diceStackPane.add(dicePane6);
        diceStackPane.add(dicePane7);
        diceStackPane.add(dicePane8);

        canvasOnDicePool.add(diceCanvas0);
        canvasOnDicePool.add(diceCanvas1);
        canvasOnDicePool.add(diceCanvas2);
        canvasOnDicePool.add(diceCanvas3);
        canvasOnDicePool.add(diceCanvas4);
        canvasOnDicePool.add(diceCanvas5);
        canvasOnDicePool.add(diceCanvas6);
        canvasOnDicePool.add(diceCanvas7);
        canvasOnDicePool.add(diceCanvas8);

        for(Canvas c : canvasOnDicePool){
            c.setMouseTransparent(true);
        }

        diceButton.add(diceButton0);
        diceButton.add(diceButton1);
        diceButton.add(diceButton2);
        diceButton.add(diceButton3);
        diceButton.add(diceButton4);
        diceButton.add(diceButton5);
        diceButton.add(diceButton6);
        diceButton.add(diceButton7);
        diceButton.add(diceButton8);


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

    public void pressedButton(){
        System.out.println("pick 1");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Group group = new Group();
        StackPane pane = new StackPane();

        setGrid();
        fillerDicePool();
        drawCanvasWindowFrameStructure(canvasPlayer1.getGraphicsContext2D());
        new WindowFrameDrawer().frameFiller(windowFramePlayer1, "{\"difficulty\":5,\"name\":\"Batllo\",\"coordinates\":[{\"die\":{\"color\":\"green\",\"shade\":5,\"id\":36},\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":0},{\"die\":{\"color\":\"red\",\"shade\":6,\"id\":0},\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":1},{\"die\":null,\"shade_constraint\":6,\"color_constraint\":null,\"row_index\":0,\"column_index\":2},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":3},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":4},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":1,\"column_index\":0},{\"die\":{\"color\":\"green\",\"shade\":6,\"id\":36},\"shade_constraint\":5,\"color_constraint\":null,\"row_index\":1,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"blue\",\"row_index\":1,\"column_index\":2},{\"die\":null,\"shade_constraint\":4,\"color_constraint\":null,\"row_index\":1,\"column_index\":3},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":1,\"column_index\":4},{\"die\":null,\"shade_constraint\":3,\"color_constraint\":null,\"row_index\":2,\"column_index\":0},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"green\",\"row_index\":2,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"yellow\",\"row_index\":2,\"column_index\":2},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"purple\",\"row_index\":2,\"column_index\":3},{\"die\":null,\"shade_constraint\":2,\"color_constraint\":null,\"row_index\":2,\"column_index\":4},{\"die\":null,\"shade_constraint\":1,\"color_constraint\":null,\"row_index\":3,\"column_index\":0},{\"die\":null,\"shade_constraint\":4,\"color_constraint\":null,\"row_index\":3,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"red\",\"row_index\":3,\"column_index\":2},{\"die\":null,\"shade_constraint\":5,\"color_constraint\":null,\"row_index\":3,\"column_index\":3},{\"die\":null,\"shade_constraint\":3,\"color_constraint\":null,\"row_index\":3,\"column_index\":4}]}");

        try {
            JSONObject jSon = (JSONObject) new JSONParser().parse(new FileReader("src/main/java/res/json_test/gen_2p_01.json"));
            new DiceDrawer().dicePoolFiller(jSon, diceStackPane, canvasOnDicePool, diceButton);
            new CardsSetter().setPublicCards(toolCard1, toolCard2, toolCard3, jSon, true);
            new CardsSetter().setPublicCards(pubObjCard1, pubObjCard2, pubObjCard3, jSon, false);
        }
        catch (ParseException f) {
            System.err.println("Error in parsing file JSON");
        } catch (FileNotFoundException e) {
            System.err.println("File JSON not found");
        } catch (IOException e) {
            System.err.println("IOException launched from JSON loader");
        }

        pane.getChildren().add(windowFramePlayer1);
        group.getChildren().addAll(canvasPlayer1, pane);
        apPlayer1.getChildren().add(group);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML_files/MainBoard.fxml"));
        primaryStage.setTitle(GUIParameters.MAIN_SCENE_TITLE);

        Scene scene = new Scene(parent, GUIParameters.SCREEN_WIDTH, GUIParameters.SCREEN_HEIGHT);
        //scene.getStylesheets().add(GuiLauncher.class.getResource("loginCSS.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}
