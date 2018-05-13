package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;


public class WrappedPlayer {

    private Player player;
    private Session session;
    private boolean playing;
    public WrappedPlayer(String username) {
        player = new ConcretePlayer(username);
        session = new Session(username,"");
        playing = false;
    }

    public Session getSession(){
        return session;
    }

    public boolean isPlaying() {
        return playing;
    }

    public Player getPlayer(){
        return player;
    }

    public void setSession(Session session) {
        this.session = session;
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
