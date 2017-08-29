package exercises.common.utils;

/**
 * Created by samuelblattner on 28.08.17.
 */
public class Matrix {

    // Data
    private double cells[][];

    Matrix(int cols, int rows) {
        this.cells = new double[cols][];
        for (int c = 0; c < cols; c++) {
            this.cells[c] = new double[rows];
            for (int r = 0; r < rows; r++) {
                this.cells[c][r] = 0;
            }
        }
    }

    public void setValue(int col, int row, double value) {
        if (col >= 0 && col < cells.length) {
            if (row >= 0 && row < cells[col].length) {
                cells[col][row] = value;
            }
        }
    }

    public double getValue(int col, int row) {
        if (col >= 0 && col < cells.length) {
            if (row >= 0 && row < cells[col].length) {
                return cells[col][row];
            }
        }

        return 0;
    }

    public int getNumRows() {
        return cells[0].length;
    }

    public int getNumCols() {
        return cells.length;
    }

    public void add(Matrix otherMatrix) {
        if (otherMatrix.getNumCols() == getNumCols() && otherMatrix.getNumRows() == getNumRows()) {
            for (int c = 0; c < cells.length; c++) {
                for (int r = 0; r < cells[r].length; r++) {
                    cells[c][r] += otherMatrix.getValue(c, r);
                }
            }
        }
    }

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
