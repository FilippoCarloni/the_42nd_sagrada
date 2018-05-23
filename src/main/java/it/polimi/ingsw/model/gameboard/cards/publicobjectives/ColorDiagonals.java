package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

public class ColorDiagonals extends AbstractCard implements PublicObjectiveCard {

    ColorDiagonals(int id) {
        name = "Color Diagonals";
        description = "{#} Count of diagonally adjacent same color dice.";
        this.id = id;
    }

    @Override
    public int getValuePoints(WindowFrame window) {
        if (window == null) throw new NullPointerException("Null window.");
        int valuePoints = 0;
        boolean found;
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                found = false;
                if (i - 1 >= 0 && j - 1 >= 0 &&
                        window.getDie(i, j).getColor().equals(window.getDie(i - 1, j - 1).getColor())) {
                    found = true;
                    valuePoints++;
                }
                if (!found && i - 1 >= 0 && j + 1 < Parameters.MAX_COLUMNS &&
                        window.getDie(i, j).getColor().equals(window.getDie(i - 1, j + 1).getColor())) {
                    found = true;
                    valuePoints++;
                }
                if (!found && i + 1 < Parameters.MAX_ROWS && j - 1 >= 0 &&
                        window.getDie(i, j).getColor().equals(window.getDie(i + 1, j - 1).getColor())) {
                    found = true;
                    valuePoints++;
                }
                if (!found && i + 1 < Parameters.MAX_ROWS && j + 1 < Parameters.MAX_COLUMNS &&
                        window.getDie(i, j).getColor().equals(window.getDie(i + 1, j + 1).getColor()))
                    valuePoints++;
            }
        }
        return valuePoints;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getUpperCard());
        sb.append("|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("[ ][ ][ ][ ][ ]");
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("[ ]").append(Color.BLUE).append("[ ][ ]").append(Color.YELLOW);
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append(Color.BLUE).append("[ ]").append(Color.BLUE).append(Color.YELLOW).append("[ ]");
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("[ ][ ]").append(Color.YELLOW).append(Color.BLUE).append("[ ]");
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }
}
