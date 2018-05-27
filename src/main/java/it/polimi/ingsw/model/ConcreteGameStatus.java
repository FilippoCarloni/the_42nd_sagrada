package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commands.CommandManager;
import it.polimi.ingsw.model.gameboard.cards.Card;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.ToolCard;
import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.dice.PlasticDie;
import it.polimi.ingsw.model.gameboard.roundtrack.PaperRoundTrack;
import it.polimi.ingsw.model.gameboard.roundtrack.RoundTrack;
import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.turns.ArrayTurnManager;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.utility.Shade;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.turns.TurnManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

/*
 * @deprecated
 */
public class ConcreteGameStatus implements GameStatus {

    private List<Player> players;
    private List<Die> dicePool;
    private DiceBag diceBag;
    private RoundTrack roundTrack;
    private List<PublicObjectiveCard> publicObjectives;
    private List<ToolCard> tools;
    private TurnManager turnManager;
    private CommandManager commandManager;
    private StateHolder stateHolder;

    public ConcreteGameStatus(List<Player> lobbyPlayers) {
        GameStatusBuilder gsb = new GameStatusBuilder();
        players = gsb.getPlayers(lobbyPlayers);
        initialize();
    }

    public ConcreteGameStatus(JSONObject obj) {
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
        JSONArray toolList = (JSONArray) obj.get("tools");
        for (Object o : toolList)
            tools.add(ToolCard.getCardFromJSON((JSONObject) o, this));
        turnManager = new ArrayTurnManager((JSONObject) obj.get("turn_manager"));
    }

    public ConcreteGameStatus(String[] names, String[] windows, String[] toolNames) {
        GameStatusBuilder gsb = new GameStatusBuilder();
        players = gsb.getPlayers(names, windows);
        initialize();
        tools = gsb.getTools(toolNames, this);
    }

    private void initialize() {
        if (players == null)
            throw new NullPointerException("You must initialize players first.");
        GameStatusBuilder gsb = new GameStatusBuilder();
        dicePool = new ArrayList<>();
        diceBag = gsb.getDiceBag();
        roundTrack = gsb.getRoundTrack();
        publicObjectives = gsb.getPublicObjectives(Parameters.PUBLIC_OBJECTIVES);
        tools = gsb.getTools(Parameters.TOOL_CARDS, this);
        turnManager = gsb.getTurnManager(players);
        //commandManager = gsb.getCommandManager(this);
        stateHolder = gsb.getStateHolder();
        fillDicePool();
    }

    public void fillDicePool() {
        if (turnManager.isRoundStarting())
            dicePool = diceBag.pick(players.size() * 2 + 1);
    }

    public void setDicePool(String serializedDicePool) {
        List<Die> dp = new ArrayList<>();
        for (int i = 0; i < serializedDicePool.length(); i += 2) {
            Die d = diceBag.pick();
            d.setColor(Color.findByID("" + serializedDicePool.charAt(i)));
            d.setShade(Shade.findByID("" + serializedDicePool.charAt(i + 1)));
            dp.add(d);
        }
        dicePool = dp;
    }

    public void emptyDicePool() {
        if (turnManager.isRoundEnding()) {
            roundTrack.put(dicePool);
            dicePool = new ArrayList<>();
        }
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public StateHolder getStateHolder() {
        return stateHolder;
    }

    public List<Die> getDicePool() {
        return dicePool;
    }

    public List<ToolCard> getTools() {
        return tools;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    public DiceBag getDiceBag() {
        return diceBag;
    }

    @Override
    public boolean isMyTurn(Player me) {
        return me.equals(turnManager.getCurrentPlayer());
    }

    @Override
    public boolean isLegal(Player player, String command) {
        //return player.equals(turnManager.getCurrentPlayer()) && commandManager.isLegal(command);
        return false;
    }

    @Override
    public void execute(Player player, String command) {
        if (isLegal(player, command)) {
            //commandManager.execute(command);
        }
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
        if (stateHolder != null && stateHolder.getDieHolder() != null) sb.append(stateHolder.getDieHolder());
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
        return obj;
    }
}
