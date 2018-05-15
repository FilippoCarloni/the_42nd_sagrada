package it.polimi.ingsw.model.commands;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.utility.Shade;

public class Decrease extends AbstractCommand {

    Decrease(ConcreteGameStatus status, String cmd) {
        super(status, cmd);
        regExp = "decrease";
        legalPredicate = s -> status.getStateHolder().isToolActive() &&
                status.getStateHolder().getActiveToolID() == 1 &&
                !status.getStateHolder().getDieHolder().getShade().equals(Shade.LIGHTEST);
    }

    @Override
    public void execute() {
        if (isLegal()) {
            Die d = status.getStateHolder().getDieHolder();
            d.setShade(Shade.findByValue(d.getShade().getValue() - 1));
            status.getStateHolder().setDieHolder(d);
            status.getStateHolder().setToolActive(false);
        }
    }
}
