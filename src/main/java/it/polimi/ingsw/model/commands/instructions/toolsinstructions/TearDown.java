package it.polimi.ingsw.model.commands.instructions.toolsinstructions;

import it.polimi.ingsw.model.commands.conditions.Condition;
import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gamedata.GameData;

/**
 * Ends the effect of a Tool Card.
 * The player can now perform generic commands (pick, place and pass).
 */
public class TearDown implements Instruction {

    private Condition condition;

    /**
     * Generates a new condition TearDown instance.
     * The instruction is performed only on null condition or verified condition.
     * @param condition A Condition instance
     */
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
