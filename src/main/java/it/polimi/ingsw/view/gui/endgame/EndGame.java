package it.polimi.ingsw.view.gui.endgame;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.utility.GuiManager;
import it.polimi.ingsw.view.gui.utility.GUIParameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.ConnectException;

import static jdk.nashorn.internal.objects.Global.print;

/**
 * Controller for the End Game screen
 */

public class EndGame {

    @FXML
    private GridPane resultsGrid;

    /**
     * Method called when the user click on the button "Return to Lobby", it allows the player to
     * come back to lobby and eventually start a new game.
     * @param event: the ActionEvent generated when a player clicks on the button.
     */
    public void returnToLobby(ActionEvent event){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.DEFAULT_FXML_DIRECTORY + GUIParameters.LOBBY_FXML_PATH));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            GuiManager.setOnCloseRequest(stage);
            stage.setTitle(GUIParameters.LOBBY_TITLE + " - " + GuiManager.getInstance().getUsernameMainPlayer());
            stage.setScene(scene);
        } catch (IOException e) {
            print(e.getMessage());
        }
    }

    private void setFinalScore(JSONObject gameStat){
        JSONArray players = (JSONArray) gameStat.get(JSONTag.PLAYERS);
        for(int i = 0; i < players.size(); i++){
            Label username = new Label(((JSONObject)players.get(i)).get(JSONTag.USERNAME).toString());
            Label points = new Label(((JSONObject)players.get(i)).get(JSONTag.SCORE).toString());
            username.setTextAlignment(TextAlignment.CENTER);
            points.setTextAlignment(TextAlignment.CENTER);
            resultsGrid.add(username, GUIParameters.FIRST_COLUMN_ROW, i + 1);
            resultsGrid.add(points, GUIParameters.SECOND_COLUMN_ROW, i + 1);
        }
    }

    @FXML
    protected void initialize() throws ConnectException {
        JSONObject gameStat = GuiManager.getInstance().getGameStatMessage();
        GuiManager.getInstance().setGameStatMessage(null);
        setFinalScore(gameStat);
    }
}
