package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.utility.Shade;

public class GrindingStone extends AbstractToolCard {

    GrindingStone(ConcreteGameStatus status) {
        super(status, 10);
        name = "Grinding Stone";
        description = "After drafting, flip the die to its opposite side.";
    }

    @Override
    public boolean isLegal() {
        return toolCheck() && favorPointsCheck() && phaseCheck();
    }

    private boolean phaseCheck() {
        return status.getStateHolder().getDieHolder() != null;
    }

    @Override
    public void execute() {
        if (isLegal()) {
            status.getStateHolder().setToolUsed(true);
            takePointsFromPlayer();
            addFavorPoints();
            flip(status.getStateHolder().getDieHolder());
        }
    }

    private void flip(Die d) {
        Shade[] shades = Shade.values();
        int index = -1;
        for (int i = 0; i < shades.length && index < 0; i++)
            if (shades[i].equals(d.getShade()))
                index = i;
        d.setShade(shades[shades.length - 1 - index]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getUpperCard());
        sb.append("|");
        sb.append("(FP:").append(getFavorPoints()).append(")");
        for (int i = 0; i < pixelWidth - 6; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("[⚀] ⬌ [⚅]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("[⚁] ⬌ [⚄]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("[⚂] ⬌ [⚃]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }
}
