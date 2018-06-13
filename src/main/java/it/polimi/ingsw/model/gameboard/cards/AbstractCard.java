package it.polimi.ingsw.model.gameboard.cards;

import it.polimi.ingsw.model.utility.JSONTag;
import org.json.simple.JSONObject;

/**
 * Represents a basic card with its basic functionality.
 * Every Card is a child of AbstractCard.
 */
public abstract class AbstractCard implements Card {

    protected String name;
    protected String description;
    protected int id;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject encode() {
        assert id > 0;
        JSONObject obj = new JSONObject();
        obj.put(JSONTag.CARD_ID, id);
        obj.put(JSONTag.NAME, name);
        obj.put(JSONTag.DESCRIPTION, description);
        return obj;
    }

    @Override
    public String toString() {
        return "#" + id + ": " + name;
    }
}
