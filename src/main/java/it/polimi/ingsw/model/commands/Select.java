package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.gameboard.dice.Die;

import static java.lang.Integer.parseInt;

public class Select extends AbstractCommand {

    Select(ConcreteGameStatus status, String cmd) {
        super(status, cmd);
        regExp = "select \\d";
        legalPredicate = s -> status.getStateHolder().isToolActive() &&
                selectionAvailable();
    }

    private boolean selectionAvailable() {
        switch (status.getStateHolder().getActiveToolID()) {
            case 5:
                int size = 0;
                int index = parseInt(cmd.split(" ")[1]) - 1;
                for (Die d : status.getRoundTrack())
                    size++;
                return size > 0 && index >= 0 && index < size;
        }
        return false;
    }

    @Override
    public void execute() {
        if (isLegal()) {
            int index = parseInt(cmd.split(" ")[1]) - 1;
            int i = 0;
            for (Die d : status.getRoundTrack()) {
                if (i == index) {
                    status.getRoundTrack().swap(
                            status.getStateHolder().getDieHolder(), d
                    );
                    status.getStateHolder().setDieHolder(d);
                    return;
                }
                i++;
            }
        }
    }
}
