package it.polimi.ingsw.model.commands.instructions.gamedatainstructions;

import it.polimi.ingsw.model.commands.instructions.Instruction;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gamedata.GameData;

/**
 * Swaps the picked die with one present on the Round Track.
 */
public class SwapFromRoundTrack implements Instruction {

    @Override
    public void perform(GameData gameData, int[] args) {
        Die playerDie = gameData.getPickedDie();
        Die roundTrackDie = gameData.getRoundTrack().getDice().get(args[0]);
        gameData.getRoundTrack().swap(playerDie, roundTrackDie);
        gameData.setPickedDie(roundTrackDie);
    }
}
