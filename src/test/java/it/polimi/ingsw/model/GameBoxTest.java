package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.GameBox;
import it.polimi.ingsw.model.gameboard.dice.ClothDiceBag;
import it.polimi.ingsw.model.gameboard.roundtrack.PaperRoundTrack;
import it.polimi.ingsw.model.gameboard.utility.Parameters;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameBoxTest {

    @Test
    void gameBoxTest() {
        GameBox gb = GameBox.open();
        assertEquals(GameBox.class, gb.getClass());
        assertEquals(PaperRoundTrack.class, gb.getRoundTrack().getClass());
        assertEquals(ClothDiceBag.class, gb.getDiceBag().getClass());
        assertEquals(Parameters.PUBLIC_OBJECTIVES, gb.getPublicObjectives().size());
        assertThrows(IllegalArgumentException.class, () -> gb.getPrivateObjectives(-1));
        assertThrows(IllegalArgumentException.class, () -> gb.getWindowFrames(-1));
        for (int numOfPlayers = 2; numOfPlayers <= Parameters.MAX_PLAYERS; numOfPlayers++) {
            assertEquals(numOfPlayers, gb.getPrivateObjectives(numOfPlayers).size());
            assertEquals(numOfPlayers * Parameters.NUM_OF_WINDOWS_PER_PLAYER_BEFORE_CHOICE,
                    gb.getWindowFrames(numOfPlayers).size());
        }
    }
}
