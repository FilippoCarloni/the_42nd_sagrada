package it.polimi.ingsw.connection.server;

import java.util.Observer;

public interface GameObserver extends Observer {
    boolean isAlive();
}
