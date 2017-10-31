package exercises.common.utils;

/**
 * Specialized Matrix representing a 2D-Vector.
 */
public class Vector3D extends AbstractVector {

    private double length = 0;

    public static Vector3D createNullVector() {
        return new Vector3D(0, 0, 0);
    }
    /**
     * Constructor.
     * @param dx {double} X coodinate
     * @param dy {double} Y coordinate
     */
    public Vector3D(double dx, double dy, double dz) {
        super(4, dx, dy, dz, 1);
    }

    /**
     * TODO: Check & implement correctly
     * @param otherVector
     * @return
     */
    public double scalar(Vector3D otherVector) {
        return super.scalar(otherVector);
    }

    /**
     * TODO: Check & implement correctly
     * @param otherVector
     * @return
     */
    public Vector3D combine(Vector3D otherVector) {
        Vector3D result = (Vector3D) this.add(otherVector, Vector3D.createNullVector());
        result.setValue(0, 3, 1);
        return result;
    }

    /**
     * TODO: Check & implement correctly
     * @param otherVector
     * @return
     */
    public Vector3D difference(Vector3D otherVector) {
        Vector3D result = (Vector3D) this.subtract(otherVector, new Vector3D(0, 0, 0));
        result.setValue(0, 3, 1);
        return result;
    }

    /**
     * TODO: Check & implement correctly
     * @param otherVector
     * @return
     */
    public double determinant(Vector3D otherVector) {
        return getValue(0, 0) * otherVector.getValue(0, 1) - getValue(0, 1) * otherVector.getValue(0, 0);
    }

    // ================= AbstractVector Methods ===================
    public AbstractVector clone() {
        return new Vector3D(getValue(0, 0), getValue(0, 1), getValue(0, 2));
    }
}
