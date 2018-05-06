package it.polimi.ingsw.model.turns;

import it.polimi.ingsw.model.players.Player;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class ConcreteTurnManager implements TurnManager {

    private boolean roundStarting;
    private boolean roundEnding;

    private Deque<Player> turnsCWiseOrder;
    private Deque<Player> turnsCCWiseOrder;
    private Player firstOfThisRound;
    private boolean isCWiseOrder;
    private Player currentPlayingPlayer;

    public ConcreteTurnManager(List<Player> players) {
        if (players == null || players.size() < 2)
            throw new IllegalArgumentException("Invalid list of players.");
        turnsCWiseOrder = new ArrayDeque<>(players);
        turnsCCWiseOrder = new ArrayDeque<>();
        roundStarting = true;
        roundEnding = false;
    }

    private Player getNextPlayer() {
        Player p;
        roundEnding = false;
        roundStarting = false;
        if (turnsCCWiseOrder.isEmpty()) {
            roundStarting = true;
            isCWiseOrder = true;
            firstOfThisRound = turnsCWiseOrder.pop();
            turnsCCWiseOrder.push(firstOfThisRound);
            return firstOfThisRound;
        }
        if (turnsCWiseOrder.isEmpty()) {
            isCWiseOrder = false;
            turnsCWiseOrder.push(firstOfThisRound);
            p = turnsCCWiseOrder.pop();
            turnsCWiseOrder.push(p);
            return p;
        }
        if (isCWiseOrder) {
            p = turnsCWiseOrder.pop();
            turnsCCWiseOrder.push(p);
            return p;
        }
        p = turnsCCWiseOrder.pop();
        if (!turnsCCWiseOrder.isEmpty()) {
            turnsCWiseOrder.push(p);
            return p;
        }
        roundEnding = true;
        isCWiseOrder = true;
        p = firstOfThisRound;
        firstOfThisRound = null;
        return p;
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayingPlayer;
    }

    @Override
    public void advanceTurn() {
        currentPlayingPlayer = getNextPlayer();
    }

    @Override
    public boolean isRoundStarting() {
        return roundStarting;
    }

    @Override
    public boolean isRoundEnding() {
        return roundEnding;
    }
}
