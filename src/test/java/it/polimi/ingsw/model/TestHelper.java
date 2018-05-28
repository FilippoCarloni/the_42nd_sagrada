package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commands.IllegalCommandException;
import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.ToolCard;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolDeck;
import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;
import it.polimi.ingsw.model.gamedata.ConcreteGameData;
import it.polimi.ingsw.model.gamedata.Game;
import it.polimi.ingsw.model.gamedata.GameData;
import it.polimi.ingsw.model.players.ConcretePlayer;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Shade;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestHelper {

    public static List<Player> init(int numOfPlayers) {
        List<Player> players = new ArrayList<>();
        Deck po = new PrivateObjectiveDeck();
        Deck wf = new WindowFrameDeck();
        for (int i = 0; i < numOfPlayers; i++) {
            Player p = new ConcretePlayer("player" + i);
            p.setWindowFrame((WindowFrame) wf.draw());
            p.setPrivateObjective((PrivateObjectiveCard) po.draw());
            players.add(p);
        }
        return players;
    }

    public static void wrappedIllegalCommand(Game g, Player player, String cmd) {
        assertThrows(IllegalCommandException.class, () -> g.executeCommand(player, cmd));
        try {
            g.executeCommand(player, cmd);
        } catch (IllegalCommandException e) {
            System.out.println("---> " + player.getUsername() + " on <" + cmd + ">\n" + e.getMessage());
        }
    }

    public static void wrappedLegalCommand(Game g, Player player, String cmd) {
        try {
            g.executeCommand(player, cmd);
            g.undoCommand();
            g.redoCommand();
            try {
                new ConcreteGameData((JSONObject) new org.json.simple.parser.JSONParser().parse(g.getData().encode().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
                assertTrue(false);
            }
        } catch (IllegalCommandException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public static void wrappedPass(Game g, Player player) {
        try {
            g.executeCommand(player, "pass");
        } catch (IllegalCommandException e) {
            e.printStackTrace();
            System.out.println();
            assertTrue(false);
        }
    }

    public static WindowFrame getFrame(String name) {
        Deck d = new WindowFrameDeck();
        while (d.size() > 0) {
            WindowFrame w = (WindowFrame) d.draw();
            if (w.getName().equals(name))
                return w;
        }
        throw new IllegalArgumentException("This map does not exist.");
    }

    public static void setDicePool(GameData gd, String serDicePool) {
        List<Die> dp = new ArrayList<>();
        for (int i = 0; i < serDicePool.length(); i += 2) {
            Die d = gd.getDiceBag().pick();
            d.setColor(Color.findByID("" + serDicePool.charAt(i)));
            d.setShade(Shade.findByID("" + serDicePool.charAt(i + 1)));
            dp.add(d);
        }
        gd.getDicePool().clear();
        gd.getDicePool().addAll(dp);
    }

    public static void setToolCard(GameData g, String card) {
        Deck d = new ToolDeck();
        ToolCard tc = (ToolCard) d.draw();
        while (!tc.getName().equals(card))
            tc = (ToolCard) d.draw();
        g.getTools().set(0, tc);
    }
}
