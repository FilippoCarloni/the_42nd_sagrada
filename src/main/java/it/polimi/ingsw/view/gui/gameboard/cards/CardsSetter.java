package it.polimi.ingsw.view.gui.gameboard.cards;

import it.polimi.ingsw.model.utility.JSONTag;
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

public class CardsSetter {

    private CardsSetter(){}

    //Setter for public cards, on Game Board
    public static List<ImageView> setPublicCards(JSONArray json, String directory) {
        List<ImageView> cards = new ArrayList<>();

        ImageView card1 = loadFromFile(json.get(0), directory);
        ImageView card2 = loadFromFile(json.get(1), directory);
        ImageView card3 = loadFromFile(json.get(2), directory);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);

        for (ImageView card : cards) {
            setCardsDimension(card, GUIParameters.CARDS_ON_GAME_BOARD_WIDTH, GUIParameters.CARDS_ON_GAME_BOARD_HEIGHT);
        }
        return cards;
    }

    //Setter for Private Objective card, for now just on Window Frame Choice screen
    public static ImageView setPrivateCard(JSONObject json) {
        ImageView card = loadFromFile(json, GUIParameters.PRIV_OBJ_DIRECTORY);
        setCardsDimension(card, GUIParameters.CARD_ON_MAP_CHOICE_WIDTH, GUIParameters.CARD_ON_MAP_CHOICE_HEIGHT);
        return card;
    }

    //Helpers for previous methods
    private static ImageView loadFromFile(Object jsonCard, String directory) {
        try{
            JSONObject jsonObject = (JSONObject) jsonCard;
            int id =parseInt(jsonObject.get(JSONTag.CARD_ID).toString());
            InputStream is;
            is = new FileInputStream(GUIParameters.DEFAULT_DIRECTORY + directory + "/" + id + ".jpg");
            return new ImageView(new Image(is));
        } catch (IOException e) {
            print(e.getMessage());
        }
        return null;
    }
    private static void setCardsDimension(ImageView card, double width, double height){
        card.setFitWidth(width);
        card.setFitHeight(height);
    }
}
