package it.polimi.ingsw.model.turns;

import it.polimi.ingsw.model.players.Player;

public interface TurnManager {
    Player getCurrentPlayer();
    void advanceTurn();
    boolean isRoundStarting();
    boolean isRoundEnding();
    boolean isSecondTurn();
    void takeTwoTurns();
}
