package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.utility.Parameters;

import static java.lang.Integer.parseInt;

public abstract class AbstractMoveCommand extends AbstractToolCommand {

    int[] coordinates;
    private boolean legalCoordinates;

    AbstractMoveCommand(ConcreteGameStatus status, String cmd, int id) {
        super(status, cmd, id);
        setRegExp("move \\d \\d \\d \\d");
        setLegalPredicate(s -> legalCoordinates && checkCoordinates() && checkRules());
        coordinates = new int[4];
        legalCoordinates = getCoordinates();
    }

    private boolean getCoordinates() {
        if (isValid()) {
            String[] cmd = getCmd().split(" ");
            for (int i = 0; i < 4; i++) {
                coordinates[i] = parseInt(cmd[i + 1]) - 1;
                if (coordinates[i] < 0 || ((i % 2 == 0 && coordinates[i] >= Parameters.MAX_ROWS) ||
                    i % 2 != 0 && coordinates[i] >= Parameters.MAX_COLUMNS))
                    return false;
            }
            return true;
        }
        return false;
    }

    private boolean checkCoordinates() {
        return !getStatus().getTurnManager().getCurrentPlayer().getWindowFrame().isEmpty(
                        coordinates[0], coordinates[1]
                ) &&
                getStatus().getTurnManager().getCurrentPlayer().getWindowFrame().isEmpty(
                        coordinates[2], coordinates[3]
                );
    }

    abstract boolean checkRules();

    @Override
    public void execute() {
        if (super.isLegal()) {
            getStatus().getTurnManager().getCurrentPlayer().getWindowFrame().move(
                    coordinates[0], coordinates[1], coordinates[2], coordinates[3]
            );
        }
    }
}
