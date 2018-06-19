package it.polimi.ingsw.model.commands.instructions.gamedatainstructions;

import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gamedata.GameData;

/**
 * The current player will take two turns in a row.
 * A player can only take two turns per round.
 */
public class TakeTwoTurns implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        gameData.getTurnManager().takeTwoTurns();
    }
}
