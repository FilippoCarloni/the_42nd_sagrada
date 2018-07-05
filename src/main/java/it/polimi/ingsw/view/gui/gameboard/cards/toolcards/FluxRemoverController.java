package it.polimi.ingsw.view.gui.gameboard.cards.toolcards;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.gameboard.dice.DiceDrawer;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONObject;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

//TODO: fix the "color bug"

public class FluxRemoverController {

    private List<StackPane> panesBetweenChoose;
    private List<Canvas> canvasBetweenChoose;

    @FXML
    private GridPane shadeChoiceGrid;

    /**
     * Methods used by the radio buttons on the flux remover screen, to send the new shade of the die.
     * @param event: the ActionEvent generated when a player clicks on the button.
     * @throws ConnectException if the connection controller is not reachable.
     */
    public void send1(ActionEvent event) throws ConnectException {
        sendValue(1, event);
    }
    public void send2(ActionEvent event) throws ConnectException {
        sendValue(2, event);
    }
    public void send3(ActionEvent event) throws ConnectException {
        sendValue(3, event);
    }
    public void send4(ActionEvent event) throws ConnectException {
        sendValue(4, event);
    }
    public void send5(ActionEvent event) throws ConnectException {
        sendValue(5, event);
    }
    public void send6(ActionEvent event) throws ConnectException {
        sendValue(6, event);
    }

    private void sendValue(int value, ActionEvent event) throws ConnectException {
        GuiManager.getInstance().getConnectionController().send(GUIParameters.SELECT + value);
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    private void drawAll() throws ConnectException {
        JSONObject diePicked = (JSONObject) GuiManager.getInstance().getGameBoardMessage().get(JSONTag.PICKED_DIE);
        String color = diePicked.get(JSONTag.COLOR).toString();

        DiceDrawer.diceFiller(shadeChoiceGrid, panesBetweenChoose, canvasBetweenChoose, GUIParameters.NUM_SHADES, false);

        for(int i = 0; i < GUIParameters.NUM_SHADES; i++){
            canvasBetweenChoose.get(i).getStyleClass().clear();
            DiceDrawer.dicePointsDrawer(i + 1, color, canvasBetweenChoose.get(i).getGraphicsContext2D(), panesBetweenChoose.get(i), 1);
        }
    }

    @FXML
    protected void initialize() throws ConnectException {
        panesBetweenChoose = new ArrayList<>();
        canvasBetweenChoose = new ArrayList<>();
        drawAll();
    }

}
