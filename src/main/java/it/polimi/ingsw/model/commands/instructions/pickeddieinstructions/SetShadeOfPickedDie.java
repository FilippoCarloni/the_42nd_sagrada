package it.polimi.ingsw.model.commands.instructions.pickeddieinstructions;

import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.commands.instructions.InstructionID;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.utility.Shade;

import static it.polimi.ingsw.model.utility.ExceptionMessage.BAD_JSON;

/**
 * Sets the selected shade to the picked die.
 * The shade can be a generic one or a particular one (increment, decrement or flip).
 */
public class SetShadeOfPickedDie implements Instruction {

    private String argument;

    /**
     * Generates a particular shade setter based on a string argument.
     * This setter can be an increment, decrement, flip or generic one.
     * @param argument A string argument
     */
    public SetShadeOfPickedDie(String argument) {
        this.argument = argument;
    }

    private Die flip(Die die) {
        Shade[] values = Shade.values();
        int index = -1;
        for (int i = 0; i < values.length && index < 0; i++)
            if (die.getShade().equals(values[i]))
                index = i;
        assert index > 0;
        die.setShade(values[values.length - 1 - index]);
        return die;
    }

    @Override
    public void perform(GameData gameData, int[] args) {
        switch (argument) {
            case InstructionID.INCREASE:
                gameData.getPickedDie().setShade(
                        Shade.findByValue(gameData.getPickedDie().getShade().getValue() + 1)
                );
                break;
            case InstructionID.DECREASE:
                gameData.getPickedDie().setShade(
                        Shade.findByValue(gameData.getPickedDie().getShade().getValue() - 1)
                );
                break;
            case InstructionID.NO_CONSTRAINT:
                gameData.getPickedDie().setShade(
                        Shade.findByValue(args[0] + 1)
                );
                break;
            case InstructionID.FLIP:
                gameData.setPickedDie(flip(gameData.getPickedDie()));
                break;
            default:
                throw new IllegalArgumentException(BAD_JSON);
        }
    }
}
