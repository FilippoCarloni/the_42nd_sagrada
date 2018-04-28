package it.polimi.ingsw.model.gameboard.cards;

import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

public interface ObjectiveCard extends Card {

    int getValuePoints(WindowFrame window);
}
