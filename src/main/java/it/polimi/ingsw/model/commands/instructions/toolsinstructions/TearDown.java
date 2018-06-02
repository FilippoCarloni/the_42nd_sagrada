package it.polimi.ingsw.model.commands.instructions.toolsinstructions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gamedata.GameData;

public class TearDown implements Instruction {

    private Condition condition;

    public TearDown(Condition condition) {
        this.condition = condition;
    }

    @Override
    public void perform(GameData gameData, int[] args) {
        if (condition == null || condition.getPredicate().test(gameData, args)) {
            gameData.setActiveToolID(0);
            gameData.setPassiveToolID(0);
        }
    }
}
