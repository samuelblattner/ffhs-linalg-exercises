package exercises.common.utils;

/**
 * Specialized Matrix representing an abstract Vector model.
 */
public abstract class AbstractVector extends Matrix {

    private double length = 0;

    /**
     * Constructor. Creates a one-column Matrix with n rows and assigns the coordinates.
     * @param numDimensions {int} Determines the number of dimensions for this Vector.
     * @param coordinates {double ...} Coordinates to set for this Vector.
     */
    public AbstractVector(int numDimensions, double ... coordinates) {
        super(1, numDimensions);
        for (int c = 0; c < coordinates.length; c++) {
            this.setValue(0, c, coordinates[c]);
        }
    }

    /**
     * Calculate the mangitude of this vector using good old Pythagoras.
     * @return {double} The magnitude.
     */
    private double calculateLength() {
        double squareSum = 0;
        for (int r = 0; r < this.getNumRows(); r++) {
            squareSum += Math.pow(this.getValue(0, r), 2);
        }

        return Math.sqrt(squareSum);
    }

    /**
     * Set a specific coordinate for this Vector and calculate & cache the
     * new magnitude.
     * @param col {int} Column
     * @param row {int} Row
     * @param value {double} Value to store
     */
    @Override
    public void setValue(int col, int row, double value) {
        super.setValue(col, row, value);
        length = calculateLength();
    }

    /**
     * Return this Vector's magnitude.
     * @return {double} Magnitude
     */
    public double getLength() {
        return length;
    }

    /**
     * Calculate the scalar product of this vector and another.
     * @param otherVector {Vector2D} The second vector to use for the caluclation.
     * @return {double} Scalar product.
     */
    protected double scalar(AbstractVector otherVector) {
        double scalarProduct = 0.0;

        for (int r = 0; r < this.getNumRows(); r++) {
            scalarProduct += this.getValue(0, r) * otherVector.getValue(0, r);
        }

        return scalarProduct;
    }

    public abstract AbstractVector clone();
}
