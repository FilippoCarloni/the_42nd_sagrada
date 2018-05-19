package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.utility.Color;

import java.util.ArrayList;
import java.util.List;

public class GlazingHammer extends AbstractToolCard {

    private static final int ID = 7;

    GlazingHammer(ConcreteGameStatus status) {
        super(status, ID);
        name = "Glazing Hammer";
        description = "Re-roll all dice in the Draft Pool. This may only be used " +
                "on your second turn before drafting.";
    }

    @Override
    public boolean isLegal() {
        return super.isLegal() && status.getTurnManager().isSecondTurn();
    }

    @Override
    public void execute() {
        if (isLegal()) {
            super.execute();
            for (Die d : status.getDicePool())
                d.roll();
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
        sb.append("(( ").append(Color.YELLOW.paint("[⚃]")).append(" ))");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("(( ").append(Color.GREEN.paint("[⚄]")).append(" ))");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("(( ").append(Color.PURPLE.paint("[⚃]")).append(" ))");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }
}
