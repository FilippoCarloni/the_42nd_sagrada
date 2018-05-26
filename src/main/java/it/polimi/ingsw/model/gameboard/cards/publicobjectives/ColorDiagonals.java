package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

class ColorDiagonals extends AbstractCard implements PublicObjectiveCard {

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
                if (window.getDie(i, j) != null) {

                    found = (window.getDie(i - 1, j - 1) != null &&
                            window.getDie(i, j).getColor() == window.getDie(i - 1, j - 1).getColor());

                    if (!found)
                        found = (window.getDie(i - 1, j + 1) != null &&
                                window.getDie(i, j).getColor() == window.getDie(i - 1, j + 1).getColor());

                    if (!found)
                        found = (window.getDie(i + 1, j - 1) != null &&
                                window.getDie(i, j).getColor() == window.getDie(i + 1, j - 1).getColor());

                    if (!found)
                        found = (window.getDie(i + 1, j + 1) != null &&
                                window.getDie(i, j).getColor() == window.getDie(i + 1, j + 1).getColor());

                    if (found) valuePoints++;
                }
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
