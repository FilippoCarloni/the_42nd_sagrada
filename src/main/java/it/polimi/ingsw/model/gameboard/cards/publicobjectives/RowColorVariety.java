package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

import java.util.HashSet;
import java.util.Set;

public class RowColorVariety extends AbstractCard implements PublicObjectiveCard {

    RowColorVariety(int id) {
        name = "Row Color Variety";
        description = "{6} Rows with no repeated color.";
        this.id = id;
    }

    @Override
    public int getValuePoints(WindowFrame window) {
        if (window == null) throw new NullPointerException("Null map.");
        int valuePoints = 0;
        Set<Color> colors;
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            colors = new HashSet<>();
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                Die d = window.getDie(i, j);
                if (d != null)
                    colors.add(d.getColor());
            }
            if (colors.size() == Parameters.MAX_COLUMNS)
                valuePoints += 6;
        }
        return valuePoints;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getUpperCard());
        sb.append("|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        for (int i = 0; i < Parameters.MAX_COLUMNS; i++) sb.append("[ ]");
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("").append(Color.RED).append(Color.BLUE).append(Color.YELLOW).append(Color.PURPLE).append(Color.GREEN);
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        for (int i = 0; i < Parameters.MAX_COLUMNS; i++) sb.append("[ ]");
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        for (int i = 0; i < Parameters.MAX_COLUMNS; i++) sb.append("[ ]");
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }
}
