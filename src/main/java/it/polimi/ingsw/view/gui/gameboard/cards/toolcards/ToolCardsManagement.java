package it.polimi.ingsw.view.gui.gameboard.cards.toolcards;


import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;

import static java.lang.Integer.parseInt;
import static jdk.nashorn.internal.objects.Global.print;

public class ToolCardsManagement {

    public void toolBehaviourSetter(ImageView toolCardImage, int id){
        if(id == 1){
            //Managing Grozing Pliers behaviour
            toolCardImage.setOnMouseClicked(e -> {
                GuiManager.getInstance().getConnectionController().send("tool " + id);
                grozingPliersManagement((JSONObject) GuiManager.getInstance().getGameBoardMessage().get(JSONTag.PICKED_DIE));
            });
        } else if (id == 11){
            //Managing Flux Remover behaviour
            toolCardImage.setOnMouseClicked(e -> {
                GuiManager.getInstance().getConnectionController().send("tool " + id);
                fluxRemoverManagement();
            });
        } else {
            //Other tool cards
            toolCardImage.setOnMouseClicked(e -> GuiManager.getInstance().getConnectionController().send("tool " + id));
        }
    }

    /**
     * Grozing Pliers tool card allows you to increase/decrease the drafted die's value; it needs a new small screen to choose
     * the value you wont (if die's shade is equal to six or to one, the new screen won't be open and the die will automatically
     * change his value).
     * @param draftedDie: JSONObject describing the die drafted by the user
     */
    private void grozingPliersManagement(JSONObject draftedDie){
        int value = parseInt((draftedDie.get(JSONTag.SHADE)).toString());
        if(value > 1 && value < 6) {
            openScreen(GUIParameters.GROZING_PLIERS_FXML_PATH, GUIParameters.GROZING_PLIERS_TITLE);
        } else if(value == 1) {
            GuiManager.getInstance().getConnectionController().send("increase");
        } else {
            GuiManager.getInstance().getConnectionController().send("decrease");
        }
    }

    /**
     * Flux Remover tool card allows you to choose a new value for the drafted die; it needs a new small screen to choose
     * the value you wont.
     */
    private void fluxRemoverManagement(){
        openScreen(GUIParameters.FLUX_REMOVER_FXML_PATH, GUIParameters.FLUX_REMOVER_TITLE);
    }

    private void openScreen(String FXMLFileToOpen, String title){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(GUIParameters.DEFAULT_FXML_DIRECTORY + FXMLFileToOpen));
            Stage stage = new Stage();
            stage.setTitle(title + " - " + GuiManager.getInstance().getUsernamePlayer1());
            stage.setScene(new Scene(parent, GUIParameters.TOOL_CARDS_WIDTH, GUIParameters.TOOL_CARDS_HEIGHT));
            stage.show();
        } catch (IOException e){
            print(e.getMessage());
        }
    }

}
