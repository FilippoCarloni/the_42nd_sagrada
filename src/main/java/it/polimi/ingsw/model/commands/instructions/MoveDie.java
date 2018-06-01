package it.polimi.ingsw.model.commands.instructions;

import it.polimi.ingsw.model.gamedata.GameData;

public class MoveDie implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        gameData.getTurnManager().getCurrentPlayer().getWindowFrame().move(
                args[0], args[1], args[2], args[3]
        );
    }
}
