package it.polimi.ingsw.model.commands.instructions.pickeddieinstructions;

import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gamedata.GameData;

/**
 * Places the picked die in a free spot on the window frame.
 */
public class PlacePickedDie implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        gameData.getTurnManager().getCurrentPlayer().getWindowFrame()
                .put(gameData.getPickedDie(), args[0], args[1]);
        gameData.setPickedDie(null);
        gameData.setDiePlaced(true);
    }
}
