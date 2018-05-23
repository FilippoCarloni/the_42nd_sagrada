package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.commands.rules.Rule;
import it.polimi.ingsw.model.utility.Parameters;

import static java.lang.Integer.parseInt;

public class Place extends AbstractCommand {

    public Place(ConcreteGameStatus status, String cmd) {
        super(status, cmd);
        setRegExp("place \\d \\d");
        setLegalPredicate(s ->
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
                        ));
    }

    @Override
    public void execute() {
        if (isLegal()) {
            getStatus().getTurnManager().getCurrentPlayer().getWindowFrame().put(
                    getStatus().getStateHolder().getDieHolder(),
                    parseInt(getCmd().split(" ")[1]) - 1,
                    parseInt(getCmd().split(" ")[2]) - 1
            );
            getStatus().getStateHolder().setDiePlaced(true);
            getStatus().getStateHolder().setDieHolder(null);
        }
    }
}
