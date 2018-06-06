package it.polimi.ingsw.model.commands.instructions.toolsinstructions;

import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolCard;
import it.polimi.ingsw.model.gamedata.GameData;

public class ManageFavorPoints implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        for (ToolCard c : gameData.getTools()) {
            if (c.getID() == args[0] + 1) {
                if (c.getFavorPoints() == 0)
                    gameData.getTurnManager().getCurrentPlayer().setFavorPoints(
                            gameData.getTurnManager().getCurrentPlayer().getFavorPoints() - 1
                    );
                else
                    gameData.getTurnManager().getCurrentPlayer().setFavorPoints(
                            gameData.getTurnManager().getCurrentPlayer().getFavorPoints() - 2
                    );
                c.addFavorPoints();
                return;
            }
        }
        throw new IllegalArgumentException("Something went wrong in tool card activation: this tool does not exist in this game.");
    }
}
