package it.polimi.ingsw.model.commands.instructions.toolsinstructions;


import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolCard;
import it.polimi.ingsw.model.gamedata.GameData;

import static it.polimi.ingsw.model.utility.ExceptionMessage.OBJECT_NOT_EXISTS;

/**
 * Sets the toolCardActivated state to true and updates the current activeToolIDs.
 */
public class SetToolCardActivated implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        for (ToolCard c : gameData.getTools()) {
            if (c.getID() == args[0] + 1) {
                gameData.setToolActivated(true);
                if (c.isEffectActive())
                    gameData.setActiveToolID(c.getID());
                gameData.setPassiveToolID(c.getID());
                return;
            }
        }
        throw new IllegalArgumentException(OBJECT_NOT_EXISTS);
    }
}
