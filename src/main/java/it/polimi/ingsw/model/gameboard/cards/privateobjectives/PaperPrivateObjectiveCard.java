package it.polimi.ingsw.model.gameboard.cards.privateobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.cards.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Shade;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

class PaperPrivateObjectiveCard extends AbstractCard implements PrivateObjectiveCard {

    private Color color;

    PaperPrivateObjectiveCard(Color color, int id) {
        this.name = "Shades of " + color.getLabel();
        this.description = "Sum of values on " + color.getLabel() + " dice.";
        this.color = color;
        this.id = id;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int getValuePoints(WindowFrame window) {
        if (window == null) throw new NullPointerException("Cannot evaluate points on a null window.");
        return window.getDice().stream()
                .filter(d -> d.getColor().equals(this.getColor()))
                .map(Die::getShade)
                .mapToInt(Shade::getValue).sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getUpperCard());
        sb.append("|");
        for (int i = 0; i < pixelWidth; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < pixelWidth - 2 * pixelWidth / 3; i++) sb.append(" ");
        for (int i = 0; i < pixelWidth / 3; i++) sb.append(color.paintBG(" "));
        for (int i = 0; i < pixelWidth - 2 * pixelWidth / 3; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < pixelWidth - 2 * pixelWidth / 3; i++) sb.append(" ");
        for (int i = 0; i < pixelWidth / 3; i++) sb.append(color.paintBG(" "));
        for (int i = 0; i < pixelWidth - 2 * pixelWidth / 3; i++) sb.append(" ");
        sb.append("|\n");
        sb.append("|");
        for (int i = 0; i < pixelWidth; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }
}
