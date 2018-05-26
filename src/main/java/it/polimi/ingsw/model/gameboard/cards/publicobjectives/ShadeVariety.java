package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Shade;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

class ShadeVariety extends AbstractCard implements PublicObjectiveCard {

    ShadeVariety(int id) {
        name = "Shade Variety";
        description = "{5} Set of one of each value anywhere.";
        this.id = id;
    }

    @Override
    public int getValuePoints(WindowFrame window) {
        if (window == null) throw new NullPointerException("Null map.");
        Shade[] shades = Shade.values();
        int[] occurrences = new int[shades.length];
        for (Die d : window.getDice())
            for (int index = 0; index < shades.length; index++)
                if (shades[index].equals(d.getShade()))
                    occurrences[index]++;
        int min = occurrences[0];
        for (int i : occurrences)
            if (i < min)
                min = i;
        return min * 5;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String s = "[ ][ ]";
        sb.append(getUpperCard());
        sb.append("|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append(Shade.DARK).append(s).append(Shade.LIGHTER).append("[ ]");
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append(s).append(Shade.DARKER).append(s);
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append(Shade.LIGHTEST).append("[ ][ ][ ]").append(Shade.LIGHT);
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("[ ][ ][ ]").append(Shade.DARKEST).append("[ ]");
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }
}
