package it.polimi.ingsw.view.gui.gameboard.cards;

import org.json.simple.JSONObject;
import javafx.scene.image.ImageView;

public interface CardsOnGameBoard {

    void setPublicCards(ImageView card1, ImageView card2, ImageView card3, JSONObject json, boolean tool);
    void setPrivateCard(ImageView card, JSONObject json);
}
