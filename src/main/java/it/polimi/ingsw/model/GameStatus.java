package it.polimi.ingsw.model;

import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.JSONSerializable;

/*
 * @deprecated
 */
public interface GameStatus extends JSONSerializable {
    boolean isMyTurn(Player me);
    boolean isLegal(Player player, String command);
    void execute(Player player, String command);
}
