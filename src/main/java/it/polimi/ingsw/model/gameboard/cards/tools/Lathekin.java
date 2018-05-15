package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.gameboard.utility.Color;

public class Lathekin extends AbstractToolCard {

    Lathekin(ConcreteGameStatus status) {
        super(status, 4);
        name = "Lathekin";
        description = "Move exactly two dice, obeying all placement restrictions.";
    }

    @Override
    public boolean isLegal() {
        return toolCheck() && favorPointsCheck();
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
        sb.append(" ↱    [⚂]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append(Color.RED.paint("[⚂]")).append(Color.GREEN.paint("[⚄]")).append(Color.YELLOW.paint("[⚃]"));
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append(Color.BLUE.paint("[⚄]")).append(" → [ ]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }
}
