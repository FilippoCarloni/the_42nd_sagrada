package it.polimi.ingsw.model.turns;

import it.polimi.ingsw.model.utility.JSONFactory;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.players.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    public ArrayTurnManager(boolean roundStarting, boolean roundEnding, List<Player> players, List<Player> playerTurns, int turnIndex, int firstPlayerIndex) {
        this.roundStarting = roundStarting;
        this.roundEnding = roundEnding;
        this.players = players.stream().map(p -> JSONFactory.getPlayer(p.encode())).collect(Collectors.toList());
        this.playerTurns = new ArrayList<>();
        for (Player p : playerTurns)
            this.playerTurns.add(this.players.get(this.players.indexOf(p)));
        this.turnIndex = turnIndex;
        this.firstPlayerIndex = firstPlayerIndex;
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
    public List<Player> getPlayers() {
        return players;
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
        obj.put(JSONTag.ROUND_STARTING, roundStarting);
        obj.put(JSONTag.ROUND_ENDING, roundEnding);
        obj.put(JSONTag.TURN_INDEX, turnIndex);
        obj.put(JSONTag.FIRST_PLAYER_INDEX, firstPlayerIndex);
        JSONArray playerList = new JSONArray();
        for (Player p : players)
            playerList.add(p.encode());
        obj.put(JSONTag.PLAYERS, playerList);
        playerList = new JSONArray();
        for (Player p : playerTurns)
            playerList.add(p.encode());
        obj.put(JSONTag.PLAYER_TURNS, playerList);
        return obj;
    }
}
