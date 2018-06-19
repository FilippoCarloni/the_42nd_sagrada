package it.polimi.ingsw.model.commands.instructions.gamedatainstructions;

import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gamedata.GameData;

/**
 * Advances the status of the game:
 * <ul>
 *     <li>resets the state to a new turn</li>
 *     <li>updates the current player</li>
 * </ul>
 */
public class AdvanceGame implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        gameData.advance();
    }
}
