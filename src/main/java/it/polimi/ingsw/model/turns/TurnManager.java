package it.polimi.ingsw.model.turns;

import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.JSONSerializable;

public interface TurnManager extends JSONSerializable {
    Player getCurrentPlayer();
    void advanceTurn();
    boolean isRoundStarting();
    boolean isRoundEnding();
    boolean isSecondTurn();
    void takeTwoTurns();
}
