package it.polimi.ingsw.model.commands.instructions.pickeddieinstructions;

import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gamedata.GameData;

public class ReturnDieToDiceBag implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        gameData.getDiceBag().insert(gameData.getPickedDie());
        gameData.setPickedDie(null);
    }
}
