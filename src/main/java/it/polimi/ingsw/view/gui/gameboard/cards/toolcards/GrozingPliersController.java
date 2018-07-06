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

    /**
     * Method, called by clicking on "increase" button, that allows the player to increase by 1 the value of the drafted die.
     * @param event: the ActionEvent generated when a player clicks on the button.
     */
    public void increase(ActionEvent event){
        sendCommand(GUIParameters.INCREASE, event);
    }
    /**
     * Method, called by clicking on "decrease" button, that allows the player to decrease by 1 the value of the drafted die.
     * @param event: the ActionEvent generated when a player clicks on the button.
     */
    public void decrease(ActionEvent event){
        sendCommand(GUIParameters.DECREASE, event);
    }

    //Method that sends the right command to the connection controller
    private void sendCommand(String command, ActionEvent event){
        try {
            GuiManager.getInstance().getConnectionController().send(command);
        } catch (ConnectException e) {
            print(e.getMessage());
        }
        //This launch a CastException, I can't do it like this
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    protected void initialize() throws ConnectException {
        JSONObject diePicked = (JSONObject) GuiManager.getInstance().getGameBoardMessage().get(JSONTag.PICKED_DIE);
        String color = diePicked.get(JSONTag.COLOR).toString();
        int value = parseInt(diePicked.get(JSONTag.SHADE).toString());

        DiceDrawer.dicePointsDrawer(value + 1, color, increaseCanvas.getGraphicsContext2D(), increaseStackPane, 1 + GUIParameters.REDUCTION_SCALE);
        DiceDrawer.dicePointsDrawer(value - 1, color, decreaseCanvas.getGraphicsContext2D(), decreaseStackPane, 1 + GUIParameters.REDUCTION_SCALE);
    }

}
