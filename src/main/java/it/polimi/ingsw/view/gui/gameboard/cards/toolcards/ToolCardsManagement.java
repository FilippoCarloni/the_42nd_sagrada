package it.polimi.ingsw.view.gui.gameboard.cards.toolcards;


import it.polimi.ingsw.connection.constraints.ConnectionCommands;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.utility.GuiManager;
import it.polimi.ingsw.view.gui.utility.GUIParameters;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.ConnectException;

import static java.lang.Integer.parseInt;
import static jdk.nashorn.internal.objects.Global.print;

/**
 * Class that handle the tool cards management.
 */

public class ToolCardsManagement {

    /**
     * Main method to handle tool cards. It distinguish between the two tool cards that will need a new screen (Grozing Pliers
     * and Flux Remover) and the others, and then set the behaviour by setting methods on mouse clicked.
     * @param toolCardTitle: the Label containing the specific tool card title, on which will be set the behaviour on click.
     * @param id: the tool card id, used to distinguish between Grozing Pliers, Flux Remover and others.
     */
    public void toolBehaviourSetter(Label toolCardTitle, int id){
        if(id == 1){
            //Managing Grozing Pliers behaviour
            toolCardTitle.setOnMouseClicked(e -> {
                try {
                    GuiManager.getInstance().getConnectionController().send(GUIParameters.TOOL + ConnectionCommands.COMMAND_SEPARATOR + id);
                    if(GuiManager.getInstance().getGameBoardMessage().get(JSONTag.PICKED_DIE) != null) {
                        grozingPliersManagement((JSONObject) GuiManager.getInstance().getGameBoardMessage().get(JSONTag.PICKED_DIE));
                    }
                } catch (ConnectException e1) {
                    print(e1.getMessage());
                }
            });
        } else if (id == 11){
            //Managing Flux Remover behaviour
            toolCardTitle.setOnMouseClicked(e -> {
                try {
                    GuiManager.getInstance().getConnectionController().send(GUIParameters.TOOL + ConnectionCommands.COMMAND_SEPARATOR + id);
                    if(GuiManager.getInstance().getGameBoardMessage().get(JSONTag.PICKED_DIE) != null)
                        fluxRemoverManagement();
                } catch (ConnectException e1) {
                    print(e1.getMessage());
                }
            });
        } else {
            //Other tool cards
            toolCardTitle.setOnMouseClicked(e -> {
                try {
                    GuiManager.getInstance().getConnectionController().send(GUIParameters.TOOL + ConnectionCommands.COMMAND_SEPARATOR + id);
                } catch (ConnectException e1) {
                    print(e1.getMessage());
                }
            });
        }
    }

    /**
     * Grozing Pliers tool card allows you to increase/decrease the drafted die's value; it needs a new small screen to choose
     * the value you wont (if die's shade is equal to six or to one, the new screen won't be open and the die will automatically
     * change his value).
     * @param draftedDie: JSONObject describing the die drafted by the user
     */
    private void grozingPliersManagement(JSONObject draftedDie) throws ConnectException {
        int value = parseInt((draftedDie.get(JSONTag.SHADE)).toString());
        if(value > 1 && value < 6) {
            openScreen(GUIParameters.GROZING_PLIERS_FXML_PATH, GUIParameters.GROZING_PLIERS_TITLE);
        } else if(value == 1) {
            GuiManager.getInstance().getConnectionController().send(GUIParameters.INCREASE);
        } else {
            GuiManager.getInstance().getConnectionController().send(GUIParameters.DECREASE);
        }
    }

    /**
     * Flux Remover tool card allows you to choose a new value for a new drafted die; it needs a new small screen to choose
     * the value you wont.
     */
    private void fluxRemoverManagement(){
        openScreen(GUIParameters.FLUX_REMOVER_FXML_PATH, GUIParameters.FLUX_REMOVER_TITLE);
    }

    private void openScreen(String fxmlFileToOpen, String title){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.DEFAULT_FXML_DIRECTORY + fxmlFileToOpen));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title + GUIParameters.SEPARATOR + GuiManager.getInstance().getUsernameMainPlayer());
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e){
            print(e.getMessage());
        }
    }

}
