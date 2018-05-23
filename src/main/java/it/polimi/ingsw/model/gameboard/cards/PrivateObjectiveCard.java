package it.polimi.ingsw.model.gameboard.cards;

import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.utility.Color;
import org.json.simple.JSONObject;

import java.util.NoSuchElementException;

public interface PrivateObjectiveCard extends ObjectiveCard {

    Color getColor();

    static PrivateObjectiveCard getCardFromJSON(JSONObject obj) {
        int id = (int) obj.get("id");
        Deck d = new PrivateObjectiveDeck();
        while (d.size() > 0) {
            PrivateObjectiveCard card = (PrivateObjectiveCard) d.draw();
            if (card.getID() == id)
                return card;
        }
        throw new NoSuchElementException("Invalid JSON format: there's no matching card.");
    }
}
