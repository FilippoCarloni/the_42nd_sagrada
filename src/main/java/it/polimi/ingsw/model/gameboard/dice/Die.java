package it.polimi.ingsw.model.gameboard.dice;

import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.JSONSerializable;
import it.polimi.ingsw.model.utility.Shade;

public interface Die extends JSONSerializable {

    void roll();
    Color getColor();
    Shade getShade();
    void setColor(Color c);
    void setShade(Shade s);
}
