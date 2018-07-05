package it.polimi.ingsw.model.gamedata;

import it.polimi.ingsw.model.gameboard.cards.Card;
import it.polimi.ingsw.model.gameboard.cards.publicobjectives.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolCard;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.roundtrack.RoundTrack;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.turns.TurnManager;
import it.polimi.ingsw.model.utility.JSONFactory;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.model.utility.Parameters;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implements a GameData interface with a generic structure.
 */
public class ConcreteGameData implements GameData {

    private RoundTrack roundTrack;
    private DiceBag diceBag;
    private List<Die> dicePool;
    private List<PublicObjectiveCard> publicObjectives;
    private List<ToolCard> tools;
    private TurnManager turnManager;
    private Die pickedDie;
    private boolean diePlaced;
    private int activeToolID;
    private int passiveToolID;
    private boolean toolActivated;
    private List<Die> diceMoved;
    private boolean undoAvailable;

    /**
     * Generates a starting game status, provided a valid list of players.
     * In order to be valid, a list of players must have at least 2 players and its size should not
     * exceed 4; players'parameters such as window frame and private objective should be not-null.
     * @param players A List of correctly initialized players
     */
    ConcreteGameData(List<Player> players) {
        turnManager = GameDataFactory.getTurnManager(players);
        roundTrack = GameDataFactory.getRoundTrack();
        diceBag = GameDataFactory.getDiceBag();
        dicePool = new ArrayList<>();
        publicObjectives = GameDataFactory.getPublicObjectives();
        tools = GameDataFactory.getTools();
        fillDicePool();
        clear();
    }

    /**
     * Generates a new game status that encodes the information provided in the parameters.
     * @param roundTrack Current round track
     * @param diceBag Current dice bag
     * @param dicePool Current dice pool
     * @param publicObjectiveCards Public objectives of the game
     * @param tools Tool cards of the game
     * @param turnManager Current turn manager
     * @param pickedDie Currently picked die (could be null)
     * @param diePlaced Boolean true if a die was already placed
     * @param activeToolID Identifies the currently activated ACTIVE tool card
     * @param passiveToolID Identifies the currently activated PASSIVE tool card
     * @param toolActivated Boolean true if there's an active tool card currently
     * @param diceMoved List of moved dice during this turn (could be null)
     * @param undoAvailable Boolean true if the player is allowed to undo his/her last action
     */
    public ConcreteGameData(RoundTrack roundTrack, DiceBag diceBag, List<Die> dicePool,
                            List<PublicObjectiveCard> publicObjectiveCards, List<ToolCard> tools,
                            TurnManager turnManager, Die pickedDie, boolean diePlaced, int activeToolID,
                            int passiveToolID, boolean toolActivated, List<Die> diceMoved, boolean undoAvailable) {
        this.roundTrack = roundTrack;
        this.diceBag = diceBag;
        this.dicePool = new ArrayList<>(dicePool);
        this.publicObjectives = publicObjectiveCards;
        this.tools = tools;
        this.turnManager = turnManager;
        this.pickedDie = pickedDie;
        this.diePlaced = diePlaced;
        this.activeToolID = activeToolID;
        this.passiveToolID = passiveToolID;
        this.toolActivated = toolActivated;
        this.diceMoved = new ArrayList<>(diceMoved);
        this.undoAvailable = undoAvailable;
    }

    private void clear() {
        pickedDie = null;
        diePlaced = false;
        activeToolID = 0;
        passiveToolID = 0;
        toolActivated = false;
        diceMoved = new ArrayList<>();
        undoAvailable = false;
    }

    private void fillDicePool() {
        if (turnManager.isRoundStarting())
            dicePool = diceBag.pick(turnManager.getPlayers().size() * 2 + 1);
    }

    private void emptyDicePool() {
        if (turnManager.isRoundEnding()) {
            assert !dicePool.isEmpty();
            roundTrack.put(dicePool);
            dicePool = new ArrayList<>();
        }
    }

    @Override
    public void advance() {
        emptyDicePool();
        turnManager.advanceTurn();
        fillDicePool();
        clear();
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject getCurrentScore() {
        Map<Player, Integer> scoreMap = new HashMap<>();
        for (Player p : turnManager.getPlayers()) {
            int playerScore = 0;
            playerScore += p.getPrivateObjective().getValuePoints(p.getWindowFrame());
            for (PublicObjectiveCard c : publicObjectives)
                playerScore += c.getValuePoints(p.getWindowFrame());
            playerScore += p.getFavorPoints();
            WindowFrame w = p.getWindowFrame();
            int emptySlots = Parameters.MAX_COLUMNS * Parameters.MAX_ROWS - w.getDice().size();
            playerScore -= emptySlots;
            if (playerScore < 0) playerScore = 0;
            scoreMap.put(JSONFactory.getPlayer(p.encode()), playerScore);
        }
        JSONObject encodedMap = new JSONObject();
        JSONArray players = new JSONArray();
        for (Map.Entry<Player, Integer> entry : scoreMap.entrySet()) {
            JSONObject player = new JSONObject();
            player.put(JSONTag.USERNAME, entry.getKey().getUsername());
            player.put(JSONTag.SCORE, entry.getValue());
            players.add(player);
        }
        encodedMap.put(JSONTag.PLAYERS, players);
        return encodedMap;
    }

    @Override
    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    @Override
    public DiceBag getDiceBag() {
        return diceBag;
    }

    @Override
    public List<Die> getDicePool() {
        return dicePool;
    }

    @Override
    public List<PublicObjectiveCard> getPublicObjectives() {
        return publicObjectives;
    }

    @Override
    public List<ToolCard> getTools() {
        return tools;
    }

    @Override
    public List<Player> getPlayers() {
        return turnManager.getPlayers();
    }

    @Override
    public TurnManager getTurnManager() {
        return turnManager;
    }

    @Override
    public Die getPickedDie() {
        return pickedDie;
    }

    @Override
    public void setPickedDie(Die die) {
        this.pickedDie = die;
    }

    @Override
    public boolean isDiePlaced() {
        return diePlaced;
    }

    @Override
    public void setDiePlaced(boolean diePlaced) {
        this.diePlaced = diePlaced;
    }

    @Override
    public int getActiveToolID() {
        return activeToolID;
    }

    @Override
    public void setActiveToolID(int id) {
        this.activeToolID = id;
    }

    @Override
    public int getPassiveToolID() {
        return passiveToolID;
    }

    @Override
    public void setPassiveToolID(int id) {
        this.passiveToolID = id;
    }

    @Override
    public boolean isToolActivated() {
        return toolActivated;
    }

    @Override
    public void setToolActivated(boolean toolActivated) {
        this.toolActivated = toolActivated;
    }

    @Override
    public List<Die> getDiceMoved() {
        return diceMoved;
    }

    @Override
    public boolean isUndoAvailable() {
        return undoAvailable;
    }

    @Override
    public void setUndoAvailable(boolean available) {
        this.undoAvailable = available;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- GAME DATA DUMP ---:");
        sb.append("\nround track : ").append(roundTrack);
        sb.append("\ndice pool   : ");
        for (Die d : dicePool) sb.append(d);
        sb.append("\npicked die  : ").append(pickedDie);
        sb.append("\ndie placed  : ").append(diePlaced);
        sb.append("\nactive  ID  : ").append(activeToolID);
        sb.append("\npassive ID  : ").append(passiveToolID);
        sb.append("\ntool act    : ").append(toolActivated);
        sb.append("\ndice moved  : ");
        for (Die d : diceMoved) sb.append(d);
        sb.append("\nundo av     : ").append(undoAvailable);
        sb.append("\npub obj     :\n");
        for (PublicObjectiveCard c : publicObjectives) sb.append(c).append("\n");
        sb.append("tool cards  :\n");
        for (ToolCard c : tools) sb.append(c).append("\n");
        sb.append("players     :\n");
        for (Player p : turnManager.getPlayers()) sb.append(p).append("\n");
        return sb.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject encode() {
        JSONObject obj = new JSONObject();
        JSONArray diceList = new JSONArray();
        diceList.addAll(dicePool.stream().map(Die::encode).collect(Collectors.toList()));
        obj.put(JSONTag.DICE_POOL, diceList);
        obj.put(JSONTag.DICE_BAG, diceBag.encode());
        obj.put(JSONTag.ROUND_TRACK, roundTrack.encode());
        JSONArray poList = new JSONArray();
        poList.addAll(publicObjectives.stream().map(Card::encode).collect(Collectors.toList()));
        obj.put(JSONTag.PUBLIC_OBJECTIVES, poList);
        JSONArray toolList = new JSONArray();
        toolList.addAll(tools.stream().map(Card::encode).collect(Collectors.toList()));
        obj.put(JSONTag.TOOLS, toolList);
        obj.put(JSONTag.TURN_MANAGER, turnManager.encode());
        obj.put(JSONTag.PICKED_DIE, pickedDie == null ? null : pickedDie.encode());
        obj.put(JSONTag.DIE_PLACED, diePlaced);
        obj.put(JSONTag.ACTIVE_TOOL_ID, activeToolID);
        obj.put(JSONTag.PASSIVE_TOOL_ID, passiveToolID);
        obj.put(JSONTag.TOOL_ACTIVATED, toolActivated);
        JSONArray diceMovedThisTurn = new JSONArray();
        diceMovedThisTurn.addAll(diceMoved.stream().map(Die::encode).collect(Collectors.toList()));
        obj.put(JSONTag.DICE_MOVED, diceMovedThisTurn);
        obj.put(JSONTag.UNDO_AVAILABLE, undoAvailable);
        return obj;
    }
}
