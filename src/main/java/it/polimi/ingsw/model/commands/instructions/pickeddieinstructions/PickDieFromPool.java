package it.polimi.ingsw.model.commands.instructions.pickeddieinstructions;

import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gamedata.GameData;

/**
 * Picks the selected die from the Dice Pool
 */
public class PickDieFromPool implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        gameData.setPickedDie(gameData.getDicePool().remove(args[0]));
    }
}
