package it.polimi.ingsw.model.gameboard.windowframes;

import it.polimi.ingsw.model.utility.Parameters;

import static java.lang.Integer.parseInt;

/**
 * Represents the unique identifier of a window frame cell.
 * Window frames are modelled as 2D planes and their cells are identifies by a tuple (rowIndex, columnIndex).
 * <br>
 * NOTE: 0 <= rowIndex < 4 and 0 <= columnIndex < 5.
 */
public final class Coordinate {

    private final int row;
    private final int column;

    /**
     * Generates a Coordinate object uniquely identified by its coordinate integers.
     * @param row An integer between 0 and 3
     * @param column An integer between 0 and 4
     */
    public Coordinate(int row, int column) {
        if (!(validateRow(row) && validateColumn(column)))
            throw new IllegalArgumentException("Exceeded window frame limits.");
        this.row = row;
        this.column = column;
    }

    /**
     * Returns true if the passed row is valid as a window frame coordinate.
     * @param row The tested row integer
     * @return A boolean value
     */
    public static boolean validateRow(int row) {
        return row >= 0 && row < Parameters.MAX_ROWS;
    }

    /**
     * Returns true if the passed column is valid as a window frame coordinate.
     * @param column The tested column integer
     * @return A boolean value
     */
    public static boolean validateColumn(int column) {
        return column >= 0 && column < Parameters.MAX_COLUMNS;
    }

    @Override
    public int hashCode() {
        return parseInt("" + row + column);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof Coordinate && row == ((Coordinate) o).row && column == ((Coordinate) o).column;
    }
}
