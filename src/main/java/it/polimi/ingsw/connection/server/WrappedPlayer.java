package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.model.players.ConcretePlayer;

public class WrappedPlayer {
    private ConcretePlayer player;
    private Session session;
    private boolean playing;
    public WrappedPlayer(String username) {
        player=new ConcretePlayer(username);
        session=new Session(username,"");
        playing=false;
    }
    public Session getSession(){
        return session;
    }
    public boolean isPlaying() {
        return playing;
    }

    public ConcretePlayer getPlayer(){
        return player;
    }

}

