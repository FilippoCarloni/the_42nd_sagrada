package it.polimi.ingsw.model.commands.instructions.pickeddieinstructions;

import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.commands.instructions.InstructionID;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.utility.Shade;

public class SetShadeOfPickedDie implements Instruction {

    private String argument;

    public SetShadeOfPickedDie(String argument) {
        this.argument = argument;
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
                        Shade.findByValue(args[0])
                );
                break;
            default:
                throw new IllegalArgumentException("Bad JSON argument: " + argument);
        }
    }
}
