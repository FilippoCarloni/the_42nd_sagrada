package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.GameStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class GameController extends Observable{
    private static final transient Logger logger=Logger.getLogger(GameController.class.getName());
    private final transient GameStatus data;
    private final transient List<WrappedPlayer> players;
    GameController(List<WrappedPlayer> players) {
        data = new ConcreteGameStatus(players
                .stream()
                .map(WrappedPlayer::getPlayer)
                .collect(Collectors.toList()));
        this.players = new ArrayList<>(players);
        for (WrappedPlayer p: players)
            addObserver(p.getObserver());
    }

    public synchronized boolean isLegal(String sessionID, String command)  throws Exception {
        return data.isLegal(this.getPlayer(sessionID).getPlayer(), command.trim());
    }

    public synchronized void sendCommand(String sessionID, String command) throws Exception {
        if(isLegal(sessionID,command))
            data.execute(this.getPlayer(sessionID).getPlayer(), command.trim());
        else
            throw new Exception("Illegal command, please check if the syntax is correct.");
        setChanged();
        notifyObservers(data.toString());
    }

    public synchronized boolean isMyTurn(String sessionID) throws Exception  {
        return data.isMyTurn(this.getPlayer(sessionID).getPlayer());
    }


    public synchronized String getStatus(String sessionID) throws Exception{
        getPlayer(sessionID);
        return "" + data;
    }
    private WrappedPlayer getPlayer(String sessionID) throws Exception{
        List<WrappedPlayer> player=players.stream().filter(x -> x.getSession().getID().equals(sessionID))
                .collect(Collectors.toList());
        if(player.size() != 1) {
            logger.log(Level.SEVERE, "Hacker !!!, not playing user are trying to enter in a match");
            throw new Exception("Error, you are not playing in this game");
        }
        return player.get(0);
    }
    synchronized boolean isPlaying(WrappedPlayer player){
        return players.stream().filter(x-> x.equals(player)).count() == 1;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "GameController";
    }
}
