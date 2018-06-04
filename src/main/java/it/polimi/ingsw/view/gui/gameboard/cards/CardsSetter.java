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

public class CardsSetter implements CardsOnGameBoard {

    //TODO: method to get card name from id number

    @Override
    public void setPublicCards(ImageView card1, ImageView card2, ImageView card3, JSONObject json, boolean tool) {
        JSONArray publicCards;
        String directory;
        if(tool){
            publicCards = (JSONArray) json.get(JSONTag.TOOLS);
            directory = GUIParameters.TOOL_DIRECTORY;
        }
        else{
            publicCards = (JSONArray) json.get(JSONTag.PUBLIC_OBJECTIVES);
            directory = GUIParameters.PUBOBJ_DIRECTORY;
        }
        card1 = loadFromFile(publicCards.get(0), directory);
        card2 = loadFromFile(publicCards.get(0), directory);
        card3 = loadFromFile(publicCards.get(0), directory);

    }
    @Override
    public void setPrivateCard(ImageView card, JSONObject json) {
    }

    private ImageView loadFromFile(Object jsonCard, String directory){
        InputStream is;
        try{
            is = new FileInputStream("/images/" + directory + jsonCard);
            return new ImageView(new Image(is));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
