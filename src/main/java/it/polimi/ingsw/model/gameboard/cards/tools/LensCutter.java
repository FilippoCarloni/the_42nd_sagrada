package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.gameboard.utility.Color;

public class LensCutter extends AbstractToolCard {

    LensCutter(ConcreteGameStatus status) {
        super(status, 5);
        name = "Lens Cutter";
        description = "After drafting, swap the drafted die with a die form the Round Track.";
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
        sb.append("[5]").append(Color.YELLOW.paint("[⚂]")).append("[7]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("    ⬍    ");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append(Color.BLUE.paint("   [⚃]   "));
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }
}
