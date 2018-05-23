package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.utility.Shade;

import java.util.ArrayList;
import java.util.List;

public class GrozingPliers extends AbstractToolCard {

    private static final int ID = 1;

    GrozingPliers(ConcreteGameStatus status) {
        super(status, ID);
        name = "Grozing Pliers";
        description = "After drafting, increase or decrease the value of the drafted die by 1. " +
                "1 may not change to 6, or 6 to 1.";
    }

    @Override
    public boolean isLegal() {
        return super.isLegal() && status.getStateHolder().getDieHolder() != null;
    }

    @Override
    public List<Command> getCommands(String cmd) {
        List<Command> commands = new ArrayList<>();
        commands.add(new Increase(status, cmd, ID));
        commands.add(new Decrease(status, cmd, ID));
        return commands;
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

    private class Increase extends AbstractToolCommand {

        Increase(ConcreteGameStatus status, String cmd, int id) {
            super(status, cmd, id);
            setRegExp("increase");
            setLegalPredicate(s -> getStatus().getStateHolder().getDieHolder().getShade() != Shade.DARKEST);
        }

        @Override
        public void execute() {
            if (super.isLegal()) {
                status.getStateHolder().getDieHolder().setShade(
                        Shade.findByValue(status.getStateHolder().getDieHolder().getShade().getValue() + 1)
                );
                assert status.getStateHolder().getDieHolder().getShade() != null;
                tearDown();
            }
        }
    }

    private class Decrease extends AbstractToolCommand {

        Decrease(ConcreteGameStatus status, String cmd, int id) {
            super(status, cmd, id);
            setRegExp("decrease");
            setLegalPredicate(s -> getStatus().getStateHolder().getDieHolder().getShade() != Shade.LIGHTEST);
        }

        @Override
        public void execute() {
            if (super.isLegal()) {
                status.getStateHolder().getDieHolder().setShade(
                        Shade.findByValue(status.getStateHolder().getDieHolder().getShade().getValue() - 1)
                );
                assert status.getStateHolder().getDieHolder().getShade() != null;
                tearDown();
            }
        }
    }
}
