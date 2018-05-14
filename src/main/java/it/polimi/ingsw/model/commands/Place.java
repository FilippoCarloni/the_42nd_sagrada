package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.commands.rules.Rule;
import it.polimi.ingsw.model.gameboard.utility.Parameters;

import static java.lang.Integer.parseInt;

public class Place extends AbstractCommand {

    Place(ConcreteGameStatus status, String cmd) {
        super(status, cmd);
        regExp = "place \\d \\d";
        legalPredicate = s ->
                status.getStateHolder().getDieHolder() != null &&
                        !status.getStateHolder().isToolActive() &&
                        parseInt(s[1]) <= Parameters.MAX_ROWS &&
                        parseInt(s[1]) >= 1 &&
                        parseInt(s[2]) <= Parameters.MAX_COLUMNS &&
                        parseInt(s[2]) >= 1 &&
                        Rule.checkAllRules(
                                status.getStateHolder().getDieHolder(),
                                status.getTurnManager().getCurrentPlayer().getWindowFrame(),
                                parseInt(cmd.split(" ")[1]) - 1,
                                parseInt(cmd.split(" ")[2]) - 1
                        );
    }

    @Override
    public void execute() {
        if (isLegal()) {
            status.getTurnManager().getCurrentPlayer().getWindowFrame().put(
                    status.getStateHolder().getDieHolder(),
                    parseInt(cmd.split(" ")[1]) - 1,
                    parseInt(cmd.split(" ")[2]) - 1
            );
            status.getStateHolder().setDiePlaced(true);
            status.getStateHolder().setDieHolder(null);
        }
    }
}
