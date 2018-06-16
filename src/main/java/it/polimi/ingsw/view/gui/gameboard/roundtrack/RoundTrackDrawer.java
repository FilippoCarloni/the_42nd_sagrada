package it.polimi.ingsw.view.gui.gameboard.roundtrack;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: complete implementation of initialSettings method
//TODO: understand how I will receive information about dice to put into RoundTrack
//TODO: maybe static class?

public class RoundTrackDrawer {

    private Map<Integer, ArrayList<Die>> diceOnRoundTrack = new HashMap<>();

    public void roundTrackFiller(GridPane roundTrackPane, List<StackPane> panesOnRoundTrack, List<Canvas> canvasOnRoundTrack){
        for(int i = 0; i < GUIParameters.MAX_NUM_TURNS; i++){
            StackPane stackPane = new StackPane();
            roundTrackPane.add(stackPane, i, 0);
            Canvas canvas = new Canvas();
            stackPane.getChildren().add(canvas);
            panesOnRoundTrack.add(stackPane);
            canvasOnRoundTrack.add(canvas);
        }
    }

    //This method will draw the initial state of Round Track
    public void initialSetting(AnchorPane backGroundAnchorPane, List<StackPane> panesOnRoundTrack){
        //Set Background image on backGroundAnchorPane
        //Set numbers of rounds on each pane into panesOnRoundTrack
    }

    public void addDice(int turnNumber, ArrayList<Die> dieToPut){
        diceOnRoundTrack.put(turnNumber, dieToPut);
    }
}
