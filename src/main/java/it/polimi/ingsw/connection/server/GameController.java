package it.polimi.ingsw.connection.server;

import it.polimi.ingsw.connection.costraints.Commands;
import it.polimi.ingsw.connection.costraints.Settings;
import it.polimi.ingsw.connection.server.messageencoder.MessageType;
import it.polimi.ingsw.connection.server.serverexception.ServerException;
import it.polimi.ingsw.model.commands.IllegalCommandException;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
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

import static it.polimi.ingsw.connection.server.ServerMessage.*;
import static it.polimi.ingsw.connection.server.serverexception.ErrorCode.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;


public class GameController extends Observable{
    private static final Logger logger=Logger.getLogger(GameController.class.getName());
    private static final int POOLED_THREAD = 3;
    private Game game;
    private final List<OnLinePlayer> players;
    private final List<OnLinePlayer> disconnected;
    private ScheduledFuture<?> beeperHandle;
    private final CentralServer server;
    private ScheduledFuture<?> timer;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(POOLED_THREAD);
    private List<WindowFrame> windowFrames;
    private List<Integer> windowChoices;
    private Settings settings;
    private boolean activeGame;
    GameController(CentralServer server, List<OnLinePlayer> players) {
        this.server=server;
        settings = new Settings();
        this.players = new ArrayList<>(players);
        this.windowChoices=new ArrayList<>();
        for(int i=0;i<this.players.size();i++)
            this.windowChoices.add(1);
        getPreGameFrames(players.size());
        for (OnLinePlayer p: players)
            addObserver(p.getObserver());
        beeperHandle=null;
        disconnected= new ArrayList<>();
        activeGame = true;
        final Runnable cleaner = () -> {
            synchronized (this) {
                for (OnLinePlayer p : this.players)
                    if (!p.getObserver().isAlive() && !disconnected.contains(p)) {
                        this.deleteObserver(p.getObserver());
                        this.disconnected.add(p);
                        this.setChanged();
                        this.notifyObservers(MessageType.encodeMessage(p.getPlayer().getUsername() + DISCONNECTED, MessageType.GENERIC_MESSAGE));
                    }
                if (this.countObservers() == 1) {
                    this.setChanged();
                    this.notifyObservers(MessageType.encodeMessage(WIN_ONE_PLAYER, MessageType.GAME_STATS));
                    this.deleteObservers();
                }

                if (this.countObservers() == 0)
                    closeGame();
            }
        }
        ;
        beeperHandle=scheduler.scheduleAtFixedRate(cleaner, 0, settings.gameRefresh, MILLISECONDS);
        startTimer();
        scheduler.schedule(this::setWindowsFrame,10000,MILLISECONDS );
    }

    public synchronized void setMap(String sessionID,int window) throws ServerException {
        OnLinePlayer player=this.getPlayer(sessionID);
        if(gameNotStarted()&&window>=1&&window<=Parameters.NUM_OF_WINDOWS_PER_PLAYER_BEFORE_CHOICE)
            windowChoices.set(players.indexOf(player),window);
    }

    private synchronized void setWindowsFrame() {
        for( OnLinePlayer p: players){
            p.getPlayer().setWindowFrame(windowFrames.get(windowChoices.get(players.indexOf(p))-1+players.indexOf(p)*Parameters.NUM_OF_WINDOWS_PER_PLAYER_BEFORE_CHOICE));
        }
        game = new ConcreteGame(players
                .stream()
                .map(OnLinePlayer::getPlayer)
                .collect(Collectors.toList()));
        if(!gameEnded())
            sendStatus();
        isTurnOf();
    }

    private boolean gameNotStarted(){
        return game == null;
    }

    @SuppressWarnings("unchecked")
    private void getPreGameFrames(int numPlayers){
        List<PrivateObjectiveCard> privateObjectiveCards=Game.getPrivateObjectives(numPlayers);
        JSONObject jsonObject;
        JSONArray encodedFrames;
        windowFrames=Game.getWindowFrames(numPlayers);
        int j=0;
        for(OnLinePlayer p:players){
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
        if(gameNotStarted())
            throw new ServerException(WAIT_WINDOW,GAME_ERROR);
        if(gameEnded())
            throw new ServerException(ENDED_GAME,GAME_ERROR);
        if(!isMyTurn(sessionID)) {
            throw new ServerException(NOT_YOUR_TURN,GAME_ERROR);
        }
        command=command.trim();
        switch (command) {
            case Commands.UNDO:
                if(game.isUndoAvailable())
                    game.undoCommand();
                else
                    throw new ServerException(NOT_UNDO,GAME_ERROR);
                break;
            case Commands.REDO:
                if(game.isRedoAvailable())
                    game.redoCommand();
                else
                    throw new ServerException(NOT_REDO,GAME_ERROR);
                break;
            default:
                try {
                    game.executeCommand(this.getPlayer(sessionID).getPlayer(), command);
                } catch (IllegalCommandException e) {
                    throw new ServerException(e.getMessage(),GAME_ERROR);
                }
                if(command.equals(Commands.PASS)) {
                        if (game.isGameEnded()) {
                            printScore();
                            closeGame();
                        } else {
                            timer.cancel(true);
                            isTurnOf();
                            startTimer();
                        }
                }
        }
        if(!game.isGameEnded())
            sendStatus();

    }

    public synchronized boolean isMyTurn(String sessionID) throws ServerException  {
        if(gameNotStarted())
            throw new ServerException(WAIT_WINDOW,GAME_ERROR);
        if(gameEnded())
            throw new ServerException(ENDED_GAME,GAME_ERROR);
        return game.getCurrentPlayer().getUsername().equals(this.getPlayer(sessionID).getPlayer().getUsername());
    }

    public synchronized String getStatus(String sessionID) throws ServerException{
        OnLinePlayer player=getPlayer(sessionID);
        if(gameNotStarted())
            throw new ServerException(WAIT_WINDOW,GAME_ERROR);
        if(gameEnded())
            throw new ServerException(ENDED_GAME,GAME_ERROR);
        return MessageType.encodeMessage(game.getData(player.getPlayer()).toString(),MessageType.GAME_BOARD);
    }

    private OnLinePlayer getPlayer(String sessionID) throws ServerException{
        List<OnLinePlayer> player=players.parallelStream().filter(x -> x.getServerSession().getID().equals(sessionID))
                .collect(Collectors.toList());
        if(player.size() != 1) {
            logger.log(Level.SEVERE, GAME_VIOLATION);
            throw new ServerException(GAME_VIOLATION_MESSAGE,SERVER_ERROR);
        }
        return player.get(0);
    }

    synchronized boolean reconnect(OnLinePlayer player) {
        if (isPlaying(player) && disconnected.contains(player) && player.getObserver().isAlive()) {
            this.setChanged();
            this.notifyObservers(MessageType.encodeMessage(player.getPlayer().getUsername() + RECONNECTED, MessageType.GENERIC_MESSAGE));
            this.addObserver(player.getObserver());
            this.disconnected.remove(player);
            if (this.countObservers() == 0)
                return false;
        }
        player.getObserver().update(this, game.getData(player.getPlayer()).toString());
        return true;
    }

    private void closeGame(){
        this.server.closeGame(this);
        this.timer.cancel(true);
        this.beeperHandle.cancel(true);
        this.activeGame = false;
    }
    synchronized boolean isPlaying(OnLinePlayer player){
        return players.stream().filter(x-> x.equals(player)).count() == 1;
    }

    private void isTurnOf(){
        setChanged();
        notifyObservers(MessageType.encodeMessage(game.getCurrentPlayer().getUsername(),MessageType.CURRENT_PLAYER));
    }

    private void startTimer(){
        Runnable task= this::timerPass;
        timer=scheduler.schedule(task,settings.turnTime,MILLISECONDS);
    }

    private void sendStatus(){
        players.parallelStream().forEach(x -> x.getObserver().update(this, MessageType.encodeMessage(this.game.getData(x.getPlayer()).toString(),MessageType.GAME_BOARD)));
    }
    private synchronized void timerPass(){
        boolean notPass=true;
        while(notPass) {
            try {
                game.executeCommand(game.getCurrentPlayer(), Commands.PASS);
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
        for (OnLinePlayer p : players)
            out = out.concat(p.getPlayer().getUsername() + ":" + Integer.toString(game.getScore().get(p.getPlayer())) + "\n");
        this.setChanged();
        this.notifyObservers(MessageType.encodeMessage(out,MessageType.GAME_STATS));
    }

    List<OnLinePlayer> getPlayers(){
        return new ArrayList<>(players);
    }

    private boolean gameEnded(){
        return !activeGame;
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
        return super.toString();
    }
}
