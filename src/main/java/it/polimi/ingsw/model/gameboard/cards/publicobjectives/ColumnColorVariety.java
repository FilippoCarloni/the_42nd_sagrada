package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

import java.util.HashSet;
import java.util.Set;

public class ColumnColorVariety extends AbstractCard implements PublicObjectiveCard {

    ColumnColorVariety(int id) {
        name = "Column Color Variety";
        description = "{5} Columns with no repeated colors.";
        this.id = id;
    }

    @Override
    public int getValuePoints(WindowFrame window) {
        if (window == null) throw new NullPointerException("Null window.");
        int valuePoints = 0;
        Set<Color> colors;
        for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
            colors = new HashSet<>();
            for (int i = 0; i < Parameters.MAX_ROWS; i++) {
                Die d = window.getDie(i, j);
                if (d != null)
                    colors.add(d.getColor());
            }
            if (colors.size() == Parameters.MAX_ROWS)
                valuePoints += 5;
        }
        return valuePoints;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String s = "[ ][ ]";
        sb.append(getUpperCard());
        sb.append("|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append(s).append(Color.BLUE).append(s);
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append(s).append(Color.RED).append(s);
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append(s).append(Color.YELLOW).append(s);
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append(s).append(Color.PURPLE).append(s);
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }
}
