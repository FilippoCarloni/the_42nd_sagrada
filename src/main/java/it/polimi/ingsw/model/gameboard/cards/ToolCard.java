package it.polimi.ingsw.model.gameboard.cards;

import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolDeck;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.JSONTag;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.Integer.parseInt;

public interface ToolCard extends Card {

    int getFavorPoints();
    void addFavorPoints();
    boolean isLegal(GameData gameData);
    void execute(GameData gameData);
    List<Command> getCommands(Player player, GameData gameData, String cmd);

    static void tearDown(GameData gameData) {
        gameData.setPassiveToolID(0);
        gameData.setActiveToolID(0);
    }

    static ToolCard getCardFromJSON(JSONObject obj) {
        int id = parseInt(obj.get(JSONTag.CARD_ID).toString());
        int favorPoints = parseInt(obj.get(JSONTag.TOOL_FAVOR_POINTS).toString());
        Deck d = new ToolDeck();
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
