package exercises.common.utils;

/**
 * Specialized Matrix representing a 2D-Vector.
 */
public class Vector2D extends AbstractVector {

    /**
     * Utility method to create a NullVector on the fly.
     * @return {Vector2D} 2D Nullvector
     */
    public static Vector2D createNullVector() {
        return new Vector2D(0, 0);
    }

    /**
     * Constructor.
     * @param dx {double} X coodinate
     * @param dy {double} Y coordinate
     */
    public Vector2D(double dx, double dy) {
        super(3, dx, dy, 1);
    }

    /**
     * Calculate the scalar product of this vector and another. Vector2D needs
     * to implement this method since scalar calculation only works with
     * other Vector2D instances. Furthermore, because Vector2D actually has
     * three dimensions due to affine transformation capabilities, we need to
     * ignore the last row when calculating the scalar product.
     * @param otherVector {Vector2D} The second vector to use for the caluclation.
     * @return {double} Scalar product.
     */
    public double scalar(Vector2D otherVector) {
        double scalarProduct = 0.0;

        for (int r = 0; r < this.getNumRows() - 1; r++) {
            scalarProduct += this.getValue(0, r) * otherVector.getValue(0, r);
        }

        return scalarProduct;
    }

    /**
     * Add another Vector2D to this one.
     * @param otherVector {Vector2D} Other vector to add.
     * @return {Vector2D} Sum
     */
    public Vector2D combine(Vector2D otherVector) {
        Vector2D result = (Vector2D) this.add(otherVector, Vector2D.createNullVector());
        result.setValue(0, 2, 1);
        return result;
    }

    /**
     * Substract another Vector2D from this one.
     * @param otherVector {Vector2D} Other vector to subtract
     * @return {Vector2D} Sum
     */
    public Vector2D difference(Vector2D otherVector) {
        return (Vector2D) this.subtract(otherVector, Vector2D.createNullVector());
    }

    /**
     * Calculate the determinant
     * @param otherVector {Vector2D} Other vector to use for calculation
     * @return Determinant
     */
    public double determinant(Vector2D otherVector) {
        return getValue(0, 0) * otherVector.getValue(0, 1) - getValue(0, 1) * otherVector.getValue(0, 0);
    }

    // ================= AbstractVector Methods ===================
    @Override
    public Vector2D clone() {
        return new Vector2D(getValue(0, 0), getValue(0, 1));
    }
}
