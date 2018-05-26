package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Shade;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

class DarkShades extends AbstractCard implements PublicObjectiveCard {

    DarkShades(int id) {
        name = "Dark shades";
        description = "{2} Sets of 5 and 6 values anywhere.";
        this.id = id;
    }

    @Override
    public int getValuePoints(WindowFrame window) {
        if (window == null) throw new NullPointerException("Null map.");
        int darker = 0;
        int darkest = 0;
        for (Die d : window.getDice()) {
            if (d.getShade().equals(Shade.DARKER))
                darker++;
            else if (d.getShade().equals(Shade.DARKEST))
                darkest++;
        }
        return darker < darkest ? darker * 2 : darkest * 2;
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
        sb.append("[ ]").append(Shade.DARKER).append("[ ][ ][ ]");
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("[ ][ ][ ]").append(Shade.DARKEST).append("[ ]");
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("[ ][ ][ ][ ][ ]");
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }
}
