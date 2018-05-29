package it.polimi.ingsw.model.gameboard.windowframes;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.dice.PlasticDie;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.JSONTag;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.utility.Shade;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

import static java.lang.Integer.parseInt;

public class PaperWindowFrame implements WindowFrame {

    private String name;
    private int difficulty;
    private Map<Coordinate, Die> dice;
    private Map<Coordinate, Color> colorConstraints;
    private Map<Coordinate, Shade> shadeConstraints;

    /**
     * Generates a window frame from a generator instance picked from file.
     * @param generator A WindowFrame generator
     */
    public PaperWindowFrame(WindowPatternGenerator generator) {
        name = generator.getName();
        difficulty = generator.getDifficulty();
        colorConstraints = generator.getColorConstraints();
        shadeConstraints = generator.getShadeConstraints();
        dice = new HashMap<>();
    }

    /**
     * Generates a clone of the die represented with JSON syntax.
     * @param obj A JSON Object that holds WindowFrame-like information
     */
    public PaperWindowFrame(JSONObject obj) {
        name = obj.get(JSONTag.NAME).toString();
        difficulty = parseInt(obj.get(JSONTag.DIFFICULTY).toString());
        dice = new HashMap<>();
        colorConstraints = new HashMap<>();
        shadeConstraints = new HashMap<>();
        JSONArray list = (JSONArray) obj.get(JSONTag.COORDINATES);
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                for (Object o : list) {
                    int row = parseInt(((JSONObject) o).get(JSONTag.ROW_INDEX).toString());
                    int column = parseInt(((JSONObject) o).get(JSONTag.COLUMN_INDEX).toString());
                    if (row == i && column == j) {
                        if (((JSONObject) o).get(JSONTag.DIE) != null)
                            dice.put(new Coordinate(i, j), new PlasticDie((JSONObject) ((JSONObject) o).get(JSONTag.DIE)));
                        if (((JSONObject) o).get(JSONTag.COLOR_CONSTRAINT) != null)
                            colorConstraints.put(new Coordinate(i, j), Color.findByLabel((String) ((JSONObject) o).get(JSONTag.COLOR_CONSTRAINT)));
                        if (((JSONObject) o).get(JSONTag.SHADE_CONSTRAINT) != null)
                            shadeConstraints.put(new Coordinate(i, j), Shade.findByValue(parseInt(((JSONObject) o).get(JSONTag.SHADE_CONSTRAINT).toString())));                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Die> getDice() {
        Object[] diceArray = dice.values().toArray();
        List<Die> diceList = new ArrayList<>();
        for (Object o : diceArray)
            diceList.add(new PlasticDie(((Die) o).encode()));
        return diceList;
    }

    @Override
    public Color getColorConstraint(int row, int column) {
        try {
            return colorConstraints.get(new Coordinate(row, column));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public Shade getShadeConstraint(int row, int column) {
        try {
            return shadeConstraints.get(new Coordinate(row, column));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public int getDifficulty() {
        return difficulty;
    }

    @Override
    public boolean isEmpty(int row, int column) {
        try {
            return dice.get(new Coordinate(row, column)) == null;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    @Override
    public Die getDie(int row, int column) {
        try {
            if (!isEmpty(row, column))
                return new PlasticDie(dice.get(new Coordinate(row, column)).encode());
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public void put(Die die, int row, int column) {
        if (die == null)
            throw new NullPointerException("Cannot place a null die.");
        if (!isEmpty(row, column))
            throw new IllegalArgumentException("This place is already occupied.");
        dice.put(new Coordinate(row, column), new PlasticDie(die.encode()));
    }

    @Override
    public void move(int oldRow, int oldColumn, int newRow, int newColumn) {
        if (isEmpty(oldRow, oldColumn))
            throw new IllegalArgumentException("There's no die to move.");
        if (!isEmpty(newRow, newColumn))
            throw new IllegalArgumentException("The place is already occupied by another die.");
        dice.put(new Coordinate(newRow, newColumn), dice.remove(new Coordinate(oldRow, oldColumn)));
    }

    @Override
    public Die pick(int row, int column) {
        if (isEmpty(row, column))
            return null;
        return dice.remove(new Coordinate(row, column));
    }

    @Override
    public String toString() {

        int pixelWidth = 21;
        int maxRows = Parameters.MAX_ROWS;
        int maxColumns = Parameters.MAX_COLUMNS;
        int leftEmptySide = (pixelWidth - Parameters.MAX_COLUMNS * 3) / 2;
        int rightEmptySide = pixelWidth - maxColumns * 3 - leftEmptySide;

        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int i = 0; i < pixelWidth; i++) sb.append("_");
        sb.append(" \n|");
        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < leftEmptySide; j++) sb.append(" ");
            for (int j = 0; j < maxColumns; j++) {
                Die d = getDie(i, j);
                Color c = colorConstraints.get(new Coordinate(i, j));
                Shade s = shadeConstraints.get(new Coordinate(i, j));
                if (d != null)
                    sb.append(getDie(i, j));
                else if (c != null)
                    sb.append(c);
                else if (s != null)
                    sb.append(s);
                else sb.append("[ ]");
            }
            for (int j = 0; j < rightEmptySide; j++) sb.append(" ");
            sb.append("|\n|");
        }

        for (int i = 0; i < pixelWidth; i++) sb.append("-");
        sb.append("|\n|");
        sb.append(name);
        for (int i = 0; i < pixelWidth - name.length(); i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < difficulty; i++) sb.append("*");
        for (int i = 0; i < pixelWidth - difficulty; i++) sb.append(" ");
        sb.append("|\n|");
        for (int i = 0; i < pixelWidth; i++) sb.append("_");
        sb.append("|\n");
        return sb.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject encode() {
        JSONObject obj = new JSONObject();
        obj.put(JSONTag.NAME, name);
        obj.put(JSONTag.DIFFICULTY, difficulty);
        JSONArray list = new JSONArray();
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                JSONObject coordinate = new JSONObject();
                coordinate.put(JSONTag.ROW_INDEX, i);
                coordinate.put(JSONTag.COLUMN_INDEX, j);
                coordinate.put(JSONTag.DIE, dice.get(new Coordinate(i, j)) == null ? null : dice.get(new Coordinate(i, j)).encode());
                coordinate.put(JSONTag.COLOR_CONSTRAINT, colorConstraints.get(new Coordinate(i, j)) == null ? null : colorConstraints.get(new Coordinate(i, j)).getLabel());
                coordinate.put(JSONTag.SHADE_CONSTRAINT, shadeConstraints.get(new Coordinate(i, j)) == null ? null : shadeConstraints.get(new Coordinate(i, j)).getValue());
                list.add(coordinate);
            }
        }
        obj.put(JSONTag.COORDINATES, list);
        return obj;
    }
}
