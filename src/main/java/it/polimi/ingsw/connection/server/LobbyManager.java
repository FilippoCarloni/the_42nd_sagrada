package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.constraints.Settings;
import it.polimi.ingsw.connection.server.messageencoder.MessageType;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static it.polimi.ingsw.connection.constraints.ServerMessages.WAITING_MESSAGE;
import static it.polimi.ingsw.connection.constraints.Settings.MAX_PLAYER_GAME;
import static it.polimi.ingsw.connection.constraints.Settings.MIN_PLAYER_GAME;
import static it.polimi.ingsw.connection.constraints.ServerMessages.LOBBY_LEAVING;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * The LobbyManager class creates a game lobby and ensures the right behavioral of a game lobby.
 * The rules of game lobby are specified in the doc specification of the project.
 */
public class LobbyManager extends Observable{


    private boolean open;
    private List<OnLinePlayer> players;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(2);
    private ScheduledFuture<?> timer;
    private ScheduledFuture<?> beeperHandle;

    /**
     * Creates a new LobbyManger.
     * A LobbyManager starts opened and with no players.
     */
    LobbyManager(){
        open=true;
        timer=null;
        players=new ArrayList<>();
    }

    /**
     * The add method try to add a player to game lobby if it is possible.
     * Also, the method is synchronized for multi-thread.
     * If the OnLinePlayer are 4 the game lobby is closed.
     * @param player - The OnLinePlayer to add at the game lobby.
     * @return - If th player is added or not.
     */
    public synchronized boolean add(OnLinePlayer player) {
        Runnable task= this::endWaiting;
        Runnable cleaner = this::playerCleaner;
        if(players.contains(player))
            return true;
        if(!open)
            return false;
        player.getObserver().update(this,MessageType.encodeMessage(WAITING_MESSAGE,MessageType.GENERIC_MESSAGE));
        this.addObserver(player.getObserver());
        players.parallelStream().forEach(x -> {
            x.getObserver().update(this, MessageType.encodeMessage(player.getPlayer().getUsername() + " is connected to this game lobby!",MessageType.GENERIC_MESSAGE));
            player.getObserver().update(this, MessageType.encodeMessage(x.getPlayer().getUsername() + " is connected to this game!",MessageType.GENERIC_MESSAGE));
        });
        players.add(player);
        if(players.size()==1)
            beeperHandle=scheduler.scheduleAtFixedRate(cleaner, 1, new Settings().lobbyRefreshTime, MILLISECONDS);
        else if(players.size()==MAX_PLAYER_GAME) {
            open = false;
            timer.cancel(true);
            beeperHandle.cancel(true);
            notifyAll();
        }else if (players.size()== MIN_PLAYER_GAME)
            timer=scheduler.schedule(task, new Settings().lobbyWaitingTime,MILLISECONDS);
        return true;
    }

    /**
     * Close the game lobby if the players are more than 1.
     * It is called after the timer is closed.
     */
    private synchronized void endWaiting(){
        if(players.size()>=MIN_PLAYER_GAME) {
            this.open = false;
            this.notifyAll();
            this.beeperHandle.cancel(true);
        }
    }

    /**
     * Wait the closing of the game lobby.
     * @return - The list of OnLinePlayer in the lobby.
     */
    synchronized List<OnLinePlayer> waitStart(){
        while (open) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return players;
    }

    /**
     * Clean the game lobby from inactive OnLinePlayer.
     */
    private synchronized void playerCleaner(){
        List<OnLinePlayer> noActive=new ArrayList<>();
            for( OnLinePlayer p: this.players)
                if(!p.getObserver().isAlive()) {
                    noActive.add(p);
                    this.deleteObserver(p.getObserver());
                    this.setChanged();
                    this.notifyObservers(MessageType.encodeMessage(p.getPlayer().getUsername()+LOBBY_LEAVING,MessageType.GENERIC_MESSAGE));
                }

            for(OnLinePlayer p:noActive)
                this.players.remove(p);
            if(this.players.isEmpty())
                this.beeperHandle.cancel(false);
            else if(this.players.size()==1&&this.timer!=null)
                this.timer.cancel(true);
    }
}
