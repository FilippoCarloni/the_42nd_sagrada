package it.polimi.ingsw.model.turns;

import it.polimi.ingsw.model.gameboard.utility.Parameters;
import it.polimi.ingsw.model.players.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayTurnManager implements TurnManager {

    private boolean roundStarting;
    private boolean roundEnding;
    private List<Player> players;
    private List<Player> playerTurns;

    private int turnIndex;
    private int firstPlayerIndex;

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
}
