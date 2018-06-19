package it.polimi.ingsw.model.commands.instructions.gamedatainstructions;

import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gamedata.GameData;

/**
 * Rolls all the dice currently present in the Dice Pool
 */
public class ReRollPool implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        for (Die d : gameData.getDicePool())
            d.roll();
    }
}
