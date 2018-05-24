package it.polimi.ingsw.model.gameboard.cards;

import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.utility.Color;
import org.json.simple.JSONObject;

import java.util.NoSuchElementException;

import static java.lang.Integer.parseInt;

/**
 * Represents a Sagrada private objective card.
 * Every player has only one private objective card during a game.
 */
public interface PrivateObjectiveCard extends ObjectiveCard {

    /**
     * Returns the color of the card.
     * @return A Color enum value
     */
    Color getColor();

    /**
     * Generates a card instance, cloned from the JSON object.
     * @param obj A JSON Object that holds card-like information
     * @return A PrivateObjectiveCard instance
     */
    static PrivateObjectiveCard getCardFromJSON(JSONObject obj) {
        int id = parseInt(obj.get("id").toString());
        Deck d = new PrivateObjectiveDeck();
        while (d.size() > 0) {
            PrivateObjectiveCard card = (PrivateObjectiveCard) d.draw();
            if (card.getID() == id)
                return card;
        }
        throw new NoSuchElementException("Invalid JSON format: there's no matching card.");
    }
}
