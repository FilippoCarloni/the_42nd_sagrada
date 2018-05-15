package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.commands.rules.Rule;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.utility.Parameters;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

import static java.lang.Integer.parseInt;

public class Move extends AbstractCommand {

    Move(ConcreteGameStatus status, String cmd) {
        super(status, cmd);
        regExp = "move \\d \\d \\d \\d";
        legalPredicate = s -> status.getStateHolder().isToolActive() && getMovementConstraint();
    }

    private boolean getMovementConstraint() {
        int[] c = readCoordinates();
        if (!areCoordinatesLegal()) return false;
        WindowFrame w = status.getTurnManager().getCurrentPlayer().getWindowFrame();
        Die d = w.pick(c[0], c[1]);
        if (d == null) return false;
        boolean value;
        switch (status.getStateHolder().getActiveToolID()) {
            case 2:
                value =  Rule.checkExcludeColor(d, w, c[2], c[3]);
                w.put(d, c[0], c[1]);
                return value;
            case 3:
                value =  Rule.checkExcludeShade(d, w, c[2], c[3]);
                w.put(d, c[0], c[1]);
                return value;
            case 4:
                value = Rule.checkAllRules(d, w, c[2], c[3]);
                w.put(d, c[0], c[1]);
                return value;
        }
        return false;
    }

    private boolean areCoordinatesLegal() {
        int[] c = readCoordinates();
        for (int i = 0; i < c.length; i++) {
            if (i % 2 == 0) {
                if (c[i] < 0 || c[i] >= Parameters.MAX_ROWS) return false;
            } else {
                if (c[i] < 0 || c[i] >= Parameters.MAX_COLUMNS) return false;
            }
        }
        WindowFrame w = status.getTurnManager().getCurrentPlayer().getWindowFrame();
        return !(w.isEmpty(c[0], c[1])) && w.isEmpty(c[2], c[3]);
    }

    private int[] readCoordinates() {
        if (isValid()) {
            int[] coordinates = new int[4];
            String[] parsedCmd = cmd.split(" ");
            coordinates[0] = parseInt(parsedCmd[1]) - 1;
            coordinates[1] = parseInt(parsedCmd[2]) - 1;
            coordinates[2] = parseInt(parsedCmd[3]) - 1;
            coordinates[3] = parseInt(parsedCmd[4]) - 1;
            return coordinates;
        }
        return new int[4];
    }

    @Override
    public void execute() {
        if (isLegal()) {
            int[] c = readCoordinates();
            status.getTurnManager().getCurrentPlayer().getWindowFrame().move(
                   c[0], c[1], c[2], c[3]
            );

            status.getStateHolder().setDieAlreadyMoved(
                    !status.getStateHolder().isDieAlreadyMoved()
            );

            if (status.getStateHolder().getActiveToolID() != 4 ||
                    (
                            status.getStateHolder().getActiveToolID() == 4 &&
                            !status.getStateHolder().isDieAlreadyMoved())
                    )
                status.getStateHolder().setToolActive(false);
        }
    }
}
