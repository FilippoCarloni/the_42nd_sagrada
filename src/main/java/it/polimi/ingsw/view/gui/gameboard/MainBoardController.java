package it.polimi.ingsw.view.gui.gameboard;

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
import java.util.ResourceBundle;

public class MainBoardController extends Application implements Initializable {

    @FXML
    private AnchorPane apPlayer1 = new AnchorPane();
    @FXML
    private Canvas canvasPlayer1 = new Canvas(GUIParameters.PLAYER_1_CANVAS_WIDTH, GUIParameters.PLAYER_1_CANVAS_HEIGHT);
    @FXML
    private GridPane windowFramePlayer1 = new GridPane();
    @FXML
    private GridPane diceGrid = new GridPane();

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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Group group = new Group();
        StackPane pane = new StackPane();

        setGrid();
        drawCanvasWindowFrameStructure(canvasPlayer1.getGraphicsContext2D());
        new WindowFrameDrawer().frameFiller(windowFramePlayer1, "{\"difficulty\":5,\"name\":\"Batllo\",\"coordinates\":[{\"die\":{\"color\":\"green\",\"shade\":5,\"id\":36},\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":0},{\"die\":{\"color\":\"red\",\"shade\":6,\"id\":0},\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":1},{\"die\":null,\"shade_constraint\":6,\"color_constraint\":null,\"row_index\":0,\"column_index\":2},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":3},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":4},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":1,\"column_index\":0},{\"die\":{\"color\":\"green\",\"shade\":6,\"id\":36},\"shade_constraint\":5,\"color_constraint\":null,\"row_index\":1,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"blue\",\"row_index\":1,\"column_index\":2},{\"die\":null,\"shade_constraint\":4,\"color_constraint\":null,\"row_index\":1,\"column_index\":3},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":1,\"column_index\":4},{\"die\":null,\"shade_constraint\":3,\"color_constraint\":null,\"row_index\":2,\"column_index\":0},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"green\",\"row_index\":2,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"yellow\",\"row_index\":2,\"column_index\":2},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"purple\",\"row_index\":2,\"column_index\":3},{\"die\":null,\"shade_constraint\":2,\"color_constraint\":null,\"row_index\":2,\"column_index\":4},{\"die\":null,\"shade_constraint\":1,\"color_constraint\":null,\"row_index\":3,\"column_index\":0},{\"die\":null,\"shade_constraint\":4,\"color_constraint\":null,\"row_index\":3,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"red\",\"row_index\":3,\"column_index\":2},{\"die\":null,\"shade_constraint\":5,\"color_constraint\":null,\"row_index\":3,\"column_index\":3},{\"die\":null,\"shade_constraint\":3,\"color_constraint\":null,\"row_index\":3,\"column_index\":4}]}");

        try {
            JSONObject jSon = (JSONObject) new JSONParser().parse(new FileReader("src/main/java/res/json_test/gen_2p_01.json"));
            new DiceDrawer().dicePoolFiller(jSon, diceGrid);
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
        primaryStage.setTitle(GUIParameters.SCENE_TITLE);

        Scene scene = new Scene(parent, GUIParameters.SCREEN_WIDTH, GUIParameters.SCREEN_HEIGHT);
        //scene.getStylesheets().add(Main.class.getResource("loginCSS.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}
