package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.client.RemoteObserver;
import it.polimi.ingsw.model.ConcreteGameStatus;
import it.polimi.ingsw.model.GameStatus;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class GameController extends Observable{
    private static final transient Logger logger=Logger.getLogger(GameController.class.getName());
    private final transient GameStatus data;
    private final transient List<WrappedPlayer> players;
    private final transient List<GameObserver> observers;
    GameController(List<WrappedPlayer> players) {
        data = new ConcreteGameStatus(players
                .stream()
                .map(WrappedPlayer::getPlayer)
                .collect(Collectors.toList()));
        this.players = new ArrayList<>(players);
        observers=new ArrayList<>();
    }

    public synchronized boolean isLegal(String sessionID, String command)  throws Exception {
        return data.isLegal(this.getPlayer(sessionID).getPlayer(), command.trim());
    }

    public synchronized void sendCommand(String sessionID, String command) throws Exception {
        data.execute(this.getPlayer(sessionID).getPlayer(), command.trim());
        setChanged();
        notifyObservers(data.toString());
    }

    public synchronized boolean isMyTurn(String sessionID) throws Exception  {
        return data.isMyTurn(this.getPlayer(sessionID).getPlayer());
    }

    public synchronized void addGameObserver(String sessionID, GameObserver obs) throws Exception{
        this.getPlayer(sessionID);
        addObserver(obs);
        observers.add(obs);
        logger.info(() -> "Added game observer :"+sessionID);
    }

    public  void removeRemoteObserver(Session session) throws Exception {
        getPlayer(session.getID());
        synchronized(observers) {
            for (Observer x : observers) {
               /* if (x.equals(new ConcreteGameManager.WrappedObs(obs))) {
                    x.removeRemoteObserver(this);
                    observers.remove(x);
                    logger.info(() -> "Removed observer:" + x);
                    return;
                }*/
            }
        }
        throw new RemoteException("Error occurred removing an observer.");
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
