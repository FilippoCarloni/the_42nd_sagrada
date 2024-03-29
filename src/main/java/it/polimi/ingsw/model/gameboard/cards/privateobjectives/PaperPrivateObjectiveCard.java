package it.polimi.ingsw.model.gameboard.cards.privateobjectives;

import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Shade;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

import static it.polimi.ingsw.model.utility.ExceptionMessage.NULL_PARAMETER;

class PaperPrivateObjectiveCard extends AbstractCard implements PrivateObjectiveCard {

    private Color color;

    /**
     * Generates a new private objective card, provided required building information.
     * @param name Card's name
     * @param description Card's description
     * @param index Index of color label insertion in the description string
     * @param color Color that the card references
     * @param id Card's identifier
     */
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
        if (window == null) throw new NullPointerException(NULL_PARAMETER);
        return window.getDice().isEmpty() ? 0 : window.getDice().stream()
                .filter(d -> d.getColor().equals(this.getColor()))
                .map(Die::getShade)
                .mapToInt(Shade::getValue).sum();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
