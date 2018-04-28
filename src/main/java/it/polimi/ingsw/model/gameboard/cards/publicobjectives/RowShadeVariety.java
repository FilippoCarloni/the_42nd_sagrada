package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.utility.Parameters;
import it.polimi.ingsw.model.gameboard.utility.Shade;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

import java.util.HashSet;
import java.util.Set;

public class RowShadeVariety extends AbstractCard implements PublicObjectiveCard {

    RowShadeVariety() {
        name = "Row Shade Variety";
        description = "{5} Rows with no repeated values.";
    }

    @Override
    public int getValuePoints(WindowFrame window) {
        if (window == null) throw new NullPointerException("Null map.");
        int valuePoints = 0;
        Set<Shade> shades;
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            shades = new HashSet<>();
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                Die d = window.getDie(i, j);
                if (d != null)
                    shades.add(d.getShade());
            }
            if (shades.size() == Parameters.MAX_COLUMNS)
                valuePoints += 5;
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
        for (int i = 0; i < Parameters.MAX_COLUMNS; i++) sb.append("[ ]");
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append(Shade.LIGHT.toString()).append(
                Shade.DARKEST.toString()).append(
                Shade.LIGHTEST.toString()).append(
                Shade.LIGHTER.toString()).append(
                Shade.DARK.toString());
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
