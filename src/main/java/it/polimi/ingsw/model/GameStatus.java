package it.polimi.ingsw.model;

import it.polimi.ingsw.model.players.Player;

public interface GameStatus {
    boolean isMyTurn(Player me);
    boolean isLegal(Player player, String command);
    void execute(Player player, String command);
}
