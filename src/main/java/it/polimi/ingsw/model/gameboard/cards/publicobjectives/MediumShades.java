package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Shade;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

class MediumShades extends AbstractCard implements PublicObjectiveCard {

    MediumShades(int id) {
        name = "Medium shades";
        description = "{2} Sets of 3 and 4 values anywhere.";
        this.id = id;
    }

    @Override
    public int getValuePoints(WindowFrame window) {
        if (window == null) throw new NullPointerException("Null map.");
        return window.getDice().stream()
                .map(Die::getShade)
                .filter(s -> s.equals(Shade.LIGHT) || s.equals(Shade.DARK))
                .collect(Collectors.groupingBy(x -> x, counting()))
                .values()
                .stream()
                .mapToInt(Long::intValue)
                .summaryStatistics()
                .getMin()
                * 2;
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
        sb.append("[ ]").append(Shade.LIGHT).append("[ ][ ][ ]");
        for (int i = 0; i < pixelWidth - 15 - (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 15) / 2; i++) sb.append(" ");
        sb.append("[ ][ ][ ]").append(Shade.DARK).append("[ ]");
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
