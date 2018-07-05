package it.polimi.ingsw.view.gui.gameboard.roundtrack;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.gameboard.dice.DiceDrawer;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.ConnectException;

import static java.lang.Integer.parseInt;
import static jdk.nashorn.internal.objects.Global.print;

/**
 * Controller class for RoundTrackVisualizer screen; it shows to the player all dice on round track.
 */

//TODO: find a way to redraw all dice on round track when one is changed after a tool card activation

public class RoundTrackVisualizer {

    @FXML
    private GridPane allDiceOnRoundTrackGridPane;

    /**
     * Method that draws all dice on the round track, when the round track screen is opened; it is also used by the
     * update() method into GameBoardController to re-draw the dice on round track if there has been some changes.
     * @param roundTrack: a JSONObject containing all information about round track:
     *                  <ol>
     *                      <li>How many dice are on every slot</li>
     *                      <li>Which dice are on round track</li>
     *                  </ol>
     */
    public void allDiceDrawer(JSONObject roundTrack){
        JSONArray dice = (JSONArray) roundTrack.get(JSONTag.ALL_DICE);
        int turnNumber = parseInt(roundTrack.get(JSONTag.CURRENT_ROUND_NUMBER).toString());
        int index = 0;

        int[] diceOnSlot = new int[GUIParameters.MAX_NUM_TURNS];
        JSONArray numOfDiceOnSlot = (JSONArray) roundTrack.get(JSONTag.NUMBER_OF_DICE_ON_SLOT);
        for (int i = 0; i < numOfDiceOnSlot.size(); i++)
            diceOnSlot[i] = parseInt(numOfDiceOnSlot.get(i).toString());

        for(int column = 0; column < turnNumber; column++){
            for(int row = 0; row < diceOnSlot[column]; row++){
                Canvas canvas = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * GUIParameters.REDUCTION_FOR_ROUND_TRACK, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * GUIParameters.REDUCTION_FOR_ROUND_TRACK);
                StackPane stackPane = new StackPane();
                allDiceOnRoundTrackGridPane.add(stackPane, column, row);
                stackPane.getChildren().add(canvas);
                int value = parseInt(((JSONObject)dice.get(index)).get(JSONTag.SHADE).toString());
                String color = ((JSONObject)dice.get(index)).get(JSONTag.COLOR).toString();
                DiceDrawer.dicePointsDrawer(value, color,  canvas.getGraphicsContext2D(), stackPane, 0.9);
                canvas.getStyleClass().clear();
                canvas.getStyleClass().add(GUIParameters.CLICKABLE_CANVAS);
                int finalIndex = index + 1;
                stackPane.setOnMouseClicked(e -> {
                    try {
                        GuiManager.getInstance().getConnectionController().send(GUIParameters.SELECT + finalIndex);
                    } catch (ConnectException e1) {
                        print(e1.getMessage());
                    }
                });
                index++;
            }
        }
    }

    @FXML
    protected void initialize() throws ConnectException {
        GuiManager.getInstance().getGameBoard().setrVisualizer(this);
        JSONObject roundTrack = (JSONObject) GuiManager.getInstance().getGameBoardMessage().get(JSONTag.ROUND_TRACK);
        allDiceDrawer(roundTrack);
    }

}
