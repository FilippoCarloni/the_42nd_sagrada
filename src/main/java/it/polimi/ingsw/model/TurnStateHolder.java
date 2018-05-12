package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.dice.Die;

public class TurnStateHolder {
    private Die dieHolder;
    private boolean isToolActive;
    private boolean diePlaced;

    public void clear() {
        dieHolder = null;
        isToolActive = false;
        diePlaced = false;
    }

    public Die getDieHolder() {
        return dieHolder;
    }

    public void setDieHolder(Die dieHolder) {
        this.dieHolder = dieHolder;
    }

    public boolean isToolActive() {
        return isToolActive;
    }

    public void setToolActive(boolean toolActive) {
        isToolActive = toolActive;
    }

    public boolean isDiePlaced() {
        return diePlaced;
    }

    public void setDiePlaced(boolean diePlaced) {
        this.diePlaced = diePlaced;
    }
}
