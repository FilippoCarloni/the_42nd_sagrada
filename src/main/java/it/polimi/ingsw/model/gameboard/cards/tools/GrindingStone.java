package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Shade;

import java.util.ArrayList;
import java.util.List;

public class GrindingStone extends AbstractToolCard {

    private static final int ID = 10;

    GrindingStone(ConcreteGameStatus status) {
        super(status, ID);
        name = "Grinding Stone";
        description = "After drafting, flip the die to its opposite side " +
                "(6 flips to 1, 5 to 2, 4 to 3, etc.)";
    }

    @Override
    public boolean isLegal() {
        return super.isLegal() && status.getStateHolder().getDieHolder() != null;
    }

    @Override
    public void execute() {
        if (isLegal()) {
            super.execute();
            flip(status.getStateHolder().getDieHolder());
            tearDown();
        }
    }

    private void flip(Die die) {
        assert die != null && die.getShade() != null;
        Shade[] values = Shade.values();
        int index = -1;
        for (int i = 0; i < values.length && index < 0; i++)
            if (die.getShade().equals(values[i]))
                index = i;
        die.setShade(values[values.length - 1 - index]);
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
        sb.append("[⚅] ⬌ [⚀]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("[⚄] ⬌ [⚁]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("[⚃] ⬌ [⚂]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }
}
