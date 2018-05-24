package it.polimi.ingsw.model.turns;

import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.players.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.Integer.parseInt;

public class ArrayTurnManager implements TurnManager {

    private boolean roundStarting;
    private boolean roundEnding;
    private List<Player> players;
    private List<Player> playerTurns;

    private int turnIndex;
    private int firstPlayerIndex;

    /**
     * Generates a Sagrada turn manager that holds the drafting logic.
     * It is implemented with an array-like structure.
     */
    public ArrayTurnManager(List<Player> players) {
        if (players == null)
            throw new NullPointerException("Cannot manage turns on a null list of players.");
        if (players.size() < 2 || players.size() > Parameters.MAX_PLAYERS)
            throw new IllegalArgumentException("Illegal number of players.");
        this.players = players;
        playerTurns = new ArrayList<>();
        roundEnding = true;
        firstPlayerIndex = players.size() - 1;
        turnIndex = players.size() * 2 - 1;
        advanceTurn();
    }

    /**
     * Generates a clone of the turn manager represented with JSON syntax.
     * @param obj A JSON Object that holds TurnManager-like information
     */
    public ArrayTurnManager(JSONObject obj) {
        roundStarting = (boolean) obj.get("round_starting");
        roundEnding = (boolean) obj.get("round_ending");
        turnIndex = parseInt(obj.get("turn_index").toString());
        firstPlayerIndex = parseInt(obj.get("first_player_index").toString());
        players = new ArrayList<>();
        playerTurns = new ArrayList<>();
        JSONArray playerList = (JSONArray) obj.get("players");
        for (Object o : playerList)
            players.add(new ConcretePlayer((JSONObject) o));
        playerList = (JSONArray) obj.get("player_turns");
        for (Object o : playerList)
            for (Player p : players)
                if (p.getUsername().equals(((JSONObject) o).get("username")))
                    playerTurns.add(p);
        for (Player p : playerTurns)
            assert players.contains(p);
    }

    private void updateRoundStatus() {
        roundStarting = false;
        roundEnding = false;
        if (turnIndex == 0) roundStarting = true;
        if (turnIndex == players.size() * 2 - 1) roundEnding = true;
    }

    private void initializeOrder() {
        playerTurns.clear();
        for (int i = 0; i < players.size(); i++)
            playerTurns.add(players.get((firstPlayerIndex + i) % players.size()));
        for (int i = players.size() - 1; i >= 0; i--)
            playerTurns.add(players.get((firstPlayerIndex + i) % players.size()));
    }

    @Override
    public Player getCurrentPlayer() {
        return playerTurns.get(turnIndex);
    }

    @Override
    public void advanceTurn() {
        turnIndex = (turnIndex + 1) % (players.size() * 2);
        updateRoundStatus();
        if (roundStarting) {
            firstPlayerIndex = (firstPlayerIndex + 1) % players.size();
            initializeOrder();
        }
    }

    @Override
    public boolean isRoundStarting() {
        return roundStarting;
    }

    @Override
    public boolean isRoundEnding() {
        return roundEnding;
    }

    @Override
    public boolean isSecondTurn() {
        for (int i = 0; i < turnIndex; i++)
            if (playerTurns.get(i).equals(playerTurns.get(turnIndex)))
                return true;
        return false;
    }

    @Override
    public void takeTwoTurns() {
        for (int i = turnIndex + 1; i < playerTurns.size(); i++) {
            if (playerTurns.get(i).equals(playerTurns.get(turnIndex))) {
                Collections.swap(playerTurns, i, turnIndex + 1);
                return;
            }
        }
        throw new NoSuchElementException("Current player already played two times during this round.");
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject encode() {
        JSONObject obj = new JSONObject();
        obj.put("round_starting", roundStarting);
        obj.put("round_ending", roundEnding);
        obj.put("turn_index", turnIndex);
        obj.put("first_player_index", firstPlayerIndex);
        JSONArray playerList = new JSONArray();
        for (Player p : players)
            playerList.add(p.encode());
        obj.put("players", playerList);
        playerList = new JSONArray();
        for (Player p : playerTurns)
            playerList.add(p.encode());
        obj.put("player_turns", playerList);
        return obj;
    }
}
