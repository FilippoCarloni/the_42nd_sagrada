package it.polimi.ingsw.model.gameboard.cards;

import it.polimi.ingsw.model.utility.JSONSerializable;

public interface Card extends Drawable, JSONSerializable {

    String getName();
    String getDescription();
    int getID();
}
