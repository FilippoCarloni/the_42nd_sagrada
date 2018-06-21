package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;

/**
 * The WrappedPlayer class encapsulates all the essential information for an OnLinePlayer.
 * An on-line player must have:
 * 1) A ServerSession
 * 2) A ConcretePlayer
 * 3) A GameObserver
 * 4) Can play or not
 * 5) An username
 */

public class OnLinePlayer {

    private Player player;
    private ServerSession serverSession;
    private boolean playing;
    private GameObserver observer;
    private String username;

    /**
     * Create a new OnLinePlayer.
     * @param username Username of the OnLinePlayer.
     * @param obs GameObserver, essential for all the communications from the server to the client.
     */
    OnLinePlayer(String username, GameObserver obs) {
        player = new ConcretePlayer(username);
        serverSession = new ServerSession(username);
        observer = obs;
        playing = false;
        this.username = username;
    }

    /**
     *
     * @return a ServerSession associated to the OnLinePlayer.
     */
    synchronized ServerSession getServerSession(){
        return serverSession;
    }

    /**
     *
     * @return if the OnLinePlayer is current playing.
     */
    synchronized boolean isPlaying() {
        return playing;
    }

    /**
     * Sets if the OnLinePlayer is current playing.
     * @param playing A boolean that represents if the OnLinePlayer is current playing or not.
     */
    synchronized void setPlaying(boolean playing){
        this.playing=playing;
    }

    /**
     *
     * @return The ConcretePlayer associated to the OnLinePlayer.
     */
    public Player getPlayer(){
        return player;
    }

    /**
     * Set a new ServerSession for the OnLinePlayer.
     * @return The new ServerSession
     */
    synchronized ServerSession refreshSession() {
        this.serverSession = new ServerSession(username);
        return serverSession;
    }

    /**
     *
     * @return The actual GameObserver associated to the OnLinePlayer.
     */
    synchronized GameObserver getObserver() {
        return observer;
    }

    /**
     * Sets a new GameObserver for the OnLinePlayer.
     * @param observer New GameObserver
     */
    synchronized void setObserver(GameObserver observer) {
        this.observer = observer;
    }

    /**
     *
     * @param sessionID A generic sessionID.
     * @return If the sessionID matches with the respective sessionID of the OnLinePlayr.
     */
    synchronized boolean isMySessionID(String sessionID) {
        return serverSession.getID().equals(sessionID);
    }

    /**
     *
     * @return The OnLinePlayer username.
     */
    public String getUsername() {
        return username;
    }

    @Override
    public synchronized boolean equals(Object obj) {
        return obj instanceof OnLinePlayer &&
                super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
