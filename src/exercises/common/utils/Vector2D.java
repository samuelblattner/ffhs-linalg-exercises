package exercises.common.utils;

/**
 * Created by samuelblattner on 28.08.17.
 */
public class Vector2D extends Matrix {

    private double length = 0;

    public Vector2D(double dx, double dy) {
        super(1, 2);
        setValue(0, 0, dx);
        setValue(0, 1, dy);
    }

    @Override
    public void setValue(int col, int row, double value) {
        super.setValue(col, row, value);
        length = calculateLength();
    }

    private double calculateLength() {
        return Math.sqrt(Math.pow(getValue(0, 0), 2) + Math.pow(getValue(0, 1), 2));
    }

    public double getLength() {
        return length;
    }

    public double scalar(Vector2D otherVector) {
        return getValue(0, 0) * otherVector.getValue(0, 0) + getValue(0, 1) * otherVector.getValue(0, 1);
    }

    public double determinant(Vector2D otherVector) {
        return getValue(0, 0) * otherVector.getValue(0, 1) - getValue(0, 1) * otherVector.getValue(0, 0);
    }

    public Vector2D combine(Vector2D otherVector) {
        Vector2D result = new Vector2D(getValue(0, 0), getValue(0, 1));
        result.add(otherVector);
        return result;
    }

    public Vector2D difference(Vector2D otherVector) {
        Vector2D result = clone();
        result.subtract(otherVector);
        return result;
    }

    public Vector2D clone() {
        return new Vector2D(getValue(0, 0), getValue(0, 1));
    }
}
