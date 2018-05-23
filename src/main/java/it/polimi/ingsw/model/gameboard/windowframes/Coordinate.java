package it.polimi.ingsw.model.gameboard.windowframes;

import it.polimi.ingsw.model.utility.Parameters;

import static java.lang.Integer.parseInt;

public final class Coordinate {

    private final int row;
    private final int column;

    public Coordinate(int row, int column) {
        if (row < 0 || column < 0 || row >= Parameters.MAX_ROWS || column >= Parameters.MAX_COLUMNS)
            throw new IllegalArgumentException("Exceeded window frame limits.");
        this.row = row;
        this.column = column;
    }

    @Override
    public int hashCode() {
        return parseInt("" + row + column);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof Coordinate && row == ((Coordinate) o).row && column == ((Coordinate) o).column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
