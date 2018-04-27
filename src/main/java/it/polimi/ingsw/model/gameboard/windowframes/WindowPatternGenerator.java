package it.polimi.ingsw.model.gameboard.windowframes;

import it.polimi.ingsw.model.gameboard.utility.Color;
import it.polimi.ingsw.model.gameboard.utility.Shade;

import java.util.Map;

public interface WindowPatternGenerator {

    String getName();
    Map<Coordinate, Color> getColorConstraints();
    Map<Coordinate, Shade> getShadeConstraints();
    int getDifficulty();
}
