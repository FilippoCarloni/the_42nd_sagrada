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

    public Session getSession(){
        return session;
    }

    boolean isPlaying() {
        return playing;
    }

    public Player getPlayer(){
        return player;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public GameObserver getObserver() {
        return observer;
    }

    public void setObserver(GameObserver observer) {
        this.observer = observer;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof WrappedPlayer &&
                this.getSession().getID().equals( ((WrappedPlayer) obj).getSession().getID());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
