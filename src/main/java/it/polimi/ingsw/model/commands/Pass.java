package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.ConcreteGameStatus;

public class Pass extends AbstractCommand {

    public Pass(ConcreteGameStatus status, String cmd) {
        super(status, cmd);
        setRegExp("pass");
        setLegalPredicate(s -> !status.getStateHolder().isToolActive() &&
                status.getStateHolder().getDieHolder() == null);
    }

    @Override
    public void execute() {
        if (isLegal()) {
            getStatus().emptyDicePool();
            getStatus().getTurnManager().advanceTurn();
            getStatus().getStateHolder().clear();
            getStatus().fillDicePool();
        }
    }
}
