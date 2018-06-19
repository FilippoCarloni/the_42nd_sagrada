package it.polimi.ingsw.model.commands.instructions.toolsinstructions;

import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolCard;
import it.polimi.ingsw.model.gamedata.GameData;

import static it.polimi.ingsw.model.utility.ExceptionMessage.OBJECT_NOT_EXISTS;

/**
 * On Tool Card activation, removes favor points from the current player
 * and gives the removed favor points to the activated Tool Card.
 */
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
        throw new IllegalArgumentException(OBJECT_NOT_EXISTS);
    }
}
