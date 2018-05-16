package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.ConcreteGameStatus;

public class Pass extends AbstractCommand {

    Pass(ConcreteGameStatus status, String cmd) {
        super(status, cmd);
        setRegExp("pass");
        setLegalPredicate(s -> !status.getStateHolder().isToolActive() &&
                (status.getStateHolder().getDieHolder() == null ||
                 status.getStateHolder().getActiveToolID() == 6));
    }

    @Override
    public void execute() {
        if (isLegal()) {
            if (getStatus().getStateHolder().getActiveToolID() == 6)
                getStatus().getDicePool().add(getStatus().getStateHolder().getDieHolder());
            getStatus().emptyDicePool();
            getStatus().getTurnManager().advanceTurn();
            getStatus().getStateHolder().clear();
            getStatus().fillDicePool();
        }
    }
}
