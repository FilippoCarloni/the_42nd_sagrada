package it.polimi.ingsw.view.gui.gameboard.roundtrack;

import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: complete implementation of initialSettings method
//TODO: understand how I will receive information about dice to put into RoundTrack
//TODO: when I click on a turn square I will display a window with all dice of this turn on round track

public class RoundTrackDrawer {

    private Map<Integer, List<JSONObject>> diceOnRoundTrack = new HashMap<>();

    public void roundTrackFiller(GridPane roundTrackPane, List<StackPane> panesOnRoundTrack, List<Canvas> canvasOnRoundTrack){
        for(int i = 0; i < GUIParameters.MAX_NUM_TURNS; i++){
            StackPane stackPane = new StackPane();
            roundTrackPane.add(stackPane, i, 0);
            Canvas canvas = new Canvas();
            stackPane.getChildren().add(canvas);
            panesOnRoundTrack.add(stackPane);
            canvasOnRoundTrack.add(canvas);
            setTurnNumbersOnRoundTrackCanvas(canvas.getGraphicsContext2D(), i);
        }
    }

    private void setTurnNumbersOnRoundTrackCanvas(GraphicsContext gc, Integer position){
        gc.setFill(GUIParameters.NUMBERS_DICE_COLOR);
        gc.fillText(position.toString(), 15, 15);
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
