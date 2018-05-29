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
    protected int pixelWidth = 21;

    protected String getUpperCard() {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int i = 0; i < pixelWidth; i++) sb.append("_");
        sb.append(" \n");
        return sb.toString();
    }

    protected String getLowerCard() {
        int nameLength = 40;
        int descriptionLength = 120;
        StringBuilder nameBuilder = new StringBuilder(this.name);
        StringBuilder descriptionBuilder = new StringBuilder(this.description);
        for (int i = 0; i < nameLength - this.name.length(); i++)
            nameBuilder.append(" ");
        for (int i = 0; i < descriptionLength - this.description.length(); i++)
            descriptionBuilder.append(" ");
        String longName = nameBuilder.toString();
        String longDescription = descriptionBuilder.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("\n|");
        for (int i = 0; i < pixelWidth; i++) sb.append("-");
        sb.append("|\n");
        int i;
        for (i = 0; i < longName.length() / pixelWidth; i++)
            sb.append("|").append(longName.substring(i * pixelWidth, pixelWidth * (i + 1))).append( "|\n");
        sb.append("|").append(longName.substring(
                i * pixelWidth, longName.length()));
        for (int j = 0; j < pixelWidth - longName.length() + i * pixelWidth; j++)
            sb.append(" ");
        sb.append("|\n|");
        for (i = 0; i < pixelWidth; i++) sb.append("-");
        sb.append("|\n");
        for (i = 0; i < longDescription.length() / pixelWidth; i++)
            sb.append("|").append(longDescription.substring(i * pixelWidth, pixelWidth * (i + 1))).append("|\n");
        sb.append("|").append(longDescription.substring(
                i * pixelWidth, longDescription.length()));
        for (int j = 0; j < pixelWidth - longDescription.length() + i * pixelWidth; j++)
            sb.append(" ");
        sb.append("|\n|");
        for (int j = 0; j < pixelWidth; j++) sb.append("_");
        sb.append("|\n");
        return sb.toString();
    }

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
        return obj;
    }
}
