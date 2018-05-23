package it.polimi.ingsw.model.gameboard.cards;

import it.polimi.ingsw.model.gameboard.cards.publicobjectives.PublicObjectiveDeck;
import org.json.simple.JSONObject;

import java.util.NoSuchElementException;

public interface PublicObjectiveCard extends ObjectiveCard {

    static PublicObjectiveCard getCardFromJSON(JSONObject obj) {
        int id = (int) obj.get("id");
        Deck d = new PublicObjectiveDeck();
        while (d.size() > 0) {
            PublicObjectiveCard card = (PublicObjectiveCard) d.draw();
            if (card.getID() == id)
                return card;
        }
        throw new NoSuchElementException("Invalid JSON format: there's no matching card.");
    }
}
