package it.polimi.ingsw.model;

import it.polimi.ingsw.model.players.Player;

public interface GameStatus {
    boolean isMyTurn(Player me);
    void selectFromDicePool();
    // TODO: add rules
    void placeDie(int row, int column/*, list<Rule> rules*/);
}
