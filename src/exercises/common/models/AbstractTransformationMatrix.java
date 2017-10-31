package exercises.common.models;

import exercises.common.utils.Matrix;

/**
 * Base class for Transformation Matrices.
 */
public abstract class AbstractTransformationMatrix extends Matrix {

    /**
     * Constructor. Initialize matrix cells.
     * @param cols Number of columns
     * @param rows Number of rows
     */
    public AbstractTransformationMatrix(int cols, int rows) {
        super(cols, rows);
    }

    /**
     * Create a uniform scaling matrix.
     * @param scale {double} Scaling factor to apply.
     */
    public void establishUniformScalingMatrix(double scale) {
        for (int c = 0; c < this.getNumCols(); c++) {
            for (int r = 0; r < this.getNumRows(); r++) {
                if (c == r) {
                    this.setValue(c, r, scale);
                }
            }
        }
    }
}
