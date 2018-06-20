package it.polimi.ingsw.view.gui.gameboard.roundtrack;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: understand how I will receive information about dice to put into RoundTrack

public class RoundTrackDrawer {

    private Map<Integer, List<JSONObject>> diceOnRoundTrack = new HashMap<>();

    public void roundTrackStartingFiller(GridPane roundTrackPane, List<StackPane> panesOnRoundTrack){
        for(int i = 0; i < GUIParameters.MAX_NUM_TURNS; i++){
            StackPane stackPane = new StackPane();
            roundTrackPane.add(stackPane, i, 0);
            panesOnRoundTrack.add(stackPane);
        }
    }

    public void roundTrackUpdate(int turnNumber, List<StackPane> panesOnRoundTrack, JSONObject roundTrack){

    }

    public void addDice(int turnNumber, List<JSONObject> dieToPut){
        diceOnRoundTrack.put(turnNumber, dieToPut);
    }

    //Dice getters
    public List<JSONObject> getDiceInSpecificTurn(int turnNumber){
        return diceOnRoundTrack.get(turnNumber);
    }
    public Map<Integer, List<JSONObject>> getDiceOnRoundTrack(){
        return diceOnRoundTrack;
    }
}
