package it.polimi.ingsw.model.gameboard.windowframes;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.utility.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

import static it.polimi.ingsw.model.utility.ExceptionMessage.NULL_PARAMETER;
import static it.polimi.ingsw.model.utility.ExceptionMessage.OBJECT_NOT_EXISTS;
import static it.polimi.ingsw.model.utility.ExceptionMessage.SLOT_OCCUPIED;

/**
 * Implements a WindowFrame interface with a generic structure.
 */
public class PaperWindowFrame implements WindowFrame {

    private String name;
    private int difficulty;
    private Map<Coordinate, Die> dice;
    private Map<Coordinate, Color> colorConstraints;
    private Map<Coordinate, Shade> shadeConstraints;

    /**
     * Generates a new window frame instance from the information provided.
     * @param name Window's name
     * @param difficulty Window's difficulty
     * @param dice Map of dice that the window is holding
     * @param colorConstraints Map of the window's color constraints
     * @param shadeConstraints Map of the window's shade constraints
     */
    public PaperWindowFrame(String name, int difficulty, Map<Coordinate, Die> dice, Map<Coordinate, Color> colorConstraints, Map<Coordinate, Shade> shadeConstraints) {
        this.name = name;
        this.difficulty = difficulty;
        this.dice = new HashMap<>(dice);
        this.colorConstraints = new HashMap<>(colorConstraints);
        this.shadeConstraints = new HashMap<>(shadeConstraints);
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
            diceList.add(JSONFactory.getDie(((Die) o).encode()));
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
                return JSONFactory.getDie(dice.get(new Coordinate(row, column)).encode());
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public void put(Die die, int row, int column) {
        if (die == null)
            throw new NullPointerException(NULL_PARAMETER);
        if (!isEmpty(row, column))
            throw new IllegalArgumentException(SLOT_OCCUPIED);
        dice.put(new Coordinate(row, column), JSONFactory.getDie(die.encode()));
    }

    @Override
    public void move(int oldRow, int oldColumn, int newRow, int newColumn) {
        if (isEmpty(oldRow, oldColumn))
            throw new IllegalArgumentException(OBJECT_NOT_EXISTS);
        if (!isEmpty(newRow, newColumn))
            throw new IllegalArgumentException(SLOT_OCCUPIED);
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
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Parameters.MAX_ROWS; i++) {
            for (int j = 0; j < Parameters.MAX_COLUMNS; j++) {
                Color color = getColorConstraint(i, j);
                Shade shade = getShadeConstraint(i, j);
                Die die = getDie(i, j);
                if (die != null) sb.append(die);
                else if (color != null) sb.append(color);
                else if (shade != null) sb.append(shade);
                else sb.append("[  ]");
            }
            sb.append("\n");
        }
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
