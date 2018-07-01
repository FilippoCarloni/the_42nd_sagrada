package it.polimi.ingsw.model.gamedata;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.publicobjectives.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolCard;
import it.polimi.ingsw.model.gameboard.cards.publicobjectives.PublicObjectiveDeck;
import it.polimi.ingsw.model.gameboard.cards.tools.ToolDeck;
import it.polimi.ingsw.model.gameboard.dice.ArrayDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.roundtrack.PaperRoundTrack;
import it.polimi.ingsw.model.gameboard.roundtrack.RoundTrack;
import it.polimi.ingsw.model.players.Player;
import it.polimi.ingsw.model.turns.ArrayTurnManager;
import it.polimi.ingsw.model.turns.TurnManager;
import it.polimi.ingsw.model.utility.Parameters;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.utility.ExceptionMessage.*;

/**
 * Generates all the class instances needed in a GameData object.
 */
class GameDataFactory {

    private GameDataFactory() {}

    /**
     * Generates an empty round track.
     * @return A RoundTrack object
     */
    public static RoundTrack getRoundTrack() {
        return new PaperRoundTrack();
    }

    /**
     * Generates a 90-dice dice bag.
     * @return A DiceBag object
     */
    static DiceBag getDiceBag() {
        return new ArrayDiceBag();
    }

    /**
     * Generates a turn manager in a starting state.
     * @param players The List of players of the game
     * @return A TurnManager object adapted to the current players
     */
    public static TurnManager getTurnManager(List<Player> players) {
        checkPlayersCorrectness(players);
        return new ArrayTurnManager(players);
    }

    /**
     * Picks randomly 3 public objectives.
     * @return A List of 3 random public objectives
     */
    static List<PublicObjectiveCard> getPublicObjectives() {
        List<PublicObjectiveCard> pos = new ArrayList<>();
        Deck d = new PublicObjectiveDeck();
        for (int i = 0; i < Parameters.PUBLIC_OBJECTIVES; i++)
            pos.add((PublicObjectiveCard) d.draw());
        return pos;
    }

    /**
     * Picks randomly 3 tool cards.
     * @return A List of 3 random tool cards
     */
    public static List<ToolCard> getTools() {
        List<ToolCard> tcs = new ArrayList<>();
        Deck d = new ToolDeck();
        for (int i = 0; i < Parameters.TOOL_CARDS; i++)
            tcs.add((ToolCard) d.draw());
        return tcs;
    }

    private static void checkPlayersCorrectness(List<Player> players) {
        if (players == null)
            throw new NullPointerException(NULL_PARAMETER);
        if (players.size() <= 1 || players.size() > Parameters.MAX_PLAYERS)
            throw new IllegalArgumentException(INDEX_OUT_OF_BOUND);
        for (Player p : players) {
            if (p.getWindowFrame() == null)
                throw new IllegalArgumentException(NULL_REFERENCE);
            if (p.getPrivateObjective() == null)
                throw new IllegalArgumentException(NULL_REFERENCE);
        }
        for (int i = 0; i < players.size(); i++)
            for (int j = i + 1; j < players.size(); j++)
                if (players.get(i).getUsername().equals(players.get(j).getUsername()))
                    throw new IllegalArgumentException(PLAYER_SAME_NAME);
    }
}
