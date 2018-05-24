package it.polimi.ingsw.model.gameboard.cards;

import it.polimi.ingsw.model.gameboard.cards.publicobjectives.PublicObjectiveDeck;
import org.json.simple.JSONObject;

import java.util.NoSuchElementException;

import static java.lang.Integer.parseInt;

/**
 * Represents a Sagrada public objective card.
 * Every game has three public objective cards, shared by all the players.
 */
public interface PublicObjectiveCard extends ObjectiveCard {

    /**
     * Generates a card instance, cloned from the JSON object.
     * @param obj A JSON Object that holds card-like information
     * @return A PublicObjectiveCard instance
     */
    static PublicObjectiveCard getCardFromJSON(JSONObject obj) {
        int id = parseInt(obj.get("id").toString());
        Deck d = new PublicObjectiveDeck();
        while (d.size() > 0) {
            PublicObjectiveCard card = (PublicObjectiveCard) d.draw();
            if (card.getID() == id)
                return card;
        }
        throw new NoSuchElementException("Invalid JSON format: there's no matching card.");
    }
}
