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
    public Matrix(int cols, int rows) {
        this.cells = new double[cols][];
        for (int c = 0; c < cols; c++) {
            this.cells[c] = new double[rows];
            for (int r = 0; r < rows; r++) {
                this.cells[c][r] = 0;
            }
        }
    }

    @Override
    public String toString() {

        String out = "";

        for (int r = 0; r < cells[0].length; r++) {
            for( int c = 0; c < cells.length; c++) {
                out = String.format("%s %f", out, cells[c][r]);
            }
            out = String.format("%s\n", out);
        }
        return out;
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

    public void setValues(double ... values) {

        int col = 0;
        int row = 0;

        for (double value : values) {
            cells[col][row] = value;

            col += 1;
            if (col >= this.getNumCols()) {
                col = 0;
                row += 1;
                if (row >= this.getNumRows()) {
                    return;
                }
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
     * Return value for a specific cell as String.
     * @param col {int} Column
     * @param row {int} Row
     * @return {String} Cell value as String
     */
    public String getStringValue(int col, int row) {
        Double val = this.getValue(col, row);
        return val.toString();
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
     * Add another matrix to the current one and returns a new Matrix
     * containing the result.
     * @param otherMatrix The matrix to be added.
     * @return {Matrix} Result matrix
     */
    public Matrix add(Matrix otherMatrix, Matrix resultMatrix) {

        if (resultMatrix == null) {
            resultMatrix = new Matrix(this.getNumCols(), this.getNumRows());
        }

        if (otherMatrix.getNumCols() == getNumCols() && otherMatrix.getNumRows() == getNumRows()) {
            for (int c = 0; c < cells.length; c++) {
                for (int r = 0; r < cells[c].length; r++) {
                    resultMatrix.setValue(c, r, cells[c][r] + otherMatrix.getValue(c, r));
                }
            }
        }

        return resultMatrix;
    }

    /**
     * Subtract another matrix from this one and returns a new Matrix
     * containing the result.
     * @param otherMatrix The matrix to be subtracted.
     * @return {Matrix} Result matrix
     */
    public Matrix subtract(Matrix otherMatrix, Matrix resultMatrix) {

        if (resultMatrix == null) {
            resultMatrix = new Matrix(this.getNumCols(), this.getNumRows());
        }

        if (otherMatrix.getNumCols() == getNumCols() && otherMatrix.getNumRows() == getNumRows()) {
            for (int c = 0; c < cells.length; c++) {
                for (int r = 0; r < cells[c].length; r++) {
                    resultMatrix.setValue(c, r, cells[c][r] - otherMatrix.getValue(c, r));
                }
            }
        }

        return resultMatrix;
    }

    /**
     * Multiplies this matrix with another matrix (in this order) and
     * returns a new Matrix containing the result.
     * @param otherMatrix {Matrix} The other "factor" matrix
     * @return {Matrix} The result matrix
     */
    public Matrix multiply(Matrix otherMatrix, Matrix resultMatrix) {

        if (resultMatrix == null) {
            resultMatrix = new Matrix(this.getNumCols(), this.getNumRows());
        }

        if (otherMatrix.getNumRows() == getNumCols()) {
            for (int c = 0; c < otherMatrix.getNumCols(); c++) {
                for (int r = 0; r < this.getNumRows(); r++) {

                    double multResult = 0;

                    for (int cM = 0; cM < cells.length; cM++) {
                        multResult += cells[cM][r] * otherMatrix.getValue(c, cM);
                    }

                    resultMatrix.setValue(c, r, multResult);
                }
            }
        }

        return resultMatrix;
    }

    public void establishIdentityMatrix() {
        for (int c = 0; c < this.getNumCols(); c++) {
            for (int r = 0; r < this.getNumRows(); r++) {
                if (c == r) {
                    cells[c][r] = 1;
                } else {
                    cells[c][r] = 0;
                }
            }
        }
    }
}
