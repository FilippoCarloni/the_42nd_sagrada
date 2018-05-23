package it.polimi.ingsw.model.gameboard.windowframes;

import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Shade;

import java.util.Map;

public interface WindowPatternGenerator {

    String getName();
    Map<Coordinate, Color> getColorConstraints();
    Map<Coordinate, Shade> getShadeConstraints();
    int getDifficulty();
}
