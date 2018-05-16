package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.dice.Die;

public final class StateHolder {

    private Die dieHolder;
    private boolean toolActive;
    private boolean toolUsed;
    private boolean diePlaced;
    private int activeToolID;
    private Die dieAlreadyMoved;
    private boolean fluxBrushRoll;

    StateHolder() {
        clear();
    }

    public void clear() {
        dieHolder = null;
        toolActive = false;
        toolUsed = false;
        diePlaced = false;
        activeToolID = 0;
        dieAlreadyMoved = null;
        fluxBrushRoll = false;
    }

    public Die getDieHolder() {
        return dieHolder;
    }

    public void setDieHolder(Die dieHolder) {
        this.dieHolder = dieHolder;
    }

    public boolean isToolActive() {
        return toolActive;
    }

    public void setToolActive(boolean toolActive) {
        this.toolActive = toolActive;
    }

    public boolean isToolUsed() {
        return toolUsed;
    }

    public void setToolUsed(boolean toolUsed) {
        this.toolUsed = toolUsed;
    }

    public boolean isDiePlaced() {
        return diePlaced;
    }

    public void setDiePlaced(boolean diePlaced) {
        this.diePlaced = diePlaced;
    }

    public int getActiveToolID() {
        return activeToolID;
    }

    public void setActiveToolID(int activeToolID) {
        this.activeToolID = activeToolID;
    }

    public Die getDieAlreadyMoved() {
        return dieAlreadyMoved;
    }

    public void setDieAlreadyMoved(Die dieAlreadyMoved) {
        this.dieAlreadyMoved = dieAlreadyMoved;
    }

    public boolean isFluxBrushRoll() {
        return fluxBrushRoll;
    }

    public void setFluxBrushRoll(boolean fluxBrushRoll) {
        this.fluxBrushRoll = fluxBrushRoll;
    }
}
