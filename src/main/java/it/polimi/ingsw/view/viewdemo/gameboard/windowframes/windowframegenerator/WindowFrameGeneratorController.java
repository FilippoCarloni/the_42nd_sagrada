package it.polimi.ingsw.view.viewdemo.gameboard.windowframes.windowframegenerator;

import it.polimi.ingsw.view.viewdemo.gameboard.MainBoardController;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WindowFrameGeneratorController extends Application implements Initializable {

    @FXML
    private Canvas windowFrameCanvas = new Canvas(360, 530);
    @FXML
    private GridPane gridWindowFrame = new GridPane();
    @FXML
    private AnchorPane apMain = new AnchorPane();

    public void setGridWindowFrame(){
        for(int i = 0; i < 5; i++){
            gridWindowFrame.getColumnConstraints().add(new ColumnConstraints());
        }
        for(int i = 0; i < 4; i++){
            gridWindowFrame.getRowConstraints().add(new RowConstraints());
        }
        gridWindowFrame.setGridLinesVisible(true);
        gridWindowFrame.setTranslateX(55);
        gridWindowFrame.setTranslateY(175);
    }
    public void drawCanvasStructure(GraphicsContext gc){
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(4);
        gc.strokeLine(25, 170, 325, 170);
        gc.strokeLine(25, 460, 325, 460);
        gc.strokePolyline(new double[]{25, 25, 325, 325},
                new double[]{170, 530, 530, 170},
                4);
        gc.strokeArc(25, 5, 300, 350, 0, 180, ArcType.OPEN);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Group group = new Group();
        StackPane pane = new StackPane();
        setGridWindowFrame();
        new WindowFrameFiller().frameFiller(gridWindowFrame, group);

        new WindowFrameGeneratorController().drawCanvasStructure(windowFrameCanvas.getGraphicsContext2D());

        pane.getChildren().add(gridWindowFrame);
        group.getChildren().addAll(windowFrameCanvas, pane);
        apMain.getChildren().add(group);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML_files/WindowFrame.fxml"));
        primaryStage.setTitle("WindowFrame Structure");

        Scene scene = new Scene(parent, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args[]){
        launch(args);
    }
}
