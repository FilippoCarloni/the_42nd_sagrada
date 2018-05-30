package it.polimi.ingsw.model.commands.instructions;

import it.polimi.ingsw.model.gamedata.GameData;

public class AdvanceGame implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        gameData.advance();
    }
}
