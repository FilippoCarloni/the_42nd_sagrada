package it.polimi.ingsw.view.gui.gameboard.cards.toolcards;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.utility.GuiManager;
import it.polimi.ingsw.view.gui.gameboard.dice.DiceDrawer;
import it.polimi.ingsw.view.gui.utility.GUIParameters;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONObject;

import java.net.ConnectException;

import static java.lang.Integer.parseInt;
import static jdk.nashorn.internal.objects.Global.print;

/**
 * Controller class for the Grozing Pliers tool card screen.
 */

public class GrozingPliersController {

    @FXML
    private StackPane increaseStackPane;
    @FXML
    private Canvas increaseCanvas;
    @FXML
    private StackPane decreaseStackPane;
    @FXML
    private Canvas decreaseCanvas;

    //Method that sends the right command to the connection controller
    private void sendCommand(String command){
        try {
            GuiManager.getInstance().getConnectionController().send(command);
        } catch (ConnectException e) {
            print(e.getMessage());
        }
        //This launch a CastException, I can't do it like this
        increaseStackPane.getScene().getWindow().hide();
    }

    //Method that draws the dice with the incremented and decremented value
    private void drawDice() throws ConnectException {
        JSONObject diePicked = (JSONObject) GuiManager.getInstance().getGameBoardMessage().get(JSONTag.PICKED_DIE);
        String color = GUIParameters.DEFAULT_DICE_COLOR;
        int value = parseInt(diePicked.get(JSONTag.SHADE).toString());

        DiceDrawer.dicePointsDrawer(value + 1, color, increaseCanvas.getGraphicsContext2D(), increaseStackPane, 1 + GUIParameters.REDUCTION_FOR_OTHER_PLAYERS);
        DiceDrawer.dicePointsDrawer(value - 1, color, decreaseCanvas.getGraphicsContext2D(), decreaseStackPane, 1 + GUIParameters.REDUCTION_FOR_OTHER_PLAYERS);

        setClickableCanvas();
    }
    private void setClickableCanvas(){
        increaseCanvas.getStyleClass().clear();
        increaseCanvas.getStyleClass().add(GUIParameters.CLICKABLE_STYLE);
        increaseCanvas.setOnMouseClicked(e -> sendCommand(GUIParameters.INCREASE));

        decreaseCanvas.getStyleClass().clear();
        decreaseCanvas.getStyleClass().add(GUIParameters.CLICKABLE_STYLE);
        decreaseCanvas.setOnMouseClicked(e -> sendCommand(GUIParameters.DECREASE));
    }

    @FXML
    protected void initialize() throws ConnectException {
        drawDice();
    }

}
