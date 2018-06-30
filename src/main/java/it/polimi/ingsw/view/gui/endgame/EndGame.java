package it.polimi.ingsw.view.gui.endgame;

import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ConnectException;
import java.rmi.RemoteException;

import static jdk.nashorn.internal.objects.Global.print;

/**
 * Controller for the End Game screen
 */

public class EndGame {

    @FXML
    private GridPane resultsGrid;

    /**
     * Method called when the user click on the button "Return to Lobby", it allows the player to
     * come back to lobby end eventually start a new game.
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


    @FXML
    protected void initialize() throws RemoteException, ConnectException {
        String gameStat = GuiManager.getInstance().getGameStatMessage();
        GuiManager.getInstance().setGameStatMessage(null);
        String[] players = gameStat.split("\n");
        for(int i = 0; i < players.length; i++){
            String[] player = players[i].split(":");
            Label username = new Label(player[0]);
            Label points = new Label(player[1]);
            username.setAlignment(Pos.CENTER);
            points.setAlignment(Pos.CENTER);
            resultsGrid.add(username, 0, i + 1);
            resultsGrid.add(points, 1, i + 1);
        }

    }
}
