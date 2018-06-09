package it.polimi.ingsw.view.gui.gameboard.cards;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public interface CardsOnGameBoard {

    ArrayList<ImageView> setPublicCards(ImageView card1, ImageView card2, ImageView card3, JSONArray json, String directory);
    void setPrivateCard(ImageView card, JSONObject json);
}
