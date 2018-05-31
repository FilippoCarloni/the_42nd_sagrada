package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;


public class WrappedPlayer {

    private Player player;
    private Session session;
    private boolean playing;
    private GameObserver observer;
    WrappedPlayer(String username,GameObserver obs) {
        player = new ConcretePlayer(username);
        session = new Session(username,"");
        observer=obs;
        playing = false;
    }

    synchronized public Session getSession(){
        return session;
    }

    synchronized boolean isPlaying() {
        return playing;
    }

    synchronized void setPlaying(boolean playing){
        this.playing=playing;
    }

    public Player getPlayer(){
        return player;
    }

    synchronized public void setSession(Session session) {
        this.session = session;
    }

    synchronized public GameObserver getObserver() {
        return observer;
    }

    synchronized public void setObserver(GameObserver observer) {
        this.observer = observer;
    }

    @Override
    synchronized public boolean equals(Object obj) {
        return obj instanceof WrappedPlayer &&
                this.getSession().getID().equals( ((WrappedPlayer) obj).getSession().getID());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
