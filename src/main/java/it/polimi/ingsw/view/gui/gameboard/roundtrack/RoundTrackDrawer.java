package it.polimi.ingsw.view.gui.gameboard.roundtrack;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.gameboard.dice.DiceDrawer;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

import static java.lang.Integer.parseInt;
import static jdk.nashorn.internal.objects.Global.print;

public class RoundTrackDrawer {

    public void roundTrackStartingFiller(GridPane roundTrackPane, List<StackPane> panesOnRoundTrack, List<Canvas> canvasOnRoundTrack){
        for(int i = 0; i < GUIParameters.MAX_NUM_TURNS; i++){
            StackPane stackPane = new StackPane();
            roundTrackPane.add(stackPane, i, 0);
            Canvas canvas = new Canvas(GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * GUIParameters.REDUCTION_FOR_ROUND_TRACK, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * GUIParameters.REDUCTION_FOR_ROUND_TRACK);
            stackPane.getChildren().add(canvas);
            panesOnRoundTrack.add(stackPane);
            canvasOnRoundTrack.add(canvas);
        }
    }

    public void roundTrackUpdate(JSONObject roundTrack, List<StackPane> panesOnRoundTrack, List<Canvas> canvasOnRoundTrack){
        JSONArray dice = (JSONArray) roundTrack.get(JSONTag.ALL_DICE);
        int turnNumber = parseInt(roundTrack.get(JSONTag.CURRENT_ROUND_NUMBER).toString());

        int[] diceOnSlot = new int[GUIParameters.MAX_NUM_TURNS];
        JSONArray numOfDiceOnSlot = (JSONArray) roundTrack.get(JSONTag.NUMBER_OF_DICE_ON_SLOT);
        for (int i = 0; i < numOfDiceOnSlot.size(); i++)
            diceOnSlot[i] = parseInt(numOfDiceOnSlot.get(i).toString());

        //Reset of round track draw
        for(int i = 0; i < panesOnRoundTrack.size(); i++){
            canvasOnRoundTrack.get(i).getGraphicsContext2D().clearRect(0, 0, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * 0.9, GUIParameters.SQUARE_PLAYER_1_GRID_DIMENSION * 0.9);
            panesOnRoundTrack.get(i).setStyle(GUIParameters.BACKGROUND_COLOR_STRING + GUIParameters.DEFAULT_DICE_COLOR);
        }

        int index = -1;
        for(int i = 0; i < turnNumber - 1; i++){
            //Index will represent the position into the JSONArray dice
            index += diceOnSlot[i];
            int value = parseInt(((JSONObject)dice.get(index)).get(JSONTag.SHADE).toString());
            String color = ((JSONObject)dice.get(index)).get(JSONTag.COLOR).toString();
            //With this call I will draw the last die of the specified turn on the round track
            DiceDrawer.dicePointsDrawer(value, color,  canvasOnRoundTrack.get(i).getGraphicsContext2D(), panesOnRoundTrack.get(i), 0.9);
        }

    }

    public void seeAllDice(){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.DEFAULT_FXML_DIRECTORY + GUIParameters.ROUNT_TRACK_DICE_FXML_PATH));
            Stage stage = new Stage();
            stage.setTitle(GUIParameters.ROUND_TRACK_TITLE + " - " + GuiManager.getInstance().getUsernamePlayer1());
            stage.setScene(new Scene(parent, 561, 500));
            stage.show();
        } catch (IOException e){
            print(e.getMessage());
        }
    }
}
