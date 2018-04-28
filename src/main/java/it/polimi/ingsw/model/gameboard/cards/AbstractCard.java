package it.polimi.ingsw.model.gameboard.cards;

public abstract class AbstractCard implements Card {

    protected String name;
    protected String description;
    protected int pixelWidth = 21;

    protected String getUpperCard() {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int i = 0; i < pixelWidth; i++) sb.append("_");
        sb.append(" \n");
        return sb.toString();
    }

    protected String getLowerCard() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n|");
        for (int i = 0; i < pixelWidth; i++) sb.append("-");
        sb.append("|\n");
        int i;
        for (i = 0; i < getName().length() / pixelWidth; i++)
            sb.append("|").append(getName().substring(i * pixelWidth, pixelWidth * (i + 1))).append( "|\n");
        sb.append("|").append(getName().substring(
                i * pixelWidth, getName().length()));
        for (int j = 0; j < pixelWidth - getName().length() + i * pixelWidth; j++)
            sb.append(" ");
        sb.append("|\n|");
        for (i = 0; i < pixelWidth; i++) sb.append("-");
        sb.append("|\n");
        for (i = 0; i < this.getDescription().length() / pixelWidth; i++)
            sb.append("|").append(getDescription().substring(i * pixelWidth, pixelWidth * (i + 1))).append("|\n");
        sb.append("|").append(getDescription().substring(
                i * pixelWidth, getDescription().length()));
        for (int j = 0; j < pixelWidth - this.getDescription().length() + i * pixelWidth; j++)
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
}
