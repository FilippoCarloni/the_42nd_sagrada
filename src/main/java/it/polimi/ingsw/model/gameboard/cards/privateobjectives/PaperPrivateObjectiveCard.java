package it.polimi.ingsw.model.gameboard.cards.privateobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Shade;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

class PaperPrivateObjectiveCard extends AbstractCard implements PrivateObjectiveCard {

    private Color color;

    PaperPrivateObjectiveCard(String name, String description, int index, Color color, int id) {
        this.name = name + color.getLabel();
        this.description = description.substring(0, index) + color.getLabel() + description.substring(index, description.length());
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
        return window.getDice().isEmpty() ? 0 : window.getDice().stream()
                .filter(d -> d.getColor().equals(this.getColor()))
                .map(Die::getShade)
                .mapToInt(Shade::getValue).sum();
    }
}
