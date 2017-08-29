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

        matrix1.add(matrix2);

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

        matrix1.subtract(matrix2);

        assertEquals(0.0, matrix1.getValue(0, 0));
        assertEquals(0.0, matrix1.getValue(1, 0));
        assertEquals(0.0, matrix1.getValue(0, 1));
        assertEquals(0.0, matrix1.getValue(1, 1));
    }
}
