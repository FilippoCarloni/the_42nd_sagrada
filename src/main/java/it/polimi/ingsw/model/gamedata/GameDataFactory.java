package it.polimi.ingsw.model.gamedata;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.publicobjectives.PublicObjectiveDeck;
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

class GameDataFactory {

    private GameData gameData;

    GameDataFactory(GameData gameData) {
        this.gameData = gameData;
    }

    public RoundTrack getRoundTrack() {
        return new PaperRoundTrack();
    }

    public DiceBag getDiceBag() {
        return new ArrayDiceBag();
    }

    public TurnManager getTurnManager() {
        assert gameData.getPlayers() != null && gameData.getPlayers().size() > 1;
        return new ArrayTurnManager(gameData.getPlayers());
    }

    public List<PublicObjectiveCard> getPublicObjectives() {
        List<PublicObjectiveCard> pos = new ArrayList<>();
        Deck d = new PublicObjectiveDeck();
        for (int i = 0; i < Parameters.PUBLIC_OBJECTIVES; i++)
            pos.add((PublicObjectiveCard) d.draw());
        return pos;
    }

    public void checkPlayersCorrectness(List<Player> players) {
        if (players == null)
            throw new NullPointerException("Passed a null list of players.");
        if (players.size() <= 1)
            throw new IllegalArgumentException("Too few players to start a new match.");
        for (Player p : players) {
            if (p.getWindowFrame() == null)
                throw new IllegalArgumentException("Every player must have a window frame.");
            if (p.getPrivateObjective() == null)
                throw new IllegalArgumentException("Every player must have a private objective.");
        }
        for (int i = 0; i < players.size(); i++)
            for (int j = i + 1; j < players.size(); j++)
                if (players.get(i).getUsername().equals(players.get(j).getUsername()))
                    throw new IllegalArgumentException("Players cannot have the same name.");
    }
}
