package it.polimi.ingsw.model.commands.instructions.gamedatainstructions;

import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gamedata.GameData;

public class TakeTwoTurns implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        gameData.getTurnManager().takeTwoTurns();
    }
}
