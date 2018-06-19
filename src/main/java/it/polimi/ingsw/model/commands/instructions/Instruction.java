package it.polimi.ingsw.model.commands.instructions;

import it.polimi.ingsw.model.gamedata.GameData;

/**
 * Represents a generic instruction that can be performed on a game status to alter its internal data.
 */
@FunctionalInterface
public interface Instruction {

    /**
     * Performs a specific instruction on a particular game state.
     * @param gameData The GameData that corresponds to the current game state
     * @param args The arguments provided to the Instruction
     */
    void perform(GameData gameData, int[] args);
}
