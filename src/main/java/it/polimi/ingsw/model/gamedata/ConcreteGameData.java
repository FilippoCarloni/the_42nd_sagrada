package it.polimi.ingsw.model.gamedata;

import it.polimi.ingsw.model.gameboard.cards.Card;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.ToolCard;
import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.dice.PlasticDie;
import it.polimi.ingsw.model.gameboard.roundtrack.PaperRoundTrack;
import it.polimi.ingsw.model.gameboard.roundtrack.RoundTrack;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.turns.ArrayTurnManager;
import it.polimi.ingsw.model.turns.TurnManager;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.model.utility.Parameters;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

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

    ConcreteGameData(List<Player> players) {
        GameDataFactory factory = new GameDataFactory();
        turnManager = factory.getTurnManager(players);
        roundTrack = factory.getRoundTrack();
        diceBag = factory.getDiceBag();
        dicePool = new ArrayList<>();
        publicObjectives = factory.getPublicObjectives();
        tools = factory.getTools();
        fillDicePool();
        clear();
    }

    public ConcreteGameData(JSONObject obj) {
        dicePool = new ArrayList<>();
        JSONArray dicePoolList = (JSONArray) obj.get(JSONTag.DICE_POOL);
        for (Object o : dicePoolList)
            dicePool.add(new PlasticDie((JSONObject) o));
        diceBag = new ArrayDiceBag((JSONObject) obj.get(JSONTag.DICE_BAG));
        roundTrack = new PaperRoundTrack((JSONObject) obj.get(JSONTag.ROUND_TRACK));
        publicObjectives = new ArrayList<>();
        JSONArray poList = (JSONArray) obj.get(JSONTag.PUBLIC_OBJECTIVES);
        for (Object o : poList)
            publicObjectives.add(PublicObjectiveCard.getCardFromJSON((JSONObject) o));
        tools = new ArrayList<>();
        JSONArray toolList = (JSONArray) obj.get(JSONTag.TOOLS);
        for (Object o : toolList)
            tools.add(ToolCard.getCardFromJSON((JSONObject) o));
        turnManager = new ArrayTurnManager((JSONObject) obj.get(JSONTag.TURN_MANAGER));
        pickedDie = null;
        if (obj.get(JSONTag.PICKED_DIE) != null)
            pickedDie = new PlasticDie((JSONObject) obj.get(JSONTag.PICKED_DIE));
        diePlaced = parseBoolean(obj.get(JSONTag.DIE_PLACED).toString());
        activeToolID = parseInt(obj.get(JSONTag.ACTIVE_TOOL_ID).toString());
        passiveToolID = parseInt(obj.get(JSONTag.PASSIVE_TOOL_ID).toString());
        toolActivated = parseBoolean(obj.get(JSONTag.TOOL_ACTIVATED).toString());
        diceMoved = new ArrayList<>();
        JSONArray diceMovedList = (JSONArray) obj.get(JSONTag.DICE_MOVED);
        for (Object o : diceMovedList)
            diceMoved.add(new PlasticDie((JSONObject) o));
        undoAvailable = parseBoolean(obj.get(JSONTag.UNDO_AVAILABLE).toString());
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
    public Map<Player, Integer> getCurrentScore() {
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
            scoreMap.put(new ConcretePlayer(p.encode()), playerScore);
        }
        return scoreMap;
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

    private String convertToHorizontal(String s1, String s2) {
        if (s1 == null || s2 == null)
            throw new NullPointerException("Strings can't be null.");
        if (s1.length() == 0) return s2;
        if (s2.length() == 0) return s1;
        String separator = "  ";
        StringBuilder sb = new StringBuilder();
        String[] splitS1 = s1.split("\n");
        String[] splitS2 = s2.split("\n");
        int min = splitS1.length > splitS2.length ? splitS2.length : splitS1.length;
        for (int i = 0; i < min; i++) {
            sb.append(splitS1[i]);
            sb.append(separator);
            sb.append(splitS2[i]);
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(roundTrack);
        sb.append("\n\n");
        sb.append("DICE ON ROUND TRACK: ");
        for (Die d : roundTrack.getDice())
            sb.append(d);
        sb.append("\n");
        sb.append("DICE POOL: ");
        for (Die d : dicePool)
            sb.append(d);
        sb.append("\n");
        sb.append("PICKED DIE: ");
        if (pickedDie != null) sb.append(pickedDie);
        sb.append("\n");
        String s = "";
        for (PublicObjectiveCard c : publicObjectives)
            s = convertToHorizontal(s, c.toString());
        for (ToolCard c : tools)
            s = convertToHorizontal(s, c.toString());
        sb.append(s);
        sb.append("\n");
        s = "";
        for (Player p : turnManager.getPlayers())
            s = convertToHorizontal(s, p.toString());
        sb.append(s);
        return sb.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject encode() {
        JSONObject obj = new JSONObject();
        JSONArray playerList = new JSONArray();
        playerList.addAll(turnManager.getPlayers().stream().map(Player::encode).collect(Collectors.toList()));
        obj.put(JSONTag.PLAYERS, playerList);
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
