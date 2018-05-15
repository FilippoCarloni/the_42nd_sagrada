package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.ConcreteGameStatus;

public class Pass extends AbstractCommand {

    Pass(ConcreteGameStatus status, String cmd) {
        super(status, cmd);
        regExp = "pass";
        legalPredicate = s ->
                status.getStateHolder().getDieHolder() == null &&
                !status.getStateHolder().isToolActive();
    }

    @Override
    public void execute() {
        if (isLegal()) {
            status.emptyDicePool();
            status.getTurnManager().advanceTurn();
            status.getStateHolder().clear();
            status.fillDicePool();
        }
    }
}