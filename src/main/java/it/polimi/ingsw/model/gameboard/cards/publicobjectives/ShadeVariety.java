package it.polimi.ingsw.model.gameboard.cards.publicobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Shade;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.counting;

public class ShadeVariety extends AbstractCard implements PublicObjectiveCard {

    ShadeVariety(int id) {
        name = "Shade Variety";
        description = "{5} Set of one of each value anywhere.";
        this.id = id;
    }

    @Override
    public int getValuePoints(WindowFrame window) {
        if (window == null) throw new NullPointerException("Null map.");
        return StreamSupport.stream(window.spliterator(), false)
                .map(Die::getShade)
                .collect(Collectors.groupingBy(x -> x, counting()))
                .values()
                .stream()
                .mapToInt(Long::intValue)
                .summaryStatistics()
                .getMin()
                * 5;
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
