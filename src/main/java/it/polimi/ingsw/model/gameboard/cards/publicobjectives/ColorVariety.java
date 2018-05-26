package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

class ColorVariety extends AbstractCard implements PublicObjectiveCard {

    ColorVariety(int id) {
        name = "Color Variety";
        description = "{4} Sets of one of each color anywhere.";
        this.id = id;
    }

    @Override
    public int getValuePoints(WindowFrame window) {
        if (window == null) throw new NullPointerException("Null map.");
        Color[] colors = Color.values();
        int[] occurrences = new int[colors.length];
        for (Die d : window.getDice())
            for (int index = 0; index < colors.length; index++)
                if (colors[index].equals(d.getColor()))
                    occurrences[index]++;
        int min = occurrences[0];
        for (int i : occurrences)
            if (i < min)
                min = i;
        return min * 4;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getUpperCard());
        sb.append("|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("[ ]").append(Color.GREEN).append("[ ][ ][ ]");
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append(Color.PURPLE).append("[ ][ ][ ][ ]");
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("[ ][ ]").append(Color.YELLOW).append("[ ]").append(Color.BLUE);
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("[ ]").append(Color.RED).append("[ ][ ][ ]");
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }
}
