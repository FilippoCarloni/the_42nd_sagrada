package it.polimi.ingsw.model.gameboard.dice;

import it.polimi.ingsw.model.gameboard.utility.Color;
import it.polimi.ingsw.model.gameboard.utility.Shade;

public interface Die {

    void roll();
    Color getColor();
    Shade getShade();
    void setColor(Color c);
    void setShade(Shade s);
}
