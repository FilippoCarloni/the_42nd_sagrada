package it.polimi.ingsw.model.gameboard.windowframes;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.dice.PlasticDie;
import it.polimi.ingsw.model.utility.Color;
import it.polimi.ingsw.model.utility.Parameters;
import it.polimi.ingsw.model.utility.Shade;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

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
        name = (String) obj.get("name");
        difficulty = (int) obj.get("difficulty");
        dice = new HashMap<>();
        colorConstraints = new HashMap<>();
        shadeConstraints = new HashMap<>();
        JSONArray list = (JSONArray) obj.get("coordinates");
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                for (Object o : list) {
                    if ((int) ((JSONObject) o).get("row_index") == i && (int) ((JSONObject) o).get("column_index") == j) {
                        if (((JSONObject) o).get("die") != null)
                            dice.put(new Coordinate(i, j), new PlasticDie((JSONObject) ((JSONObject) o).get("die")));
                        if (((JSONObject) o).get("color_constraint") != null)
                            colorConstraints.put(new Coordinate(i, j), Color.findByLabel((String) ((JSONObject) o).get("color_constraint")));
                        if (((JSONObject) o).get("shade_constraint") != null)
                            shadeConstraints.put(new Coordinate(i, j), Shade.findByValue((int) ((JSONObject) o).get("shade_constraint")));                    }
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
            return false;
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
        obj.put("name", name);
        obj.put("difficulty", difficulty);
        JSONArray list = new JSONArray();
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                JSONObject coordinate = new JSONObject();
                coordinate.put("row_index", i);
                coordinate.put("column_index", j);
                coordinate.put("die", dice.get(new Coordinate(i, j)) == null ? null : dice.get(new Coordinate(i, j)).encode());
                coordinate.put("color_constraint", colorConstraints.get(new Coordinate(i, j)) == null ? null : colorConstraints.get(new Coordinate(i, j)).getLabel());
                coordinate.put("shade_constraint", shadeConstraints.get(new Coordinate(i, j)) == null ? null : shadeConstraints.get(new Coordinate(i, j)).getValue());
                list.add(coordinate);
            }
        }
        obj.put("coordinates", list);
        return obj;
    }
}
