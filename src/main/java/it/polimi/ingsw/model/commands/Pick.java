package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.ConcreteGameStatus;

import static java.lang.Integer.parseInt;

public class Pick extends AbstractCommand {

    Pick(ConcreteGameStatus status, String cmd) {
        super(status, cmd);
        regExp = "pick \\d";
        legalPredicate = s ->
                !status.getTurnStateHolder().isDiePlaced() &&
                status.getTurnStateHolder().getDieHolder() == null &&
                !status.getTurnStateHolder().isToolActive() &&
                parseInt(s[1]) <= status.getDicePool().size() &&
                parseInt(s[1]) >= 1;
    }

    @Override
    public void execute() {
        if (isLegal()) {
            status.getTurnStateHolder().setDieHolder(
                    status.getDicePool().remove(parseInt(cmd.split(" ")[1]) - 1)
            );
        }
    }
}
