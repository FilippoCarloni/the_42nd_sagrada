package it.polimi.ingsw.model.gameboard.cards.tools;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.gameboard.cards.AbstractCard;
import it.polimi.ingsw.model.gameboard.cards.ToolCard;

public abstract class AbstractToolCard extends AbstractCard implements ToolCard {

    private int id;
    ConcreteGameStatus status;
    private int favorPoints;

    AbstractToolCard(ConcreteGameStatus status, int id) {
        this.id = id;
        favorPoints = 0;
        this.status = status;
    }

    private boolean toolCheck() {
        return !status.getStateHolder().isToolActive() && !status.getStateHolder().isToolUsed();
    }

    private boolean favorPointsCheck() {
        return getFavorPoints() > 0 ?
                status.getTurnManager().getCurrentPlayer().getFavorPoints() > 1 :
                status.getTurnManager().getCurrentPlayer().getFavorPoints() >= 1;
    }

    private void takePointsFromPlayer() {
        status.getTurnManager().getCurrentPlayer().setFavorPoints(
                getFavorPoints() > 0 ?
                        status.getTurnManager().getCurrentPlayer().getFavorPoints() - 2 :
                        status.getTurnManager().getCurrentPlayer().getFavorPoints() - 1
        );
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public int getFavorPoints() {
        return favorPoints;
    }

    @Override
    public void addFavorPoints() {
        favorPoints = favorPoints > 0 ? favorPoints + 2 : favorPoints + 1;
    }

    @Override
    public boolean isLegal() {
        return toolCheck() && favorPointsCheck();
    }

    @Override
    public void execute() {
        if (isLegal()) {
            status.getStateHolder().setToolUsed(true);
            takePointsFromPlayer();
            addFavorPoints();
            status.getStateHolder().setToolActive(true);
            status.getStateHolder().setActiveToolID(getID());
        }
    }

    void tearDown() {
        status.getStateHolder().setToolActive(false);
        status.getStateHolder().setActiveToolID(0);
    }
}
