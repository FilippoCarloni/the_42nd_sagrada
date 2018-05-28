package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.socket.RemoteClient;
import it.polimi.ingsw.model.commands.IllegalCommandException;
import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import it.polimi.ingsw.model.gamedata.ConcreteGame;
import it.polimi.ingsw.model.gamedata.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class GameController extends Observable{
    private static final transient Logger logger=Logger.getLogger(GameController.class.getName());
    private final transient Game game;
    private final transient List<WrappedPlayer> players;
    private final List<WrappedPlayer> disconnected;
    final ScheduledFuture<?> beeperHandle;
    ScheduledFuture<?> timer;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(2);
    GameController(List<WrappedPlayer> players) {
        Deck deck1=new PrivateObjectiveDeck();
        Deck deck2=new WindowFrameDeck();
        for( WrappedPlayer p: players){
            p.getPlayer().setWindowFrame((WindowFrame)deck2.draw());
            p.getPlayer().setPrivateObjective((PrivateObjectiveCard)deck1.draw());
        }

        game = new ConcreteGame(players
                .stream()
                .map(WrappedPlayer::getPlayer)
                .collect(Collectors.toList()));
        this.players = new ArrayList<>(players);
        for (WrappedPlayer p: players)
            addObserver(p.getObserver());
        setChanged();
        notifyObservers(game.getData().encode().toString());
        isTurnOf();
        disconnected= new ArrayList<>();
        final Runnable cleener = () -> {
            synchronized (this.disconnected){
            for( WrappedPlayer p: this.players)
                if(!p.getObserver().isAlive()&&!disconnected.contains(p)) {
                    this.deleteObserver(p.getObserver());
                    this.disconnected.add(p);
                    this.setChanged();
                    this.notifyObservers(p.getPlayer().getUsername()+" is now disconnected");
                }
        }}
        ;
        beeperHandle=scheduler.scheduleAtFixedRate(cleener, 1, 100, MILLISECONDS);
        startTimer();
    }

    public synchronized void sendCommand(String sessionID, String command) throws Exception {
        boolean print=false;
        if(!isMyTurn(sessionID)) {
            throw new Exception("Is not your turn!");
        }
        command=command.trim();
        switch (command) {
            case "undo":
                if(game.isUndoAvailable())
                    game.undoCommand();
                else
                    throw new Exception("You can not undo");
                break;
            case "redo":
                if(game.isRedoAvailable())
                    game.redoCommand();
                else
                    throw new Exception("You can not redo");
                break;
            default:
                try {
                    game.executeCommand(this.getPlayer(sessionID).getPlayer(), command);
                } catch (IllegalCommandException e) {
                    throw new Exception(e.getMessage());
                }
                if(command.equals("pass")) {
                    print=true;
                }
                break;
        }
        setChanged();
        notifyObservers(game.getData().encode().toString());
        if(print){
            timer.cancel(true);
            isTurnOf();
            startTimer();
        }
    }

    public synchronized boolean isMyTurn(String sessionID) throws Exception  {
        return game.getCurrentPlayer().getUsername().equals(this.getPlayer(sessionID).getPlayer().getUsername());
    }


    public synchronized String getStatus(String sessionID) throws Exception{
        getPlayer(sessionID);
        return game.getData().encode().toString();
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
    void reconnect(WrappedPlayer player){
        synchronized (disconnected) {
            if (isPlaying(player)&&disconnected.contains(player)&& player.getObserver().isAlive()) {
                this.setChanged();
                this.notifyObservers(player.getPlayer().getUsername()+" is now reconnected");
                this.addObserver(player.getObserver());
                this.disconnected.remove(player);
            }
        }
        synchronized (this) {
            player.getObserver().update(this, game.getData().encode().toString());
        }
    }
    synchronized boolean isPlaying(WrappedPlayer player){
        return players.stream().filter(x-> x.equals(player)).count() == 1;
    }
    private void isTurnOf(){
        setChanged();
        notifyObservers("Current player: "+game.getCurrentPlayer().getUsername());
    }
    private void startTimer(){
        Runnable task=() -> {
            this.timerPass();
        };
        timer=scheduler.schedule(task,20000,MILLISECONDS);
    }
    private synchronized void timerPass(){
        boolean notCanPass=true;
        while(notCanPass) {
            try {
                game.executeCommand(game.getCurrentPlayer(), "pass");
                notCanPass=false;
            } catch (IllegalCommandException e) {
                game.undoCommand();
            }
        }
        startTimer();
        setChanged();
        notifyObservers(this.game.getData().encode().toString());
        isTurnOf();
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
