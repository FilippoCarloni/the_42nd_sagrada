package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.model.gameboard.cards.Deck;
import it.polimi.ingsw.model.gameboard.cards.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.PublicObjectiveCard;
import it.polimi.ingsw.model.gameboard.cards.privateobjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.gameboard.cards.publicobjectives.PublicObjectiveDeck;
import it.polimi.ingsw.model.gameboard.dice.ClothDiceBag;
import it.polimi.ingsw.model.gameboard.dice.DiceBag;
import it.polimi.ingsw.model.gameboard.roundtrack.PaperRoundTrack;
import it.polimi.ingsw.model.gameboard.roundtrack.RoundTrack;
import it.polimi.ingsw.model.gameboard.utility.Parameters;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrameDeck;

import java.util.ArrayList;
import java.util.List;

public final class GameBox {

    private GameBox instance;

    private GameBox() {}

    public GameBox open() {
        if (instance == null) instance = new GameBox();
        return instance;
    }

    public DiceBag getDiceBag() {
        return new ClothDiceBag();
    }

    public RoundTrack getRoundTrack() {
        return new PaperRoundTrack();
    }

    public List<PublicObjectiveCard> getPublicObjectives() {
        ArrayList<PublicObjectiveCard> listOfCards = new ArrayList<>();
        Deck d = new PublicObjectiveDeck();
        for (int i = 0; i < Parameters.PUBLIC_OBJECTIVES; i++)
            listOfCards.add((PublicObjectiveCard) d.draw());
        return listOfCards;
    }

    public List<PrivateObjectiveCard> getPrivateObjectives(int numOfPlayers) {
        if (numOfPlayers <= 0) throw new IllegalArgumentException("Player number must be greater than 0.");
        ArrayList<PrivateObjectiveCard> listOfCards = new ArrayList<>();
        Deck d = new PrivateObjectiveDeck();
        for (int i = 0; i < numOfPlayers; i++)
            listOfCards.add((PrivateObjectiveCard) d.draw());
        return listOfCards;
    }

    public List<WindowFrame> getWindowFrames(int numOfPlayers) {
        if (numOfPlayers <= 0) throw new IllegalArgumentException("Player number must be greater than 0.");
        ArrayList<WindowFrame> listOfCards = new ArrayList<>();
        Deck d = new WindowFrameDeck();
        for (int i = 0; i < numOfPlayers * Parameters.NUM_OF_WINDOWS_PER_PLAYER_BEFORE_CHOICE; i++)
            listOfCards.add((WindowFrame) d.draw());
        return listOfCards;
    }
}
