package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.ConcreteGameStatus;

import static java.lang.Integer.parseInt;

public class Pick extends AbstractCommand {

    public Pick(ConcreteGameStatus status, String cmd) {
        super(status, cmd);
        setRegExp("pick \\d");
        setLegalPredicate(s ->
                !status.getStateHolder().isDiePlaced() &&
                        status.getStateHolder().getDieHolder() == null &&
                        !status.getStateHolder().isToolActive() &&
                        parseInt(s[1]) <= status.getDicePool().size() &&
                        parseInt(s[1]) >= 1);
    }

    @Override
    public void execute() {
        if (isLegal()) {
            getStatus().getStateHolder().setDieHolder(
                    getStatus().getDicePool().remove(parseInt(getCmd().split(" ")[1]) - 1)
            );
        }
    }
}
