package it.polimi.ingsw.model.gameboard.windowframes;

import it.polimi.ingsw.model.gameboard.utility.Color;
import it.polimi.ingsw.model.gameboard.utility.Parameters;
import it.polimi.ingsw.model.gameboard.utility.Shade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class FileWindowPatternGenerator implements WindowPatternGenerator {

    private String name;
    private Map<Coordinate, Color> colorConstraints;
    private Map<Coordinate, Shade> shadeConstraints;
    private int difficulty;

    public FileWindowPatternGenerator(String path) {
        colorConstraints = new HashMap<>();
        shadeConstraints = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            name = br.readLine();
            difficulty = parseInt(br.readLine());
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
        } catch (IOException e) {
            throw new IllegalArgumentException("Given path (" + path + ") is not valid or the file is broken.");
        }
    }

    public String getName() {
        return name;
    }

    public Map<Coordinate, Color> getColorConstraints() {
        return colorConstraints;
    }

    public Map<Coordinate, Shade> getShadeConstraints() {
        return shadeConstraints;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
