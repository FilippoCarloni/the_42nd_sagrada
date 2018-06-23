package it.polimi.ingsw.view.gui.preliminarystages;

import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

import static jdk.nashorn.internal.objects.Global.print;

/**
 * Controller class for the lobby screen.
 */

public class LobbyController {

    @FXML
    private Button startButton;
    @FXML
    private TextArea textLobby;

    /**
     * Method that allows the player to enter the lobby, joining a new game.
      */
    public void enteredLobby(){
        GuiManager.getInstance().getConnectionController().send("play");
    }

    /**
     * Method that calls the "change scene" method, to go or to the map selection screen or to the
     * main board, if the game is already started.
     * @param event: the ActionEvent generated when a player clicks on the button.
     */
    public void startClicked(ActionEvent event){
        lobbyToPreGameOrToGame(event);
    }

    /**
     * Method called by the update() method into GuiManager, to write messages into the TextArea.
     * @param message: the message to print.
     * @see GuiManager
     */
    public void printConnectionOrDisconnection(String message){
        textLobby.appendText(" " + message + "\n");
    }

    /**
     * Method used by the update () method into GuiManager, to make the startButton not disabled.
     * @return this.startButton.
     * @see GuiManager
     */
    public Button getStartButton(){
        return startButton;
    }

    //Change scene Management
    private void lobbyToPreGameOrToGame(ActionEvent event){
        try {
            if(GuiManager.getInstance().getGameBoardMessage() != null){
               new WindowFramesChoice().launchGameBoard(event);
            } else {
                Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.DEFAULT_FXML_DIRECTORY + GUIParameters.MAP_CHOICE_FXML_PATH));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle(GUIParameters.MAP_CHOICE_SCENE_TITLE + " - " + GuiManager.getInstance().getUsernamePlayer1());
                stage.setOnCloseRequest(e -> {
                    GuiManager.getInstance().getConnectionController().send("exit");
                    System.exit(0);
                });
                stage.setScene(scene);
            }
        } catch (IOException e) {
            print(e.getMessage());
        }
    }

    @FXML
    protected void initialize(){
        GuiManager.getInstance().setLobbyController(this);
        startButton.setDisable(true);
    }
}