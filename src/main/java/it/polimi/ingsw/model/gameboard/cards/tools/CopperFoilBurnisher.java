package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.commands.rules.Rule;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Shade;

import java.util.ArrayList;
import java.util.List;

public class CopperFoilBurnisher extends AbstractToolCard {

    private static final int ID = 3;

    CopperFoilBurnisher(ConcreteGameStatus status) {
        super(status, ID);
        name = "Copper Foil Burnisher";
        description = "Move any one die in your window ignoring value restrictions. " +
                "You must obey all other placement restrictions.";
    }

    @Override
    public List<Command> getCommands(String cmd) {
        List<Command> commands = new ArrayList<>();
        commands.add(new MoveIgnoringShade(status, cmd, ID));
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
        sb.append("    → ").append(Shade.LIGHTEST);
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append(" ↑ [ ]").append(Color.YELLOW.paint("[⚄]"));
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append(Color.BLUE.paint("[⚂]")).append(Color.GREEN.paint("[⚃]")).append("[ ]");
        for (int i = 0; i < pixelWidth - 9 - (pixelWidth - 9) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }

    private class MoveIgnoringShade extends AbstractMoveCommand {

        MoveIgnoringShade(ConcreteGameStatus status, String cmd, int id) {
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
            boolean value = Rule.checkExcludeShade(d, getStatus().getTurnManager().getCurrentPlayer().getWindowFrame(),
                    coordinates[2], coordinates[3]
            );
            getStatus().getTurnManager().getCurrentPlayer().getWindowFrame().put(d, coordinates[0], coordinates[1]);
            return value;
        }
    }
}
