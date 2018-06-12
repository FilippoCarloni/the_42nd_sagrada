package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.server.messageencoder.MessageType;
import it.polimi.ingsw.connection.server.serverexception.ServerException;
import it.polimi.ingsw.model.commands.IllegalCommandException;
import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import it.polimi.ingsw.model.gamedata.ConcreteGame;
import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.model.utility.Parameters;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static it.polimi.ingsw.connection.server.serverexception.ErrorCode.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class GameController extends Observable{
    private static final Logger logger=Logger.getLogger(GameController.class.getName());
    private Game game;
    private final List<WrappedPlayer> players;
    private final List<WrappedPlayer> disconnected;
    private ScheduledFuture<?> beeperHandle;
    private final CentralServer server;
    private ScheduledFuture<?> timer;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(3);
    private List<WindowFrame> windowFrames;
    private List<Integer> windowChose;
    GameController(CentralServer server, List<WrappedPlayer> players) {
        List<PrivateObjectiveCard> privateObjectiveCards=Game.getPrivateObjectives(players.size());
        Deck deck2=new WindowFrameDeck();
        this.server=server;
        this.players = new ArrayList<>(players);
        this.windowChose=new ArrayList<>();
        for(int i=0;i<this.players.size();i++)
            this.windowChose.add(1);
        getPreGameFrames(players.size());
        for (WrappedPlayer p: players)
            addObserver(p.getObserver());
        beeperHandle=null;
        disconnected= new ArrayList<>();
        final Runnable cleaner = () -> {
            synchronized (this.disconnected){
            for( WrappedPlayer p: this.players)
                if(!p.getObserver().isAlive()&&!disconnected.contains(p)) {
                    this.deleteObserver(p.getObserver());
                    this.disconnected.add(p);
                    this.setChanged();
                    this.notifyObservers(MessageType.encodeMessage(p.getPlayer().getUsername()+" is now disconnected",MessageType.GENERIC_MESSAGE));
                }
                if(this.countObservers()==1) {
                    this.setChanged();
                    this.notifyObservers(MessageType.encodeMessage("You win! because you are the only player",MessageType.GENERIC_MESSAGE));
                    this.deleteObservers();
                }

            }
            if(this.countObservers()==0) {
                closeGame();
            }
        }
        ;
        beeperHandle=scheduler.scheduleAtFixedRate(cleaner, 1, 100, MILLISECONDS);
        startTimer();
        scheduler.schedule(this::setWindowsFrame,10000,MILLISECONDS );
    }

    public synchronized void setMap(String sessionID,int window) throws ServerException {
        WrappedPlayer player=this.getPlayer(sessionID);
        if(window>=1&&window<=Parameters.NUM_OF_WINDOWS_PER_PLAYER_BEFORE_CHOICE)
            windowChose.set(players.indexOf(player),window);
    }

    private synchronized void setWindowsFrame() {
        for( WrappedPlayer p: players){
            p.getPlayer().setWindowFrame(windowFrames.get(windowChose.get(players.indexOf(p))-1+players.indexOf(p)*Parameters.NUM_OF_WINDOWS_PER_PLAYER_BEFORE_CHOICE));
        }
        game = new ConcreteGame(players
                .stream()
                .map(WrappedPlayer::getPlayer)
                .collect(Collectors.toList()));
        isTurnOf();
        sendStatus();
    }
    private void getPreGameFrames(int numPlayers){
        List<PrivateObjectiveCard> privateObjectiveCards=Game.getPrivateObjectives(numPlayers);
        JSONObject jsonObject;
        JSONArray encodedFrames;
        windowFrames=Game.getWindowFrames(numPlayers);
        int j=0;
        for(WrappedPlayer p:players){
            jsonObject=new JSONObject();
            p.getPlayer().setPrivateObjective(privateObjectiveCards.remove(0));
            jsonObject.put(JSONTag.PRIVATE_OBJECTIVE,p.getPlayer().getPrivateObjective().encode());
            encodedFrames=new JSONArray();
            for(int i=0;i<Parameters.NUM_OF_WINDOWS_PER_PLAYER_BEFORE_CHOICE;i++)
                encodedFrames.add(windowFrames.get(i+Parameters.NUM_OF_WINDOWS_PER_PLAYER_BEFORE_CHOICE*j).encode());
            jsonObject.put(JSONTag.WINDOW_FRAMES,encodedFrames);
            p.getObserver().update(this,MessageType.encodeMessage(jsonObject.toString(),MessageType.PRE_GAME_CHOICE));
            j++;
        }
    }

    public synchronized void sendCommand(String sessionID, String command) throws ServerException {
        boolean passed=false;
        if(!isMyTurn(sessionID)) {
            throw new ServerException("Is not your turn!",GAME_ERROR);
        }
        command=command.trim();
        switch (command) {
            case "undo":
                if(game.isUndoAvailable())
                    game.undoCommand();
                else
                    throw new ServerException("You can not undo",GAME_ERROR);
                break;
            case "redo":
                if(game.isRedoAvailable())
                    game.redoCommand();
                else
                    throw new ServerException("You can not redo",GAME_ERROR);
                break;
            default:
                try {
                    game.executeCommand(this.getPlayer(sessionID).getPlayer(), command);
                } catch (IllegalCommandException e) {
                    throw new ServerException(e.getMessage(),GAME_ERROR);
                }
                if(command.equals("pass")) {
                    passed=true;
                }
                break;
        }
        sendStatus();
        if(passed) {
            if(game.isGameEnded()) {
                printScore();
                closeGame();
            }
            else {
                timer.cancel(true);
                isTurnOf();
                startTimer();
            }
        }
    }

    public synchronized boolean isMyTurn(String sessionID) throws ServerException  {
        return game.getCurrentPlayer().getUsername().equals(this.getPlayer(sessionID).getPlayer().getUsername());
    }

    public synchronized String getStatus(String sessionID) throws ServerException{
        WrappedPlayer player=getPlayer(sessionID);
        return MessageType.encodeMessage(game.getData(player.getPlayer()).toString(),MessageType.GAME_BOARD);
    }

    private WrappedPlayer getPlayer(String sessionID) throws ServerException{
        List<WrappedPlayer> player=players.parallelStream().filter(x -> x.getSession().getID().equals(sessionID))
                .collect(Collectors.toList());
        if(player.size() != 1) {
            logger.log(Level.SEVERE, "Hacker !!!, not playing user are trying to enter in a match");
            throw new ServerException("Error, you are not playing in this game",SERVER_ERROR);
        }
        return player.get(0);
    }

    boolean reconnect(WrappedPlayer player){
        synchronized (disconnected) {
            if (isPlaying(player)&&disconnected.contains(player)&& player.getObserver().isAlive()) {
                this.setChanged();
                this.notifyObservers(MessageType.encodeMessage(player.getPlayer().getUsername()+" is now reconnected",MessageType.GENERIC_MESSAGE));
                this.addObserver(player.getObserver());
                this.disconnected.remove(player);
                if(this.countObservers()==0)
                    return false;
            }
        }
        synchronized (this) {
            player.getObserver().update(this, game.getData().encode().toString());
        }
        return true;
    }

    private void closeGame(){
        this.server.closeGame(this);
        this.timer.cancel(true);
        this.beeperHandle.cancel(true);
    }
    synchronized boolean isPlaying(WrappedPlayer player){
        return players.stream().filter(x-> x.equals(player)).count() == 1;
    }

    private void isTurnOf(){
        setChanged();
        notifyObservers(MessageType.encodeMessage("Current player: "+game.getCurrentPlayer().getUsername(),MessageType.GENERIC_MESSAGE));
    }

    private void startTimer(){
        Runnable task= this::timerPass;
        timer=scheduler.schedule(task,60000,MILLISECONDS);
    }

    private void sendStatus(){
        players.parallelStream().forEach(x -> x.getObserver().update(this, MessageType.encodeMessage(this.game.getData(x.getPlayer()).toString(),MessageType.GAME_BOARD)));
    }
    private synchronized void timerPass(){
        boolean notPass=true;
        while(notPass) {
            try {
                game.executeCommand(game.getCurrentPlayer(), "pass");
                notPass=false;
            } catch (IllegalCommandException e) {
                game.undoCommand();
            }
        }
        startTimer();
        setChanged();
        sendStatus();
        if(game.isGameEnded()) {
            printScore();
            closeGame();
        }
        else
            isTurnOf();
    }

    private void printScore(){
        String out="";
        for (WrappedPlayer p : players)
            out = out.concat(p.getPlayer().getUsername() + ":" + Integer.toString(game.getScore().get(p.getPlayer())) + "\n");
        out=out.concat("Ready to start a new game!");
        this.setChanged();
        this.notifyObservers(MessageType.encodeMessage(out,MessageType.GENERIC_MESSAGE));
    }

    List<WrappedPlayer> getPlayers(){
        return new ArrayList<>(players);
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
