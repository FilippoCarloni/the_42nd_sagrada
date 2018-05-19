package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.gameboard.utility.Color;

import java.util.ArrayList;
import java.util.List;

public class RunningPliers extends AbstractToolCard {

    private static final int ID = 8;

    RunningPliers(ConcreteGameStatus status) {
        super(status, ID);
        name = "Running Pliers";
        description = "After your first turn, immediately draft a die. Skip your next turn this round.";
    }

    @Override
    public boolean isLegal() {
        return super.isLegal() && !status.getTurnManager().isSecondTurn();
    }

    @Override
    public void execute() {
        if (isLegal()) {
            super.execute();
            status.getTurnManager().takeTwoTurns();
            tearDown();
        }
    }

    @Override
    public List<Command> getCommands(String cmd) {
        return new ArrayList<>();
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
        sb.append("   ").append(Color.PURPLE.paint("[⚀]")).append(" → ");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append(Color.BLUE.paint("[⚄]")).append("      ");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("   ").append(Color.RED.paint("[⚃]")).append(" → ");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }
}
