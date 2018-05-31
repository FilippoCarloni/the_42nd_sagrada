package it.polimi.ingsw.model.commands.instructions.toolsinstructions;

import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gamedata.GameData;

public class ManageFavorPoints implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        if (gameData.getTools().get(args[0]).getFavorPoints() == 0)
            gameData.getTurnManager().getCurrentPlayer().setFavorPoints(
                    gameData.getTurnManager().getCurrentPlayer().getFavorPoints() - 1
            );
        else
            gameData.getTurnManager().getCurrentPlayer().setFavorPoints(
                    gameData.getTurnManager().getCurrentPlayer().getFavorPoints() - 2
            );
        gameData.getTools().get(args[0]).addFavorPoints();
    }
}
