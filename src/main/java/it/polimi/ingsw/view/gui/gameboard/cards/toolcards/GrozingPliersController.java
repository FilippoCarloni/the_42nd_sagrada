package it.polimi.ingsw.view.gui.gameboard.cards.toolcards;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.gameboard.dice.DiceDrawer;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONObject;

import static java.lang.Integer.parseInt;

public class GrozingPliersController {

    @FXML
    private StackPane increaseStackPane;
    @FXML
    private Canvas increaseCanvas;
    @FXML
    private StackPane decreaseStackPane;
    @FXML
    private Canvas decreaseCanvas;

    private void sendCommand(String command, ActionEvent event){
        GuiManager.getInstance().getConnectionController().send(command);
        //This launch a CastException, I can't do it like this
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    protected void initialize(){
        JSONObject diePicked = (JSONObject) GuiManager.getInstance().getGameBoardMessage().get(JSONTag.PICKED_DIE);
        String color = diePicked.get(JSONTag.COLOR).toString();
        int value = parseInt(diePicked.get(JSONTag.SHADE).toString());

        DiceDrawer.dicePointsDrawer(value + 1, color, increaseCanvas.getGraphicsContext2D(), increaseStackPane, 1 + GUIParameters.REDUCTION_SCALE);
        DiceDrawer.dicePointsDrawer(value - 1, color, decreaseCanvas.getGraphicsContext2D(), decreaseStackPane, 1 + GUIParameters.REDUCTION_SCALE);

        increaseStackPane.setOnMouseClicked(e -> sendCommand("increase", new ActionEvent()));
        decreaseStackPane.setOnMouseClicked(e -> sendCommand("decrease", new ActionEvent()));
    }

}
