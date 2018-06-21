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

import static java.lang.Integer.parseInt;

public class RoundTrackVisualizer {

    @FXML
    private GridPane allDiceOnRoundTrackGridPane;

    private void allDiceDrawer(JSONObject roundTrack){
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
                int finalIndex = index;
                stackPane.setOnMouseClicked(e -> GuiManager.getInstance().getConnectionController().send("select " + finalIndex));
                index++;
            }
        }
    }

    @FXML
    protected void initialize(){
        JSONObject roundTrack = (JSONObject) GuiManager.getInstance().getGameBoardMessage().get(JSONTag.ROUND_TRACK);
        allDiceDrawer(roundTrack);
    }

}
