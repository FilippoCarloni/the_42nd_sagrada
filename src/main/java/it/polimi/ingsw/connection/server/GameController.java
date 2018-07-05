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

import static it.polimi.ingsw.connection.costraints.ServerMessage.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * The GameController class is the controller of the game.
 * It manages all the call to the game from te OnlinePlayer after have checked if they can do the specific action.
 * It executes on multiple threads to perform periodic update or to do periodic check on the OnLinePlayer activity.
 * the main issues in do actions can be: a no playing player try to do an action, an action is do in a wrong moment
 * the action is wrong.
 * */
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

    /**
     * Creates a new GameController.
     * @param server - The CentralServer references.
     * @param players - The player in the match.
     */
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
                    this.notifyObservers(MessageType.encodeMessage(WIN_ONE_PLAYER, MessageType.GENERIC_MESSAGE));
                    this.printScore();
                    this.deleteObservers();
                    closeGame();
                }
            }
        }
        ;
        beeperHandle=scheduler.scheduleAtFixedRate(cleaner, 0, settings.gameRefresh, MILLISECONDS);
        startTimer();
        scheduler.schedule(this::setWindowsFrame,10000,MILLISECONDS );
    }

    /**
     * Set the map for the specific player associated to the sessionID.
     * @param sessionID - The sessionID of the player.
     * @param window - The window number to set.
     * @throws ServerException - Throws a ServerException if there are issues.
     */
    public synchronized void setMap(String sessionID,int window) throws ServerException {
        OnLinePlayer player=this.getPlayer(sessionID);
        if(gameNotStarted()&&window>=1&&window<=Parameters.NUM_OF_WINDOWS_PER_PLAYER_BEFORE_CHOICE)
            windowChoices.set(players.indexOf(player),window);
    }

    /**
     * Set all the window frames for all the and starts the match if it is possible.
     */
    private synchronized void setWindowsFrame() {
        for( OnLinePlayer p: this.players){
            p.getPlayer().setWindowFrame(this.windowFrames.get(this.windowChoices.get(this.players.indexOf(p))-1+this.players.indexOf(p)*Parameters.NUM_OF_WINDOWS_PER_PLAYER_BEFORE_CHOICE));
        }
        this.game = new ConcreteGame(this.players
                .stream()
                .map(OnLinePlayer::getPlayer)
                .collect(Collectors.toList()));
        if(!this.gameEnded()) {
            this.sendStatus();
            this.isTurnOf();
        }
    }

    /**
     * Tells if the game is started or not.
     * @return - If the game is started or not.
     */
    private boolean gameNotStarted(){
        return game == null;
    }

    /**
     * Sets the pre-game frames for the player.
     * @param numPlayers -The number of the players in the match.
     */
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

    /**
     * Sends a game action to the game for the specif OnLinePlayer.
     * @param sessionID - The sessioniD associated to the player.
     * @param command - The game action to send.
     * @throws ServerException - Throws a ServerException if there are issues.
     */
    public synchronized void sendCommand(String sessionID, String command) throws ServerException {
        if(gameNotStarted())
            throw new ServerException(WAIT_WINDOW);
        if(gameEnded())
            throw new ServerException(ENDED_GAME);
        if(!isMyTurn(sessionID)) {
            throw new ServerException(NOT_YOUR_TURN);
        }
        command=command.trim();
        switch (command) {
            case Commands.UNDO:
                if(game.isUndoAvailable())
                    game.undoCommand();
                else
                    throw new ServerException(NOT_UNDO);
                break;
            case Commands.REDO:
                if(game.isRedoAvailable())
                    game.redoCommand();
                else
                    throw new ServerException(NOT_REDO);
                break;
            default:
                try {
                    game.executeCommand(this.getPlayer(sessionID).getPlayer(), command);
                } catch (IllegalCommandException e) {
                    throw new ServerException(e.getMessage());
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

    /**
     * Tells if is the turn of the OnlinePlayer associated to the sessionID.
     * @param sessionID - The sessionID of the player.
     * @return - If is the turn of the player or not.
     * @throws ServerException- Throws a ServerException if there are issues.
     */
    public synchronized boolean isMyTurn(String sessionID) throws ServerException  {
        if(gameNotStarted())
            throw new ServerException(WAIT_WINDOW);
        if(gameEnded())
            throw new ServerException(ENDED_GAME);
        return game.getCurrentPlayer().getUsername().equals(this.getPlayer(sessionID).getPlayer().getUsername());
    }

    /**
     *
     * @param sessionID - The sessionID of the OnLinePlayer.
     * @return - The specific status for the player.
     * @throws ServerException- Throws a ServerException if there are issues.
     */
    public synchronized String getStatus(String sessionID) throws ServerException{
        OnLinePlayer player=getPlayer(sessionID);
        if(gameNotStarted())
            throw new ServerException(WAIT_WINDOW);
        if(gameEnded())
            throw new ServerException(ENDED_GAME);
        return MessageType.encodeMessage(game.getData(player.getPlayer()).toString(),MessageType.GAME_BOARD);
    }

    /**
     *
     * @param sessionID - The sessionID of the OnLinePlayer.
     * @return - The relative OnLinePayer associate to the sessionID.
     * @throws ServerException- Throws a ServerException if the player is not enrolled in the match.
     */
    private OnLinePlayer getPlayer(String sessionID) throws ServerException{
        List<OnLinePlayer> player=players.parallelStream().filter(x -> x.getServerSession().getID().equals(sessionID))
                .collect(Collectors.toList());
        if(player.size() != 1) {
            logger.log(Level.SEVERE, GAME_VIOLATION);
            throw new ServerException(GAME_VIOLATION_MESSAGE);
        }
        return player.get(0);
    }

    /**
     * Reconnect the OnLinePlayer to the game if previously disconnected.
     * @param player - The OnLinePlayer to reconnected.
     * @return
     */
    synchronized boolean reconnect(OnLinePlayer player) {
        if (isPlaying(player) && disconnected.contains(player) && player.getObserver().isAlive()) {
            this.setChanged();
            this.notifyObservers(MessageType.encodeMessage(player.getPlayer().getUsername() + RECONNECTED, MessageType.GENERIC_MESSAGE));
            this.addObserver(player.getObserver());
            this.disconnected.remove(player);
            if (this.countObservers() == 0)
                return false;
        }
        if(!gameNotStarted())
            player.getObserver().update(this, MessageType.encodeMessage(game.getData(player.getPlayer()).toString(), MessageType.GAME_BOARD));
        return true;
    }

    /**
     * Closes the match: stops all the periodic task and sets the game ended.
     */
    private synchronized void closeGame(){
        this.server.closeGame(this);
        this.timer.cancel(true);
        this.beeperHandle.cancel(true);
        this.activeGame = false;
    }

    /**
     * Tells if an OnLinePlayer is enrolled in the match.
     * @param player - The player to check.
     * @return - If the player is playing or not in the game.
     */
    synchronized boolean isPlaying(OnLinePlayer player){
        return players.stream().filter(x-> x.equals(player)).count() == 1;
    }

    /**
     * Sends to the active OnLinePlayers which player can play in the turn in which it is called.
     */
    private void isTurnOf(){
        setChanged();
        notifyObservers(MessageType.encodeMessage(game.getCurrentPlayer().getUsername(),MessageType.CURRENT_PLAYER));
    }

    /**
     * Starts the timer for the turn.
     */
    private void startTimer(){
        Runnable task= this::timerPass;
        timer=scheduler.schedule(task,settings.turnTime,MILLISECONDS);
    }

    /**
     * Sends the status of the game to the active OnLinePlayers.
     */
    private void sendStatus(){
        players.parallelStream().forEach(x -> x.getObserver().update(this, MessageType.encodeMessage(this.game.getData(x.getPlayer()).toString(),MessageType.GAME_BOARD)));
    }

    /**
     * Passes the turn, called by a periodic task.
     */
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

    /**
     * Sends the scores to the OnLinePlayer active.
     */
    private void printScore(){
        if (!this.gameNotStarted()){
            this.setChanged();
            this.notifyObservers(MessageType.encodeMessage(game.getScore().toString(), MessageType.GAME_STATS));
        }
    }

    /**
     *
     * @return - The list of the OnLinePlayers enrolled in the match.
     */
    List<OnLinePlayer> getPlayers(){
        return new ArrayList<>(players);
    }

    /**
     * Tells if the game is ended or not.
     * @return - If the game is ended or not.
     */
    private synchronized boolean gameEnded(){
        return !this.activeGame;
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
