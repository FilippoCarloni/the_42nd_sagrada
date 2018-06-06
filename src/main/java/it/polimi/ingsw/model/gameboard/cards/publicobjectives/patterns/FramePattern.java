package it.polimi.ingsw.model.gameboard.cards.publicobjectives.patterns;

import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

@FunctionalInterface
public interface FramePattern {
    int getNumOfPatterns(WindowFrame windowFrame);
}
