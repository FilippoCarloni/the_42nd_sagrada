package it.polimi.ingsw.model.gameboard.windowframes;

import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.utility.Shade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public final class WindowFrameFactory {

    private WindowFrameFactory() {}

    public static List<WindowFrame> getWindowFrames() {
        List<WindowFrame> frames = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(Parameters.WINDOW_PATTERNS_PATH))) {
            paths.filter(Files::isRegularFile).forEach(path -> frames.add(getWindowFrame(path.toString())));
            return frames;
        } catch (IOException e) {
            throw new IllegalArgumentException("Something went wrong in " + Parameters.WINDOW_PATTERNS_PATH + ". Try checking all the window patterns.");
        }
    }

    private static WindowFrame getWindowFrame(String path) {
        if (path == null)
            throw new NullPointerException("Null path.");
        Map<Coordinate, Color> colorConstraints = new HashMap<>();
        Map<Coordinate, Shade> shadeConstraints = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String name = br.readLine();
            int difficulty = parseInt(br.readLine());
            for (int i = 0; i < Parameters.MAX_ROWS; i++) {
                String string = br.readLine();
                for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                    Color c = Color.findByID("" + string.charAt(j));
                    Shade s = Shade.findByID("" + string.charAt(j));
                    if (c != null)
                        colorConstraints.put(new Coordinate(i, j), c);
                    else if (s != null)
                        shadeConstraints.put(new Coordinate(i, j), s);
                }
            }
            return new PaperWindowFrame(name, difficulty, new HashMap<>(), colorConstraints, shadeConstraints);
        } catch (IOException e) {
            throw new IllegalArgumentException("Given path (" + path + ") is not valid or the file is broken.");
        }
    }
}
