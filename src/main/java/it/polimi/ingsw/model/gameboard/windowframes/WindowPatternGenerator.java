package it.polimi.ingsw.model.gameboard.windowframes;

import it.polimi.ingsw.model.gameboard.utility.Color;
import it.polimi.ingsw.model.gameboard.utility.Parameters;
import it.polimi.ingsw.model.gameboard.utility.Shade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class WindowPatternGenerator {

    private String name;
    private Map<Coordinate, Color> colorConstraints;
    private Map<Coordinate, Shade> shadeConstraints;
    private int difficulty;

    WindowPatternGenerator() {
        colorConstraints = new HashMap<>();
        shadeConstraints = new HashMap<>();

        // TODO: add patterns from file
        /* try (BufferedReader br = new BufferedReader(new FileReader(path))) {
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
        */

        colorConstraints.put(new Coordinate(0, 0), Color.BLUE);
        shadeConstraints.put(new Coordinate(0, 1), Shade.LIGHT);
        difficulty = 3;
        name = "Via Lux";
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
