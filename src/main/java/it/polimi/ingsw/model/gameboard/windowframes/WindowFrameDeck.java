package it.polimi.ingsw.model.gameboard.windowframes;

import it.polimi.ingsw.model.gameboard.cards.AbstractDeck;
import java.util.List;

/**
 * Generates a new deck of window frames from files.
 * It should contain the 24 original Sagrada frames.
 */
public class WindowFrameDeck extends AbstractDeck {

    public WindowFrameDeck() {
        List<WindowFrame> frames = WindowFrameFactory.getWindowFrames();
        for (WindowFrame frame : frames)
            add(frame);
    }
}
