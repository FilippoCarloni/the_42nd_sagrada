package it.polimi.ingsw.connection.server;

import java.util.Observer;

/**
 * the gameObserver is the generic interfaces used by the OnLinePlayer.
 * It permits to the server to update a OnLinePlayer and to monitor if a OnLinePlayers ia connected.
 */
public interface GameObserver extends Observer {

    /**
     * Permits to monitor if it is in live.
     * @return - A boolean that represents if it is alive or not.
     */
    boolean isAlive();
}
