package it.polimi.ingsw.model.commands.instructions.toolsinstructions;


import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gameboard.cards.ToolCard;
import it.polimi.ingsw.model.gamedata.GameData;

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
        throw new IllegalArgumentException("Something went wrong in tool card activation: this tool does not exist in this game.");
    }
}
