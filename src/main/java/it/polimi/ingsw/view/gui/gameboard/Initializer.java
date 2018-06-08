package it.polimi.ingsw.view.gui.gameboard;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.gameboard.cards.CardsSetter;
import it.polimi.ingsw.view.gui.gameboard.dice.DiceDrawer;
import it.polimi.ingsw.view.gui.gameboard.windowframes.WindowFrameDrawer;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Initializer implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Group group = new Group();
        StackPane pane = new StackPane();
        List<ImageView> toolCards = new MainBoardController().getToolCards();
        List<ImageView> pubObjCards = new MainBoardController().getPubObjCards();
        GridPane windowFramePlayer1 = new MainBoardController().getWindowFramePlayer1();
        AnchorPane apPlayer1 = new MainBoardController().getApPlayer1();
        Canvas canvasPlayer1 = new MainBoardController().getCanvasPlayer1();

        new WindowFrameDrawer().frameFiller(windowFramePlayer1, "{\"difficulty\":5,\"name\":\"Batllo\",\"coordinates\":[{\"die\":{\"color\":\"green\",\"shade\":5,\"id\":36},\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":0},{\"die\":{\"color\":\"red\",\"shade\":6,\"id\":0},\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":1},{\"die\":null,\"shade_constraint\":6,\"color_constraint\":null,\"row_index\":0,\"column_index\":2},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":3},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":0,\"column_index\":4},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":1,\"column_index\":0},{\"die\":{\"color\":\"green\",\"shade\":6,\"id\":36},\"shade_constraint\":5,\"color_constraint\":null,\"row_index\":1,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"blue\",\"row_index\":1,\"column_index\":2},{\"die\":null,\"shade_constraint\":4,\"color_constraint\":null,\"row_index\":1,\"column_index\":3},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":null,\"row_index\":1,\"column_index\":4},{\"die\":null,\"shade_constraint\":3,\"color_constraint\":null,\"row_index\":2,\"column_index\":0},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"green\",\"row_index\":2,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"yellow\",\"row_index\":2,\"column_index\":2},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"purple\",\"row_index\":2,\"column_index\":3},{\"die\":null,\"shade_constraint\":2,\"color_constraint\":null,\"row_index\":2,\"column_index\":4},{\"die\":null,\"shade_constraint\":1,\"color_constraint\":null,\"row_index\":3,\"column_index\":0},{\"die\":null,\"shade_constraint\":4,\"color_constraint\":null,\"row_index\":3,\"column_index\":1},{\"die\":null,\"shade_constraint\":null,\"color_constraint\":\"red\",\"row_index\":3,\"column_index\":2},{\"die\":null,\"shade_constraint\":5,\"color_constraint\":null,\"row_index\":3,\"column_index\":3},{\"die\":null,\"shade_constraint\":3,\"color_constraint\":null,\"row_index\":3,\"column_index\":4}]}");

        try {
            JSONObject jSon = (JSONObject) new JSONParser().parse(new FileReader("src/main/java/res/json_test/gen_2p_01.json"));
            new DiceDrawer().dicePoolFiller(jSon, new MainBoardController().getDiceGrid());
            toolCards = new CardsSetter().setPublicCards(toolCards.get(0), toolCards.get(1), toolCards.get(2), (JSONArray) jSon.get(JSONTag.TOOLS), GUIParameters.TOOL_DIRECTORY);
            pubObjCards = new CardsSetter().setPublicCards(pubObjCards.get(0), pubObjCards.get(1), pubObjCards.get(2), (JSONArray) jSon.get(JSONTag.PUBLIC_OBJECTIVES), GUIParameters.PUBOBJ_DIRECTORY);

        }
        catch (ParseException f) {
            System.err.println("Error in parsing file JSON");
        } catch (FileNotFoundException e) {
            System.err.println("File JSON not found");
        } catch (IOException e) {
            System.err.println("IOException launched from JSON loader");
        }

        /*for(int i = 0, j = 0; i < 5 && j < toolCards.size(); i += 2, j++){
            gridCards.add(toolCards.get(j), i, 0);
            gridCards.add(pubObjCards.get(j), i, 2);
        }*/

        pane.getChildren().add(windowFramePlayer1);
        group.getChildren().addAll(canvasPlayer1, pane);
        apPlayer1.getChildren().add(group);
    }
}
