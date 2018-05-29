package it.polimi.ingsw.view.viewdemo.gameboard;

import it.polimi.ingsw.view.viewdemo.gameboard.windowframes.WindowFrameDrawer;
import it.polimi.ingsw.view.viewdemo.settings.GUIParameters;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainBoardController extends Application implements Initializable {

    @FXML
    private GridPane gridBase = new GridPane();
    @FXML
    private AnchorPane apPlayer1 = new AnchorPane();
    @FXML
    private Canvas canvasPlayer1 = new Canvas(GUIParameters.PLAYER_1_CANVAS_WIDTH, GUIParameters.PLAYER_1_CANVAS_HEIGHT);
    @FXML
    private GridPane windowFramePlayer1 = new GridPane();
    @FXML
    private Label labelPlayer1 = new Label();

    private void setGridWindowFrame(){
        windowFramePlayer1.setStyle("-fx-background-color: black");
        windowFramePlayer1.setTranslateX(40);
        windowFramePlayer1.setTranslateY(300);
        windowFramePlayer1.setGridLinesVisible(true);
    }
    private void drawCanvasStructure(GraphicsContext gc){
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(4);
        gc.strokeLine(15, 180, 335, 180);
        gc.strokeLine(15, 438, 335, 438);
        gc.strokePolyline(new double[]{15, 15, 335, 335},
                new double[]{180, 498, 498, 180},
                4);
        gc.strokeArc(15, 20, 320, 320, 0, 180, ArcType.OPEN);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Group group = new Group();
        StackPane pane = new StackPane();

        setGridWindowFrame();
        drawCanvasStructure(canvasPlayer1.getGraphicsContext2D());
        new WindowFrameDrawer().frameFiller(windowFramePlayer1, "{\"difficulty\":5,\"name\":\"Batllo\",\"coordinates\":[{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":0},{\"die\":{\"color\":\"red\",\"shade\":6,\"id\":0},\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":1},{\"die\":null,\"shade_constraint\":6,\"color_constraint\":null,\"row_index\":0,\"column_index\":2},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":3},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":4},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":1,\"column_index\":0},{\"die\":{\"color\":\"green\",\"shade\":6,\"id\":36},\"shade_constraint\":5,\"color_constraint\":null,\"row_index\":1,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"blue\",\"row_index\":1,\"column_index\":2},{\"die\":null,\"shade_constraint\":4,\"color_constraint\":null,\"row_index\":1,\"column_index\":3},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":1,\"column_index\":4},{\"die\":null,\"shade_constraint\":3,\"color_constraint\":null,\"row_index\":2,\"column_index\":0},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"green\",\"row_index\":2,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"yellow\",\"row_index\":2,\"column_index\":2},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"purple\",\"row_index\":2,\"column_index\":3},{\"die\":null,\"shade_constraint\":2,\"color_constraint\":null,\"row_index\":2,\"column_index\":4},{\"die\":null,\"shade_constraint\":1,\"color_constraint\":null,\"row_index\":3,\"column_index\":0},{\"die\":null,\"shade_constraint\":4,\"color_constraint\":null,\"row_index\":3,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"red\",\"row_index\":3,\"column_index\":2},{\"die\":null,\"shade_constraint\":5,\"color_constraint\":null,\"row_index\":3,\"column_index\":3},{\"die\":null,\"shade_constraint\":3,\"color_constraint\":null,\"row_index\":3,\"column_index\":4}]}");

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
