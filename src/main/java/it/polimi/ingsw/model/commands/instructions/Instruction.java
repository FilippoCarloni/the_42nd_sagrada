package it.polimi.ingsw.model.commands.instructions;

import it.polimi.ingsw.model.gamedata.GameData;

@FunctionalInterface
public interface Instruction {
    void perform(GameData gameData, int[] args);
}
