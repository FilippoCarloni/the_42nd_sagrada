package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.commands.rules.Rule;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.utility.Color;

import java.util.ArrayList;
import java.util.List;

public class EglomiseBrush extends AbstractToolCard {

    private static final int ID = 2;

    EglomiseBrush(ConcreteGameStatus status) {
        super(status, ID);
        name = "Eglomise Brush";
        description = "Move any one die in your window ignoring color restrictions. " +
                "You must obey all other placement restrictions.";
    }

    @Override
    public List<Command> getCommands(String cmd) {
        List<Command> commands = new ArrayList<>();
        commands.add(new EglomiseBrush.MoveIgnoringColor(status, cmd, ID));
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
        sb.append("    → ").append(Color.BLUE);
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append(" ↑ [ ]").append(Color.YELLOW.paint("[⚄]"));
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append(Color.RED.paint("[⚂]")).append(Color.GREEN.paint("[⚃]")).append("[ ]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }

    private class MoveIgnoringColor extends AbstractMoveCommand {

        MoveIgnoringColor(ConcreteGameStatus status, String cmd, int id) {
            super(status, cmd, id);
        }

        @Override
        public void execute() {
            if (super.isLegal()) {
                super.execute();
                tearDown();
            }
        }

        @Override
        boolean checkRules() {
            Die d = getStatus().getTurnManager().getCurrentPlayer().getWindowFrame().pick(
                    coordinates[0], coordinates[1]
            );
            boolean value = Rule.checkExcludeColor(d, getStatus().getTurnManager().getCurrentPlayer().getWindowFrame(),
                    coordinates[2], coordinates[3]
            );
            getStatus().getTurnManager().getCurrentPlayer().getWindowFrame().put(d, coordinates[0], coordinates[1]);
            return value;
        }
    }
}
