package it.polimi.ingsw.model.commands.instructions.toolsinstructions;


import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gamedata.GameData;

public class SetToolCardActivated implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        gameData.setToolActivated(true);
        if (gameData.getTools().get(args[0]).isEffectActive())
            gameData.setActiveToolID(gameData.getTools().get(args[0]).getID());
        gameData.setPassiveToolID(gameData.getTools().get(args[0]).getID());
    }
}
