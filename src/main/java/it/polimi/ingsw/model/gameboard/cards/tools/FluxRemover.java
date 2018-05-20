package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.commands.AbstractCommand;
import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.gameboard.utility.Color;
import it.polimi.ingsw.model.gameboard.utility.Shade;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class FluxRemover extends AbstractToolCard {

    private static final int ID = 11;

    FluxRemover(ConcreteGameStatus status) {
        super(status, ID);
        name = "Flux Remover";
        description = "After drafting, return the die to the Dice Bag and pull 1 die from the bag. " +
                "Choose a value and place the new die or pass.";
    }

    @Override
    public boolean isLegal() {
        return super.isLegal() && status.getStateHolder().getDieHolder() != null;
    }

    @Override
    public List<Command> getCommands(String cmd) {
        List<Command> commands = new ArrayList<>();
        commands.add(new SelectShade(status, cmd, ID));
        commands.add(new PassWithoutPlace(status, cmd));
        return commands;
    }

    @Override
    public void execute() {
        if (isLegal()) {
            super.execute();
            status.getDiceBag().insert(status.getStateHolder().getDieHolder());
            status.getStateHolder().setDieHolder(status.getDiceBag().pick());
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
        sb.append("         ");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append(" ⬅ ").append(Color.BLUE.paint("[⚂]")).append("   ");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("   ").append(Color.YELLOW.paint("[⚀]")).append(" ➡ ");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }

    private class SelectShade extends AbstractToolCommand {

        SelectShade(ConcreteGameStatus status, String cmd, int id) {
            super(status, cmd, id);
            setRegExp("select \\d");
            setLegalPredicate(s -> getStatus().getStateHolder().getDieHolder() != null &&
                    getIndex() >= minShade() && getIndex() <= maxShade());
        }

        private int getIndex() {
            return parseInt(getCmd().split(" ")[1]);
        }

        private int minShade() {
            int min = 7;
            for (int i = 0; i < Shade.values().length; i++)
                if (Shade.values()[i].getValue() < min)
                    min = Shade.values()[i].getValue();
            return min;
        }

        private int maxShade() {
            int max = 0;
            for (int i = 0; i < Shade.values().length; i++)
                if (Shade.values()[i].getValue() > max)
                    max = Shade.values()[i].getValue();
            return max;
        }

        @Override
        public void execute() {
            if (super.isLegal()) {
                status.getStateHolder().getDieHolder().setShade(Shade.findByValue(getIndex()));
                status.getStateHolder().setFluxRemoverChoice(true);
                tearDown();
            }
        }
    }

    private class PassWithoutPlace extends AbstractCommand {

        PassWithoutPlace(ConcreteGameStatus status, String cmd) {
            super(status, cmd);
            setRegExp("pass");
            setLegalPredicate(s -> status.getStateHolder().getDieHolder() != null &&
                    status.getStateHolder().isFluxRemoverChoice());
        }

        @Override
        public void execute() {
            if (super.isLegal()) {
                getStatus().getDicePool().add(
                        getStatus().getStateHolder().getDieHolder()
                );
                assert !getStatus().getDicePool().contains(null);
                getStatus().emptyDicePool();
                getStatus().getTurnManager().advanceTurn();
                getStatus().getStateHolder().clear();
                getStatus().fillDicePool();
            }
        }
    }
}
