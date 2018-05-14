package it.polimi.ingsw.model.players;

import it.polimi.ingsw.model.gameboard.cards.PrivateObjectiveCard;
import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

public interface Player {
    String getUsername();
    int getFavorPoints();
    void setFavorPoints(int points);
    WindowFrame getWindowFrame();
    void setWindowFrame(WindowFrame window);
    PrivateObjectiveCard getPrivateObjective();
    void setPrivateObjective(PrivateObjectiveCard po);
}
