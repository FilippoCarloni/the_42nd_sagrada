package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.utility.Shade;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

import java.util.HashSet;
import java.util.Set;

public class ColumnShadeVariety extends AbstractCard implements PublicObjectiveCard {

    ColumnShadeVariety(int id) {
        name = "Column Shade Variety";
        description = "{5} Columns with no repeated values.";
        this.id = id;
    }

    @Override
    public int getValuePoints(WindowFrame window) {
        if (window == null) throw new NullPointerException("Null window.");
        int valuePoints = 0;
        Set<Shade> shades;
        for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
            shades = new HashSet<>();
            for (int i = 0; i < Parameters.MAX_ROWS; i++) {
                Die d = window.getDie(i, j);
                if (d != null)
                    shades.add(d.getShade());
            }
            if (shades.size() == Parameters.MAX_ROWS)
                valuePoints += 5;
        }
        return valuePoints;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getUpperCard());
        String s = "[ ][ ]";
        sb.append("|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append(s).append(Shade.DARKEST).append(s);
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append(s).append(Shade.LIGHT).append(s);
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append(s).append(Shade.LIGHTER).append(s);
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append(s).append(Shade.DARK).append(s);
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }
}
