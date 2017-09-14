package exercises.common.utils;

/**
 * Simple double-value model for matrices.
 */
public class Matrix {

    // Data
    private double cells[][];

    /**
     * Constructor. Initialize matrix cells.
     * @param cols Number of columns
     * @param rows Number of rows
     */
    Matrix(int cols, int rows) {
        this.cells = new double[cols][];
        for (int c = 0; c < cols; c++) {
            this.cells[c] = new double[rows];
            for (int r = 0; r < rows; r++) {
                this.cells[c][r] = 0;
            }
        }
    }

    /**
     * Set value for a specific cell.
     * @param col Cell-column
     * @param row Cell-row
     * @param value Value to store
     */
    public void setValue(int col, int row, double value) {
        if (col >= 0 && col < cells.length) {
            if (row >= 0 && row < cells[col].length) {
                cells[col][row] = value;
            }
        }
    }

    /**
     * Retrieve value for a specific cell.
     * @param col Cell-column
     * @param row Cell-row
     * @return {double} Cell value
     */
    public double getValue(int col, int row) {
        if (col >= 0 && col < cells.length) {
            if (row >= 0 && row < cells[col].length) {
                return cells[col][row];
            }
        }

        return 0;
    }

    /**
     * Return number of matrix rows.
     * @return {int} number of rows
     */
    public int getNumRows() {
        return cells[0].length;
    }

    /**
     * Return number of matrix columns.
     * @return {int} number of columns
     */
    public int getNumCols() {
        return cells.length;
    }

    /**
     * Add another matrix to the current one.
     * @param otherMatrix The matrix to be added.
     */
    public void add(Matrix otherMatrix) {
        if (otherMatrix.getNumCols() == getNumCols() && otherMatrix.getNumRows() == getNumRows()) {
            for (int c = 0; c < cells.length; c++) {
                for (int r = 0; r < cells[r].length; r++) {
                    cells[c][r] += otherMatrix.getValue(c, r);
                }
            }
        }
    }

    /**
     * Subtract another matrix from this one.
     * @param otherMatrix The matrix to be subtracted.
     */
    public void subtract(Matrix otherMatrix) {
        if (otherMatrix.getNumCols() == getNumCols() && otherMatrix.getNumRows() == getNumRows()) {
            for (int c = 0; c < cells.length; c++) {
                for (int r = 0; r < cells[c].length; r++) {
                    cells[c][r] -= otherMatrix.getValue(c, r);
                }
            }
        }
    }
}
