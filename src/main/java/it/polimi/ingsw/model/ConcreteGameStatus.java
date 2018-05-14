package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commands.CommandManager;
import it.polimi.ingsw.model.commands.ConcreteCommandManager;
import it.polimi.ingsw.model.gameboard.GameBox;
import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.ToolCard;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolDeck;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.roundtrack.RoundTrack;
import it.polimi.ingsw.model.gameboard.utility.Parameters;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.turns.ConcreteTurnManager;
import it.polimi.ingsw.model.turns.TurnManager;

import java.util.*;

public class ConcreteGameStatus implements GameStatus {

    private List<Player> players;
    private List<Die> dicePool;
    private DiceBag diceBag;
    private RoundTrack roundTrack;
    private List<PublicObjectiveCard> publicObjectives;
    private List<ToolCard> tools;
    private ConcreteTurnManager turnManager;
    private ConcreteCommandManager commandManager;
    private StateHolder stateHolder;

    public ConcreteGameStatus(List<Player> players) {
        initialize(players);
        turnManager.advanceTurn();
    }

    private void initialize(List<Player> players) {
        if (players == null || players.size() < 2)
            throw new NullPointerException("Not enough players to start a new game.");
        GameBox gb = GameBox.open();
        this.players = players;
        diceBag = gb.getDiceBag();
        dicePool = new ArrayList<>();
        roundTrack = gb.getRoundTrack();
        publicObjectives = gb.getPublicObjectives();
        List<WindowFrame> frames = gb.getWindowFrames(players.size());
        for (Player p : this.players)
            if (p.getWindowFrame() == null)
                p.setWindowFrame(frames.remove(0));
        List<PrivateObjectiveCard> po = gb.getPrivateObjectives(players.size());
        for (Player p : this.players)
            p.setPrivateObjective(po.remove(0));
        tools = new ArrayList<>();
        Deck d = new ToolDeck(this);
        for (int i = 0; i < Parameters.TOOL_CARDS; i++)
            tools.add((ToolCard) d.draw());
        turnManager = new ConcreteTurnManager(players);
        stateHolder = new StateHolder();
        stateHolder.clear();
        commandManager = new ConcreteCommandManager(this);
        fillDicePool();
    }

    public void fillDicePool() {
        if (turnManager.isRoundStarting())
            dicePool = diceBag.pick(players.size() * 2 + 1);
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

    public CommandManager getCommandManager() {
        return commandManager;
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

    @Override
    public boolean isMyTurn(Player me) {
        return me.equals(turnManager.getCurrentPlayer());
    }

    @Override
    public boolean isLegal(Player player, String command) {
        return player.equals(turnManager.getCurrentPlayer()) && commandManager.isLegal(command);
    }

    @Override
    public void execute(Player player, String command) {
        if (isLegal(player, command)) {
            commandManager.execute(command);
        }
    }

    private String convertToHorizontal(String s1, String s2, String separator) {
        if (s1 == null || s2 == null || separator == null)
            throw new NullPointerException("Strings can't be null.");
        if (s1.length() == 0) return s2;
        if (s2.length() == 0) return s1;
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
        sb.append("DICE POOL: ");
        for (Die d : dicePool)
            sb.append(d);
        sb.append("\n");
        sb.append("PICKED DIE: ");
        if (stateHolder.getDieHolder() != null) sb.append(stateHolder.getDieHolder());
        sb.append("\n\n");
        String s = "";
        for (PublicObjectiveCard c : publicObjectives)
            s = convertToHorizontal(s, c.toString(), "  ");
        for (ToolCard c : tools)
            s = convertToHorizontal(s, c.toString(), "  ");
        sb.append(s);
        sb.append("\n\n");
        s = "";
        for (Player p : players)
            s = convertToHorizontal(s, p.toString(), "  ");
        sb.append(s);
        return sb.toString();
    }
}
