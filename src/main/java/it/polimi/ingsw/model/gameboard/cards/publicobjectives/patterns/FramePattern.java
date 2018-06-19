package it.polimi.ingsw.model.gameboard.cards.publicobjectives.patterns;

import it.polimi.ingsw.model.gameboard.windowframes.WindowFrame;

/**
 * Represents a pattern fetcher on a window frame.
 */
@FunctionalInterface
public interface FramePattern {

    /**
     * Returns the number of patterns found on a window frame, looking at its dice positioning.
     * @param windowFrame Provided window frame
     * @return Integer that counts the patterns found
     */
    int getNumOfPatterns(WindowFrame windowFrame);
}
