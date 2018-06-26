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
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.print;

//TODO: fix the "color bug" and the closing problem

public class FluxRemoverController {

    private List<StackPane> panesBetweenChoose = new ArrayList<>();
    private List<Canvas> canvasBetweenChoose = new ArrayList<>();

    @FXML
    private GridPane shadeChoiceGrid;

    private void sendValue(int value, ActionEvent event) throws RemoteException, ConnectException {
        GuiManager.getInstance().getConnectionController().send(GUIParameters.SELECT + value);
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    protected void initialize() throws RemoteException, ConnectException {
        JSONObject diePicked = (JSONObject) GuiManager.getInstance().getGameBoardMessage().get(JSONTag.PICKED_DIE);
        String color = diePicked.get(JSONTag.COLOR).toString();

        DiceDrawer.diceFiller(shadeChoiceGrid, panesBetweenChoose, canvasBetweenChoose, GUIParameters.NUM_SHADES, false);

        for(int i = 0; i < GUIParameters.NUM_SHADES; i++){
            DiceDrawer.dicePointsDrawer(i + 1, color, canvasBetweenChoose.get(i).getGraphicsContext2D(), panesBetweenChoose.get(i), 1);
            int finalValue = i + 1;
            panesBetweenChoose.get(i).setOnMouseClicked(e -> {
                try {
                    sendValue(finalValue, new ActionEvent());
                } catch (RemoteException | ConnectException e1) {
                    print(e1.getMessage());
                }
            });
        }
    }

}
