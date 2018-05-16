package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.commands.Command;
import it.polimi.ingsw.model.commands.rules.Rule;
import it.polimi.ingsw.model.gameboard.utility.Color;
import it.polimi.ingsw.model.gameboard.utility.Parameters;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class CorkBackedStraightedge extends AbstractToolCard {

    private static final int ID = 9;

    CorkBackedStraightedge(ConcreteGameStatus status) {
        super(status, ID);
        name = "Cork-backed Straightedge";
        description = "After drafting, place the die in a spot that is not adjacent to another die. " +
                "You must obey all other placement restrictions.";
    }

    @Override
    public boolean isLegal() {
        return super.isLegal() && status.getStateHolder().getDieHolder() != null;
    }

    @Override
    public List<Command> getCommands(String cmd) {
        List<Command> commands = new ArrayList<>();
        commands.add(new PlaceIgnoringDistance(status, cmd, ID));
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
        sb.append("[ ][ ]").append(Color.YELLOW.paint("[⚃]")).append("[ ]");
        for (int i = 0; i < pixelWidth - 12 - (pixelWidth - 12) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 12) / 2; i++) sb.append(" ");
        sb.append("[ ][ ]").append(Color.GREEN.paint("[⚂]")).append(Color.PURPLE.paint("[⚃]"));
        for (int i = 0; i < pixelWidth - 12 - (pixelWidth - 12) / 2; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < (pixelWidth - 12) / 2; i++) sb.append(" ");
        sb.append(Color.PURPLE.paint("[⚂]")).append(" ⬅ [ ][ ]");
        for (int i = 0; i < pixelWidth - 12 - (pixelWidth - 12) / 2; i++) sb.append(" ");
        sb.append("|");
        sb.append(getLowerCard());
        return sb.toString();
    }

    private class PlaceIgnoringDistance extends AbstractToolCommand {

        private int[] coordinates;
        private boolean legalCoordinates;

        PlaceIgnoringDistance(ConcreteGameStatus status, String cmd, int id) {
            super(status, cmd, id);
            setRegExp("place \\d \\d");
            coordinates = new int[2];
            setLegalPredicate(s -> status.getStateHolder().getDieHolder() != null &&
                    legalCoordinates && checkCoordinates() && checkRules());
            legalCoordinates = getCoordinates();
        }

        private boolean getCoordinates() {
            if (isValid()) {
                String[] cmd = getCmd().split(" ");
                if (parseInt(cmd[1]) <= 0 || parseInt(cmd[1]) > Parameters.MAX_ROWS)
                    return false;
                if (parseInt(cmd[2]) <= 0 || parseInt(cmd[2]) > Parameters.MAX_COLUMNS)
                    return false;
                coordinates[0] = parseInt(cmd[1]) - 1;
                coordinates[1] = parseInt(cmd[2]) - 1;
                return true;
            }
            return false;
        }

        private boolean checkCoordinates() {
            return getStatus().getTurnManager().getCurrentPlayer().getWindowFrame().isEmpty(
                    coordinates[0], coordinates[1]
            );
        }

        private boolean checkRules() {
            return Rule.checkExcludePlacing(
                    status.getStateHolder().getDieHolder(),
                    status.getTurnManager().getCurrentPlayer().getWindowFrame(),
                    coordinates[0],
                    coordinates[1]
            );
        }

        @Override
        public void execute() {
            if (super.isLegal()) {
                status.getTurnManager().getCurrentPlayer().getWindowFrame().put(
                        status.getStateHolder().getDieHolder(),
                        coordinates[0],
                        coordinates[1]
                );
                assert status.getTurnManager().getCurrentPlayer().getWindowFrame().getDie(coordinates[0], coordinates[1]) != null;
                getStatus().getStateHolder().setDiePlaced(true);
                getStatus().getStateHolder().setDieHolder(null);
                tearDown();
            }
        }
    }
}
