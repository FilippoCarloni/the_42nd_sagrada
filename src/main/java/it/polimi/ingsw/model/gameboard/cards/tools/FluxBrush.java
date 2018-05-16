package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.gameboard.utility.Color;

import java.util.ArrayList;
import java.util.List;

public class FluxBrush extends AbstractToolCard {

    private static final int ID = 6;

    FluxBrush(ConcreteGameStatus status) {
        super(status, ID);
        name = "Flux Brush";
        description = "After drafting, re-roll the drafted die. " +
                "If it cannot be placed, return it to the Draft Pool.";
    }

    @Override
    public boolean isLegal() {
        return super.isLegal() && status.getStateHolder().getDieHolder() != null;
    }

    @Override
    public void execute() {
        if (isLegal()) {
            super.execute();
            status.getStateHolder().getDieHolder().roll();
            tearDown();
            status.getStateHolder().setActiveToolID(ID);
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
        sb.append("         ");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("(( ").append(Color.BLUE.paint("[⚃]")).append(" ))");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("         ");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }
}
