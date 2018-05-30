package it.polimi.ingsw.model.commands.instructions;

import it.polimi.ingsw.model.gamedata.GameData;

public class PlacePickedDie implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        gameData.getTurnManager().getCurrentPlayer().getWindowFrame()
                .put(gameData.getPickedDie(), args[0], args[1]);
        gameData.setPickedDie(null);
        gameData.setDiePlaced(true);
    }
}
