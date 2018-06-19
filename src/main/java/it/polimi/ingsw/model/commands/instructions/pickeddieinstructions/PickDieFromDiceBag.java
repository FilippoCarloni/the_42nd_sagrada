package it.polimi.ingsw.model.commands.instructions.pickeddieinstructions;

import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gamedata.GameData;

/**
 * Picks a single die from the Dice Bag.
 */
public class PickDieFromDiceBag implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        gameData.setPickedDie(gameData.getDiceBag().pick());
    }
}
