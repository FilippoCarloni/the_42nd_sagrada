package it.polimi.ingsw.view.gui.gameboard.cards.toolcards;

import it.polimi.ingsw.connection.constraints.ConnectionCommands;
import it.polimi.ingsw.view.gui.utility.GuiManager;
import it.polimi.ingsw.view.gui.gameboard.dice.DiceDrawer;
import it.polimi.ingsw.view.gui.utility.GUIParameters;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.print;

/**
 * Controller class for the Flux Remover tool card screen.
 */

public class FluxRemoverController {

    private List<StackPane> panesBetweenChoose;
    private List<Canvas> canvasBetweenChoose;

    @FXML
    private GridPane shadeChoiceGrid;

    private void sendValue(int value) throws ConnectException {
        GuiManager.getInstance().getConnectionController().send(GUIParameters.SELECT + ConnectionCommands.COMMAND_SEPARATOR + value);
        shadeChoiceGrid.getScene().getWindow().hide();
    }
    private void drawAll() {
        String color = GUIParameters.DEFAULT_DICE_COLOR;

        DiceDrawer.diceFiller(shadeChoiceGrid, panesBetweenChoose, canvasBetweenChoose, GUIParameters.NUM_SHADES, false);

        for(int i = 0; i < GUIParameters.NUM_SHADES; i++){
            canvasBetweenChoose.get(i).getStyleClass().clear();
            canvasBetweenChoose.get(i).getStyleClass().add(GUIParameters.CLICKABLE);
            int value = i + 1;
            DiceDrawer.dicePointsDrawer(value, color, canvasBetweenChoose.get(i).getGraphicsContext2D(), panesBetweenChoose.get(i), GUIParameters.NO_SCALE);
            canvasBetweenChoose.get(i).setOnMouseClicked(e -> {
                try {
                    sendValue(value);
                } catch (ConnectException e1) {
                    print(e1.getMessage());
                }
            });
        }
    }

    @FXML
    protected void initialize() {
        panesBetweenChoose = new ArrayList<>();
        canvasBetweenChoose = new ArrayList<>();
        drawAll();
    }

}
