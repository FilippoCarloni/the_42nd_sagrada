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

import static java.lang.Integer.parseInt;

public class CardsSetter implements CardsOnGameBoard {

    //TODO: refactor

    @Override
    public ArrayList<ImageView> setPublicCards(ImageView card1, ImageView card2, ImageView card3, JSONArray json, String directory) {
        ArrayList<ImageView> cards = new ArrayList<>();

        card1 = loadFromFile(json.get(0), directory);
        card2 = loadFromFile(json.get(1), directory);
        card3 = loadFromFile(json.get(2), directory);

        card1.setFitHeight(215);
        card1.setFitWidth(200);
        card2.setFitWidth(200);
        card2.setFitHeight(215);
        card3.setFitHeight(215);
        card3.setFitWidth(200);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);

        return cards;
    }
    @Override
    public void setPrivateCard(ImageView card, JSONObject json) {
        //Complete the implementation later
    }

    private ImageView loadFromFile(Object jsonCard, String directory){
        JSONObject jsonObject = (JSONObject) jsonCard;
        int id =parseInt(jsonObject.get(JSONTag.CARD_ID).toString());
        InputStream is;
        try{
            is = new FileInputStream(GUIParameters.DEFAULT_DIRECTORY + directory + "/" + id + ".jpg");
            return new ImageView(new Image(is));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
