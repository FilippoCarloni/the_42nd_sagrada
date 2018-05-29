package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.cards.ToolCard;
import it.polimi.ingsw.model.utility.JSONTag;
import org.json.simple.JSONObject;

public abstract class AbstractToolCard extends AbstractCard implements ToolCard {

    private int favorPoints;

    AbstractToolCard(int id) {
        this.id = id;
    }

    @Override
    public int getFavorPoints() {
        return favorPoints;
    }

    @Override
    public void addFavorPoints() {
        favorPoints = favorPoints == 0 ? favorPoints + 1 : favorPoints + 2;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject encode() {
        JSONObject obj = super.encode();
        obj.put(JSONTag.TOOL_FAVOR_POINTS, favorPoints);
        return obj;
    }
}
