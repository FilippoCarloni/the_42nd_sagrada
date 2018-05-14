package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;

public class GrozingPliers extends AbstractToolCard {

    GrozingPliers(ConcreteGameStatus status) {
        super(status, 1);
        name = "Grozing Pliers";
        description = "After drafting, increase or decrease the value of the drafted die by 1." +
                "1 may not change to 6, or 6 to 1.";
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
            status.getStateHolder().setToolActive(true);
            status.getStateHolder().setActiveToolID(getID());
        }
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
        sb.append("[⚂] ➡ [⚃]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("[⚂] ➡ [⚁]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("[⚀] x [⚅]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }
}
