package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameboard.GameBox;
import it.polimi.ingsw.model.gameboard.cards.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.roundtrack.RoundTrack;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.players.Player;

import java.util.*;

public class ConcreteGameStatus implements GameStatus {

    private List<Player> players;
    private List<Die> dicePool;
    private DiceBag diceBag;
    private RoundTrack roundTrack;
    private List<PublicObjectiveCard> publicObjectives;

    private Deque<Player> turnsCWiseOrder;
    private Deque<Player> turnsCCWiseOrder;
    private Player firstOfThisRound;
    private boolean isCWiseOrder;
    private boolean isRoundStarting;
    private Player currentPlayingPlayer;

    public ConcreteGameStatus(List<Player> players) {
        if (players == null || players.size() < 2)
            throw new NullPointerException("Not enough players to start a new game.");
        GameBox gb = GameBox.open();
        this.players = new ArrayList<>(players);
        diceBag = gb.getDiceBag();
        roundTrack = gb.getRoundTrack();
        publicObjectives = gb.getPublicObjectives();
        List<WindowFrame> frames = gb.getWindowFrames(players.size());
        for (Player p : this.players)
            p.setWindowFrame(frames.remove(0));
        List<PrivateObjectiveCard> po = gb.getPrivateObjectives(players.size());
        for (Player p : this.players)
            p.setPrivateObjective(po.remove(0));
        turnsCWiseOrder = new ArrayDeque<>();
        turnsCCWiseOrder = new ArrayDeque<>();
        isRoundStarting = true;
    }

    private void fillDicePool() {
        dicePool = diceBag.pick(players.size() * 2 + 1);
    }

    private Player getNextPlayer() {
        Player p;
        if (turnsCCWiseOrder.isEmpty()) {
            isRoundStarting = true;
            isCWiseOrder = true;
            firstOfThisRound = turnsCWiseOrder.pop();
            turnsCCWiseOrder.push(firstOfThisRound);
            return firstOfThisRound;
        }
        isRoundStarting = false;
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
        isCWiseOrder = true;
        p = firstOfThisRound;
        firstOfThisRound = null;
        return p;
    }

    private void startTurn() {
        currentPlayingPlayer = getNextPlayer();
        if (isRoundStarting) fillDicePool();
    }

    @Override
    public boolean isMyTurn(Player me) {
        return me.equals(currentPlayingPlayer);
    }

    @Override
    public void selectFromDicePool() {

    }

    @Override
    public void placeDie(int row, int column) {

    }
}
