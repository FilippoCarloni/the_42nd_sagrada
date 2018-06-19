package it.polimi.ingsw.model.gameboard.windowframes;

import it.polimi.ingsw.model.gameboard.cards.AbstractDeck;
import java.util.List;

/**
 * Generates a new deck of window frames from files.
 * It should contain the 24 original Sagrada frames.
 * New frames can be added or deleted from the Parameters.WINDOW_PATTERNS_PATH directory.
 * @see it.polimi.ingsw.model.utility.Parameters
 */
public class WindowFrameDeck extends AbstractDeck {

    /**
     * Generates a new window frame deck instance.
     */
    public WindowFrameDeck() {
        List<WindowFrame> frames = WindowFrameFactory.getWindowFrames();
        for (WindowFrame frame : frames)
            add(frame);
    }
}
