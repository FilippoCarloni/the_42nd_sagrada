package it.polimi.ingsw.model.gameboard.cards;

import java.util.List;

public interface Deck {

    Drawable draw();
    List<Drawable> draw(int numOfDraws);
    int size();
}
