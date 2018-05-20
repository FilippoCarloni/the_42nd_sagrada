package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.commands.Pass;
import it.polimi.ingsw.model.commands.Pick;
import it.polimi.ingsw.model.commands.Place;
import it.polimi.ingsw.model.commands.rules.Rule;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.utility.Color;
import it.polimi.ingsw.model.gameboard.utility.Parameters;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class TapWheel extends AbstractToolCard {

    private static final int ID = 12;

    TapWheel(ConcreteGameStatus status) {
        super(status, ID);
        name = "Tap Wheel";
        description = "Move up to two dice of the same color that match the color of a die " +
                "on the Round Track.";
    }

    @Override
    public List<Command> getCommands(String cmd) {
        List<Command> commands = new ArrayList<>();
        commands.add(new TapWheelPick(status, cmd));
        commands.add(new TapWheelPlace(status, cmd));
        commands.add(new TapWheelPass(status, cmd));
        commands.add(new MoveUpToTwoDice(status, cmd, ID));
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
        for (int i = 0; i < (pixelWidth - 12) / 2; i++) sb.append(" ");
        sb.append(" ↱    [⚂]").append(Color.PURPLE.paint("[⚃]"));
        for (int i = 0; i < pixelWidth - 12 - (pixelWidth - 12) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 12) / 2; i++) sb.append(" ");
        sb.append(Color.RED.paint("[⚂]")).append(Color.GREEN.paint("[⚄]")).append(Color.YELLOW.paint("[⚃]")).append("[ ]");
        for (int i = 0; i < pixelWidth - 12 - (pixelWidth - 12) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 12) / 2; i++) sb.append(" ");
        sb.append("[ ]").append(Color.RED.paint("[⚄]")).append(" → [ ]");
        for (int i = 0; i < pixelWidth - 12 - (pixelWidth - 12) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }

    private class TapWheelPick extends Pick {

        TapWheelPick(ConcreteGameStatus status, String cmd) {
            super(status, cmd);
            setLegalPredicate(s ->
                    !status.getStateHolder().isDiePlaced() &&
                            status.getStateHolder().getDieHolder() == null &&
                            status.getStateHolder().getActiveToolID() == ID &&
                            parseInt(s[1]) <= status.getDicePool().size() &&
                            parseInt(s[1]) >= 1);
        }

        @Override
        public void execute() {
            if (super.isLegal()) {
                super.execute();
                tearDown();
            }
        }
    }

    private class TapWheelPlace extends Place {

        TapWheelPlace(ConcreteGameStatus status, String cmd) {
            super(status, cmd);
            setLegalPredicate(s ->
                    status.getStateHolder().getDieHolder() != null &&
                            status.getStateHolder().getActiveToolID() == ID &&
                            parseInt(s[1]) <= Parameters.MAX_ROWS &&
                            parseInt(s[1]) >= 1 &&
                            parseInt(s[2]) <= Parameters.MAX_COLUMNS &&
                            parseInt(s[2]) >= 1 &&
                            Rule.checkAllRules(
                                    status.getStateHolder().getDieHolder(),
                                    status.getTurnManager().getCurrentPlayer().getWindowFrame(),
                                    parseInt(cmd.split(" ")[1]) - 1,
                                    parseInt(cmd.split(" ")[2]) - 1
                            ));
        }

        @Override
        public void execute() {
            if (super.isLegal()) {
                super.execute();
                tearDown();
            }
        }
    }

    private class TapWheelPass extends Pass {

        TapWheelPass(ConcreteGameStatus status, String cmd) {
            super(status, cmd);
            setLegalPredicate(s -> status.getStateHolder().getActiveToolID() == ID &&
                    status.getStateHolder().getDieHolder() == null);
        }

        @Override
        public void execute() {
            if (super.isLegal()) {
                super.execute();
                tearDown();
            }
        }
    }

    private class MoveUpToTwoDice extends AbstractMoveCommand {

        MoveUpToTwoDice(ConcreteGameStatus status, String cmd, int id) {
            super(status, cmd, id);
        }

        @Override
        public boolean isLegal() {
            if (super.isLegal()) {
                Color c = status.getTurnManager().getCurrentPlayer().getWindowFrame().getDie(coordinates[0], coordinates[1]).getColor();
                if (status.getStateHolder().getTapWheelColor() == null) {
                    for (Die d : status.getRoundTrack())
                        if (d.getColor().equals(c))
                            return true;
                    return false;
                } else {
                    return status.getStateHolder().getTapWheelColor().equals(c);
                }
            }
            return false;
        }

        @Override
        public void execute() {
            if (super.isLegal()) {
                if (status.getStateHolder().getTapWheelColor() == null) {
                    status.getStateHolder().setTapWheelColor(
                            status.getTurnManager().getCurrentPlayer().getWindowFrame().getDie(
                                    coordinates[0], coordinates[1]).getColor()
                    );
                    super.execute();
                } else {
                    status.getStateHolder().setTapWheelColor(null);
                    super.execute();
                    tearDown();
                }
            }
        }

        @Override
        boolean checkRules() {
            Die d = getStatus().getTurnManager().getCurrentPlayer().getWindowFrame().pick(
                    coordinates[0], coordinates[1]
            );
            boolean value = Rule.checkAllRules(d, getStatus().getTurnManager().getCurrentPlayer().getWindowFrame(),
                    coordinates[2], coordinates[3]
            );
            getStatus().getTurnManager().getCurrentPlayer().getWindowFrame().put(d, coordinates[0], coordinates[1]);
            return value;
        }
    }
}
