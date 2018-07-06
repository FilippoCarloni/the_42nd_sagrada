package it.polimi.ingsw.view.gui.gameboard.cards;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.gameboard.cards.toolcards.ToolCardsManagement;
import it.polimi.ingsw.view.gui.settings.GUIColor;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

/**
 * Class that manage all cards setting.
 */

public class CardsSetter {

    private CardsSetter(){}

    /**
     * Setter for public cards, on Game Board. It receive a JSONArray
     * @param json: JSONArray containing all information about cards.
     * @param toolCards: boolean that says if we are setting tool cards, in order to set on them
     *                 proper click events.
     */
    public static void setPublicCards(JSONArray json, ArrayList<Label> titles, ArrayList<TextArea> descriptions, boolean toolCards) {
        for (int i = 0; i < json.size(); i++) {
            drawCards((JSONObject) json.get(i), titles.get(i), descriptions.get(i));
            if(toolCards){
                titles.get(i).getStyleClass().add(GUIParameters.CLICKABLE);
                int id =parseInt(((JSONObject)json.get(i)).get(JSONTag.CARD_ID).toString());
                new ToolCardsManagement().toolBehaviourSetter(titles.get(i), id);
            }
        }
    }

    /**
     * Setter for Private Objective card on Window Frame Choice screen, and on Private Objective screen.
     * @param json: JSONObject containing the information about the private objective card.
     */
    public static void setPrivateCard(JSONObject json, Label title, Rectangle privateObjectiveRectangle, TextArea description) {
        int id = parseInt(json.get(JSONTag.CARD_ID).toString());
        String name = json.get(JSONTag.NAME).toString();
        String descriptionString = json.get(JSONTag.DESCRIPTION).toString();
        privateObjectiveRectangle.setFill(GUIColor.findById(id).getColor());

        title.setText(name);
        description.appendText(descriptionString);

    }

    //Support method, it draws all cards (on game board or not)
    private static void drawCards(JSONObject json, Label title, TextArea description){
        String name = json.get(JSONTag.NAME).toString();
        String descriptionString = json.get(JSONTag.DESCRIPTION).toString();

        title.setText(name);
        description.appendText(descriptionString);
    }
}
