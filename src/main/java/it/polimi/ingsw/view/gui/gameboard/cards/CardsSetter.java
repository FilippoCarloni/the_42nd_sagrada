package it.polimi.ingsw.view.gui.gameboard.cards;

import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.view.gui.gameboard.cards.toolcards.ToolCardsManagement;
import it.polimi.ingsw.view.gui.settings.GUIParameters;
import javafx.scene.image.Image;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static jdk.nashorn.internal.objects.Global.print;

/**
 * Class that manage all cards setting.
 */

public class CardsSetter {

    private CardsSetter(){}

    /**
     * Setter for public cards, on Game Board.
     * @param json: JSONArray containing all information about cards.
     * @param directory: directory in which I will take the cards' images
     * @param toolCards: boolean that says if we are setting tool cards, in order to set on them
     *                 proper click events.
     * @return a List containing all ImageView,  with the cards' images inside them
     */
    public static List<ImageView> setPublicCards(JSONArray json, String directory, boolean toolCards) {
        List<ImageView> cards = new ArrayList<>();

        for (int i = 0; i < json.size(); i++) {
            int id =parseInt(((JSONObject)json.get(i)).get(JSONTag.CARD_ID).toString());
            cards.add(loadFromFile(id, directory));
            if(toolCards){
                cards.get(i).getStyleClass().clear();
                cards.get(i).getStyleClass().add("usable-cards");
                new ToolCardsManagement().toolBehaviourSetter(cards.get(i), id);
            }
            setCardsDimension(cards.get(i), GUIParameters.CARDS_ON_GAME_BOARD_WIDTH, GUIParameters.CARDS_ON_GAME_BOARD_HEIGHT);
        }
        return cards;
    }

    /**
     * Setter for Private Objective card on Window Frame Choice screen, and on Private Objective screen.
     * @param json: JSONObject containing the information about the private objective card.
     * @return an ImageView, with the card's image inside id.
     */
    public static ImageView setPrivateCard(JSONObject json) {
        int id = parseInt(json.get(JSONTag.CARD_ID).toString());

        ImageView card = loadFromFile(id, GUIParameters.PRIV_OBJ_DIRECTORY);
        setCardsDimension(card, GUIParameters.CARD_ON_MAP_CHOICE_WIDTH, GUIParameters.CARD_ON_MAP_CHOICE_HEIGHT);
        return card;
    }

    //Helpers for previous methods
    private static ImageView loadFromFile(int id,  String directory) {
        try{
            InputStream is;
            is = new FileInputStream(GUIParameters.DEFAULT_DIRECTORY + directory + "/" + id + ".jpg");
            return new ImageView(new Image(is));
        } catch (IOException e) {
            print(e.getMessage());
        }
        return new ImageView();
    }
    private static void setCardsDimension(ImageView card, double width, double height){
        card.setFitWidth(width);
        card.setFitHeight(height);
    }
}
