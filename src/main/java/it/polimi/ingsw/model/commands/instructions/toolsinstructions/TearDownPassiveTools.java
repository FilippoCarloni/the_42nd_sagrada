package it.polimi.ingsw.model.commands.instructions.toolsinstructions;

import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gamedata.GameData;

/**
 * Ends the effect of a passive Tool Card.
 * This effect can be terminated only by a call to a generic command (pick, place, pass).
 */
public class TearDownPassiveTools implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        gameData.setPassiveToolID(0);
        if (gameData.getPickedDie() != null) {
            gameData.getDicePool().add(gameData.getPickedDie());
            gameData.setPickedDie(null);
        }
    }
}
