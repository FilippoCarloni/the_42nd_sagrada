package it.polimi.ingsw.model.commands.instructions.windowframeinstructions;

import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gamedata.GameData;

public class MoveDie implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        gameData.getDiceMoved().add(gameData.getTurnManager().getCurrentPlayer().getWindowFrame().getDie(args[0], args[1]));
        gameData.getTurnManager().getCurrentPlayer().getWindowFrame().move(
                args[0], args[1], args[2], args[3]
        );
    }
}
