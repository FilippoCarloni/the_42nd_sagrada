package it.polimi.ingsw.model.gameboard.windowframes;

import it.polimi.ingsw.model.gameboard.dice.Die;
import it.polimi.ingsw.model.gameboard.dice.PlasticDie;
import it.polimi.ingsw.model.gameboard.utility.Color;
import it.polimi.ingsw.model.gameboard.utility.Parameters;
import it.polimi.ingsw.model.gameboard.utility.Shade;

import java.util.*;

public class PaperWindowFrame implements WindowFrame {

    private String name;
    private int difficulty;
    private Map<Coordinate, Die> dice;
    private Map<Coordinate, Color> colorConstraints;
    private Map<Coordinate, Shade> shadeConstraints;

    PaperWindowFrame(WindowPatternGenerator generator) {
        name = generator.getName();
        difficulty = generator.getDifficulty();
        colorConstraints = generator.getColorConstraints();
        shadeConstraints = generator.getShadeConstraints();
        dice = new HashMap<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<Coordinate, Color> getColorConstraints() {
        return new HashMap<>(colorConstraints);
    }

    @Override
    public Map<Coordinate, Shade> getShadeConstraints() {
        return new HashMap<>(shadeConstraints);
    }

    @Override
    public int getDifficulty() {
        return difficulty;
    }

    @Override
    public boolean isEmpty(int row, int column) {
        return dice.get(new Coordinate(row, column)) == null;
    }

    @Override
    public Die getDie(int row, int column) {
        if (!isEmpty(row, column))
            return new PlasticDie((PlasticDie) dice.get(new Coordinate(row, column)));
        return null;
    }

    @Override
    public void put(Die die, int row, int column) {
        if (die == null) throw new NullPointerException("Cannot place a null die.");
        if (!isEmpty(row, column))
            throw new IllegalArgumentException("This place is already occupied.");
        dice.put(new Coordinate(row, column), die);
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
    public void move(Die die, int row, int column) {
        if (die == null)
            throw new NullPointerException("There's no null die on the window frame.");
        if (!isEmpty(row, column))
            throw new IllegalArgumentException("The place is already occupied by another die.");
        for (Map.Entry<Coordinate, Die> entry : dice.entrySet()) {
            Coordinate c = entry.getKey();
            if (dice.get(c).equals(die)) {
                put(dice.remove(c), row, column);
                return;
            }
        }
        throw new IllegalArgumentException("There's no such die in the window frame.");
    }

    @Override
    public Iterator<Die> iterator() {
        return new WindowFrameIterator(dice);
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

    private class WindowFrameIterator implements Iterator<Die> {

        private int position;
        private List<Die> dice;

        private WindowFrameIterator(Map<Coordinate, Die> dice) {
            position = 0;
            this.dice = new ArrayList<>(dice.values());
        }

        @Override
        public boolean hasNext() {
            return position < dice.size();
        }

        @Override
        public Die next() {
            if (hasNext()) {
                Die d = dice.get(position);
                position++;
                return d;
            }
            throw new NoSuchElementException("There are no more dice in the window frame.");
        }
    }
}
