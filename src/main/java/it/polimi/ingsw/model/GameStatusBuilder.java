package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commands.CommandManager;
import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.ToolCard;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.gameboard.cards.publicobjectives.PublicObjectiveDeck;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolDeck;
import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.roundtrack.PaperRoundTrack;
import it.polimi.ingsw.model.gameboard.roundtrack.RoundTrack;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.turns.ArrayTurnManager;
import it.polimi.ingsw.model.turns.TurnManager;

import java.util.ArrayList;
import java.util.List;

/*
 * @deprecated
 */
public final class GameStatusBuilder {

    public RoundTrack getRoundTrack() {
        return new PaperRoundTrack();
    }

    public DiceBag getDiceBag() {
        return new ArrayDiceBag();
    }

    /*public CommandManager getCommandManager(ConcreteGameStatus status) {
        return new ConcreteCommandManager(status);
    }*/

    public StateHolder getStateHolder() {
        return new StateHolder();
    }

    public TurnManager getTurnManager(List<Player> players) {
        return new ArrayTurnManager(players);
    }

    public List<PublicObjectiveCard> getPublicObjectives(int numOfPublicObjectives) {
        if (numOfPublicObjectives < 0)
            throw new IllegalArgumentException("Negative number of cards.");
        List<PublicObjectiveCard> po = new ArrayList<>();
        Deck d = new PublicObjectiveDeck();
        for (int i = 0; i < numOfPublicObjectives; i++)
            po.add((PublicObjectiveCard) d.draw());
        return po;
    }

    public List<ToolCard> getTools(int numOfTools, ConcreteGameStatus status) {
        if (numOfTools < 0)
            throw new IllegalArgumentException("Negative number of cards.");
        List<ToolCard> tools = new ArrayList<>();
        Deck d = new ToolDeck(status);
        for (int i = 0; i < numOfTools; i++)
            tools.add((ToolCard) d.draw());
        return tools;
    }

    public List<ToolCard> getTools(String[] names, ConcreteGameStatus status) {
        if (names == null)
            throw new NullPointerException("Names cannot be null");
        if (names.length <= 0)
            throw new IllegalArgumentException("Not enough tools.");
        Deck d;
        List<ToolCard> tc = new ArrayList<>();
        for (String s : names) {
            d = new ToolDeck(status);
            ToolCard c = (ToolCard) d.draw();
            while (!c.getName().equals(s))
                c = (ToolCard) d.draw();
            tc.add(c);
        }
        return tc;
    }

    public List<Player> getPlayers(List<Player> players) {
        if (players == null || players.size() < 2)
            throw new IllegalArgumentException("Not enough player to start a new match.");
        Deck wf = new WindowFrameDeck();
        Deck po = new PrivateObjectiveDeck();
        for (Player p : players) {
            if (p.getWindowFrame() == null)
                p.setWindowFrame((WindowFrame) wf.draw());
            if (p.getPrivateObjective() == null)
                p.setPrivateObjective((PrivateObjectiveCard) po.draw());
        }
        return players;
    }

    public List<Player> getPlayers(String[] names, String[] windows) {
        if (names == null || windows == null)
            throw new NullPointerException("You should specify player names and windows.");
        if (names.length != windows.length)
            throw new IllegalArgumentException("Names and windows should be the same number.");
        List<Player> players = new ArrayList<>();
        Deck d;
        for (int i = 0; i < names.length; i++) {
            Player p = new ConcretePlayer(names[i]);
            d = new WindowFrameDeck();
            WindowFrame w = (WindowFrame) d.draw();
            while (!windows[i].equals(w.getName()))
                w = (WindowFrame) d.draw();
            p.setWindowFrame(w);
            players.add(p);
        }
        return players;
    }
}
