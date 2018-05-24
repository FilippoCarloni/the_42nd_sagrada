package it.polimi.ingsw.model.gameboard.cards;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.commands.Executable;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolDeck;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.Integer.parseInt;

public interface ToolCard extends Card, Executable {

    int getFavorPoints();
    void addFavorPoints();
    List<Command> getCommands(String cmd);

    static ToolCard getCardFromJSON(JSONObject obj, ConcreteGameStatus status) {
        int id = parseInt(obj.get("id").toString());
        int favorPoints = parseInt(obj.get("favor_points").toString());
        Deck d = new ToolDeck(status);
        while (d.size() > 0) {
            ToolCard card = (ToolCard) d.draw();
            if (card.getID() == id) {
                while (card.getFavorPoints() < favorPoints)
                    card.addFavorPoints();
                assert card.getFavorPoints() == favorPoints;
                return card;
            }
        }
        throw new NoSuchElementException("Invalid JSON format: there's no matching card.");
    }
}
