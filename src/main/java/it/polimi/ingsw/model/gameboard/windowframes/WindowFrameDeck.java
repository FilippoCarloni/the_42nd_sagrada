package it.polimi.ingsw.model.gameboard.windowframes;

import it.polimi.ingsw.model.gameboard.cards.AbstractDeck;
import it.polimi.ingsw.model.gameboard.utility.Parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class WindowFrameDeck extends AbstractDeck {

    public WindowFrameDeck() {
        try (Stream<Path> paths = Files.walk(Paths.get(Parameters.WINDOW_PATTERNS_PATH))) {
        paths.filter(Files::isRegularFile).forEach(
                s -> add(new PaperWindowFrame(new FileWindowPatternGenerator(s.toString())))
            );
        } catch (IOException e) {
            throw new IllegalArgumentException("Something went wrong in " + Parameters.WINDOW_PATTERNS_PATH + ". Try checking all the window patterns.");
        }
    }
}
