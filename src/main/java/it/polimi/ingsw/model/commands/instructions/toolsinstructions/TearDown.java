package it.polimi.ingsw.model.commands.instructions.toolsinstructions;

import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gamedata.GameData;

public class TearDown implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        gameData.setActiveToolID(0);
        gameData.setPassiveToolID(0);
    }
}
