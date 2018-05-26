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
import it.polimi.ingsw.model.utility.Parameters;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class ConcreteGameData implements GameData {

    // TODO: correct the tool cards and commands interfaces

    private RoundTrack roundTrack;
    private DiceBag diceBag;
    private List<Die> dicePool;
    private List<PublicObjectiveCard> publicObjectives;
    private List<ToolCard> tools;
    private List<Player> players;
    private TurnManager turnManager;
    private Die pickedDie;
    private boolean diePlaced;
    private int activeToolID;
    private int passiveToolID;
    private boolean toolActivated;
    private List<Die> diceMoved;
    private boolean undoAvailable;

    public ConcreteGameData(List<Player> players) {
        GameDataFactory factory = new GameDataFactory(this);
        factory.checkPlayersCorrectness(players);
        roundTrack = factory.getRoundTrack();
        diceBag = factory.getDiceBag();
        dicePool = new ArrayList<>();
        publicObjectives = factory.getPublicObjectives();
        tools = new ArrayList<>(); // TODO: change this to correct draw of tool cards
        this.players = players.stream().map(p -> new ConcretePlayer(p.encode())).collect(Collectors.toList());
        turnManager = factory.getTurnManager();
        fillDicePool();
        clear();
    }

    public ConcreteGameData(JSONObject obj) {
        players = new ArrayList<>();
        JSONArray playerList = (JSONArray) obj.get("players");
        for (Object o : playerList)
            players.add(new ConcretePlayer((JSONObject) o));
        dicePool = new ArrayList<>();
        JSONArray dicePoolList = (JSONArray) obj.get("dice_pool");
        for (Object o : dicePoolList)
            dicePool.add(new PlasticDie((JSONObject) o));
        diceBag = new ArrayDiceBag((JSONObject) obj.get("dice_bag"));
        roundTrack = new PaperRoundTrack((JSONObject) obj.get("round_track"));
        publicObjectives = new ArrayList<>();
        JSONArray poList = (JSONArray) obj.get("public_objectives");
        for (Object o : poList)
            publicObjectives.add(PublicObjectiveCard.getCardFromJSON((JSONObject) o));
        tools = new ArrayList<>();
        /* TODO: uncomment this when tool cards are integrated
        JSONArray toolList = (JSONArray) obj.get("tools");
        for (Object o : toolList)
            tools.add(ToolCard.getCardFromJSON((JSONObject) o, this));
        */
        turnManager = new ArrayTurnManager((JSONObject) obj.get("turn_manager"));
        pickedDie = (Die) obj.get("picked_die");
        diePlaced = (boolean) obj.get("die_placed");
        activeToolID = parseInt(obj.get("active_tool_ID").toString());
        passiveToolID = parseInt(obj.get("passive_tool_ID").toString());
        toolActivated = (boolean) obj.get("tool_activated");
        diceMoved = new ArrayList<>();
        JSONArray diceMovedList = (JSONArray) obj.get("dice_moved");
        for (Object o : diceMovedList)
            diceMoved.add(new PlasticDie((JSONObject) o));
        undoAvailable = (boolean) obj.get("undo_available");
    }

    private void clear() {
        pickedDie = null;
        diePlaced = false;
        activeToolID = 0;
        passiveToolID = 0;
        toolActivated = false;
        diceMoved = new ArrayList<>();
    }

    private void fillDicePool() {
        if (turnManager.isRoundStarting())
            dicePool = diceBag.pick(players.size() * 2 + 1);
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
        for (Player p : players) {
            int playerScore = 0;
            playerScore += p.getPrivateObjective().getValuePoints(p.getWindowFrame());
            for (PublicObjectiveCard c : publicObjectives)
                playerScore += c.getValuePoints(p.getWindowFrame());
            playerScore += p.getFavorPoints();
            WindowFrame w = p.getWindowFrame();
            int emptySlots = Parameters.MAX_COLUMNS * Parameters.MAX_ROWS - w.getDice().size();
            playerScore -= emptySlots;
            if (playerScore < 0) playerScore = 0;
            scoreMap.put(p, playerScore);
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
        return players;
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
        for (Player p : players)
            s = convertToHorizontal(s, p.toString());
        sb.append(s);
        return sb.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject encode() {
        JSONObject obj = new JSONObject();
        JSONArray playerList = new JSONArray();
        playerList.addAll(players.stream().map(Player::encode).collect(Collectors.toList()));
        obj.put("players", playerList);
        JSONArray diceList = new JSONArray();
        diceList.addAll(dicePool.stream().map(Die::encode).collect(Collectors.toList()));
        obj.put("dice_pool", diceList);
        obj.put("dice_bag", diceBag.encode());
        obj.put("round_track", roundTrack.encode());
        JSONArray poList = new JSONArray();
        poList.addAll(publicObjectives.stream().map(Card::encode).collect(Collectors.toList()));
        obj.put("public_objectives", poList);
        JSONArray toolList = new JSONArray();
        toolList.addAll(tools.stream().map(Card::encode).collect(Collectors.toList()));
        obj.put("tools", toolList);
        obj.put("turn_manager", turnManager.encode());
        obj.put("picked_die", pickedDie);
        obj.put("die_placed", diePlaced);
        obj.put("active_tool_ID", activeToolID);
        obj.put("passive_tool_ID", passiveToolID);
        obj.put("tool_activated", toolActivated);
        JSONArray diceMovedThisTurn = new JSONArray();
        diceMovedThisTurn.addAll(diceMoved.stream().map(Die::encode).collect(Collectors.toList()));
        obj.put("dice_moved", diceMovedThisTurn);
        obj.put("undo_available", undoAvailable);
        return obj;
    }
}
