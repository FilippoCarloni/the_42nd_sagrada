package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.costraints.Settings;
import it.polimi.ingsw.connection.server.messageencoder.MessageType;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class LobbyManager extends Observable{
    private boolean open;
    private List<WrappedPlayer> players;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(2);
    private ScheduledFuture<?> timer;
    private ScheduledFuture<?> beeperHandle;
    LobbyManager(){
        open=true;
        timer=null;
        players=new ArrayList<>();
    }
    public synchronized boolean add(WrappedPlayer player) {
        Runnable task= this::endWaiting;
        Runnable cleaner = this::playerCleaner;
        if(players.contains(player))
            return true;
        if(!open)
            return false;
        player.getObserver().update(this,MessageType.encodeMessage("Waiting others players ...",MessageType.GENERIC_MESSAGE));
        this.addObserver(player.getObserver());
        players.parallelStream().forEach(x -> {
            x.getObserver().update(this, MessageType.encodeMessage(player.getPlayer().getUsername() + " is connected to this game!",MessageType.GENERIC_MESSAGE));
            player.getObserver().update(this, MessageType.encodeMessage(x.getPlayer().getUsername() + " is connected to this game!",MessageType.GENERIC_MESSAGE));
        });
        players.add(player);
        if( players.size()==1)
            beeperHandle=scheduler.scheduleAtFixedRate(cleaner, 1, 500, MILLISECONDS);
        else if(players.size()==4) {
            open = false;
            timer.cancel(true);
            beeperHandle.cancel(true);
            notifyAll();
        }else if (players.size()==2)
            timer=scheduler.schedule(task, Settings.WAITINGTIMETOMATCH,MILLISECONDS);
        return true;
    }
    private synchronized void endWaiting(){
        if(players.size()>=2) {
            this.open = false;
            this.notifyAll();
            this.beeperHandle.cancel(true);
        }
    }
    synchronized List<WrappedPlayer> waitStart(){
        while (open) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return players;
    }
    private synchronized void playerCleaner(){
        List<WrappedPlayer> noActive=new ArrayList<>();
            for( WrappedPlayer p: this.players)
                if(!p.getObserver().isAlive()) {
                    noActive.add(p);
                    this.deleteObserver(p.getObserver());
                    this.setChanged();
                    this.notifyObservers(MessageType.encodeMessage(p.getPlayer().getUsername()+" has leaved the match lobby!",MessageType.GENERIC_MESSAGE));
                }

            for(WrappedPlayer p:noActive)
                this.players.remove(p);
            if(this.players.isEmpty())
                this.beeperHandle.cancel(false);
            else if(this.players.size()==1&&this.timer!=null)
                this.timer.cancel(true);
    }
}
