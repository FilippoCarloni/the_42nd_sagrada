package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.commands.AbstractCommand;

public abstract class AbstractToolCommand extends AbstractCommand {

    private int id;

    AbstractToolCommand(ConcreteGameStatus status, String string, int id) {
        super(status, string);
        this.id = id;
    }

    @Override
    public boolean isLegal() {
        return super.isLegal() &&
                getStatus().getStateHolder().isToolActive() &&
                getStatus().getStateHolder().getActiveToolID() == id;
    }
}
