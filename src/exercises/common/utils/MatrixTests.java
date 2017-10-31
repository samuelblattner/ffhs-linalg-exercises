package exercises.common.utils;

import junit.framework.TestCase;

/**
 * Created by samuelblattner on 28.08.17.
 */
public class MatrixTests extends TestCase {

    public void testMatrixAddition() {
        Matrix matrix1 = new Matrix(2, 2);
        matrix1.setValue(0, 0, 1);
        matrix1.setValue(1, 0, 2);
        matrix1.setValue(0, 1, 3);
        matrix1.setValue(1, 1, 4);

        Matrix matrix2 = new Matrix(2, 2);
        matrix2.setValue(0, 0, 1);
        matrix2.setValue(1, 0, 2);
        matrix2.setValue(0, 1, 3);
        matrix2.setValue(1, 1, 4);

        matrix1 = matrix1.add(matrix2, null);

        assertEquals(2.0, matrix1.getValue(0, 0));
        assertEquals(4.0, matrix1.getValue(1, 0));
        assertEquals(6.0, matrix1.getValue(0, 1));
        assertEquals(8.0, matrix1.getValue(1, 1));
    }

    public void testMatrixSubtraction() {
        Matrix matrix1 = new Matrix(2, 2);
        matrix1.setValue(0, 0, 1);
        matrix1.setValue(1, 0, 2);
        matrix1.setValue(0, 1, 3);
        matrix1.setValue(1, 1, 4);

        Matrix matrix2 = new Matrix(2, 2);
        matrix2.setValue(0, 0, 1);
        matrix2.setValue(1, 0, 2);
        matrix2.setValue(0, 1, 3);
        matrix2.setValue(1, 1, 4);

        matrix1 = matrix1.subtract(matrix2, null);

        assertEquals(0.0, matrix1.getValue(0, 0));
        assertEquals(0.0, matrix1.getValue(1, 0));
        assertEquals(0.0, matrix1.getValue(0, 1));
        assertEquals(0.0, matrix1.getValue(1, 1));
    }

    public void testMatrixMultiplication() {
        Matrix matrix1 = new Matrix(2, 2);
        matrix1.setValues(
                1, 2,
                3, 4
        );

        Matrix matrix2 = new Matrix(2, 2);
        matrix2.setValues(
                1, 2,
                3, 4
        );

        matrix1 = matrix1.multiply(matrix2, null);

        assertEquals(7.0, matrix1.getValue(0, 0));
        assertEquals(10.0, matrix1.getValue(1, 0));
        assertEquals(15.0, matrix1.getValue(0, 1));
        assertEquals(22.0, matrix1.getValue(1, 1));
    }

    public void testMatrixMultiplication2() {
        Matrix matrix1 = new Matrix(3, 2);
        matrix1.setValues(
                1, 2, 3,
                4, 5, 6
        );

        Matrix matrix2 = new Matrix(2, 3);
        matrix2.setValues(
                1, 2,
                3, 4,
                5, 6
        );

        matrix1 = matrix1.multiply(matrix2, null);

        assertEquals(22.0, matrix1.getValue(0, 0));
        assertEquals(28.0, matrix1.getValue(1, 0));
        assertEquals(49.0, matrix1.getValue(0, 1));
        assertEquals(64.0, matrix1.getValue(1, 1));
    }

    public void testMatrixMultiplication3() {
        Matrix matrix1 = new Matrix(3, 3);
        matrix1.setValues(
                1, 2, 3,
                4, 5, 6,
                7, 8, 9
        );

        Matrix matrix2 = new Matrix(1, 3);
        matrix2.setValues(
                1,
                2,
                3
        );

        matrix1 = matrix1.multiply(matrix2, null);

        assertEquals(14.0, matrix1.getValue(0, 0));
        assertEquals(32.0, matrix1.getValue(0, 1));
        assertEquals(50.0, matrix1.getValue(0, 2));
    }
}
