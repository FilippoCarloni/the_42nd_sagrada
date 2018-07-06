package it.polimi.ingsw.view.gui.gameboard.cards;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.gameboard.cards.toolcards.ToolCardsManagement;
import it.polimi.ingsw.view.gui.settings.GUIColor;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
     * Setter for public cards, on Game Board.
     * @param json: JSONArray containing all information about cards.
     * @param toolCards: boolean that says if we are setting tool cards, in order to set on them
     *                 proper click events.
     */
    public static void setPublicCards(JSONArray json, ArrayList<VBox> containers, ArrayList<Label> titles, ArrayList<TextArea> descriptions, boolean toolCards) {

        for (int i = 0; i < json.size(); i++) {
            int id =parseInt(((JSONObject)json.get(i)).get(JSONTag.CARD_ID).toString());
            drawCardsFromId((JSONObject) json.get(i), titles.get(i), descriptions.get(i));
            if(toolCards){
                containers.get(i).getStyleClass().add("usable-cards");
                new ToolCardsManagement().toolBehaviourSetter(containers.get(i), id);
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


    private static void drawCardsFromId(JSONObject json, Label title, TextArea description){
        int id =parseInt(json.get(JSONTag.CARD_ID).toString());
        String name = json.get(JSONTag.NAME).toString();
        String descriptionString = json.get(JSONTag.DESCRIPTION).toString();

        title.setText(name + "\n" + id);
        description.appendText(descriptionString);
    }
}
